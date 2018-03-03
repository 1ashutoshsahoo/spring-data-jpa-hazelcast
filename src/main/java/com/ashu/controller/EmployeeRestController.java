package com.ashu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashu.domain.Employee;
import com.ashu.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/employees", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class EmployeeRestController {

	private final EmployeeRepository employeeRepository;

	public EmployeeRestController(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@PostMapping
	public ResponseEntity<Void> addEmployee(@RequestBody Employee employee) {
		log.debug("Request received to save employee : {}", employee.toString());
		employeeRepository.save(employee);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public List<Employee> getAllEmpployees() {
		log.debug("Request received for all employees.");
		final Iterable<Employee> employees = employeeRepository.findAll(new Sort("name"));
		final List<Employee> list = new ArrayList<>();
		if (employees != null) {
			employees.forEach(list::add);
		}
		return list;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getEmployee(@PathVariable("id") Integer id) {
		log.debug("Request received view employee for id: {}", id);
		final Employee employee = employeeRepository.findOne(id);
		if (employee == null) {
			log.debug("No Employee found for id : {} ", id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

}
