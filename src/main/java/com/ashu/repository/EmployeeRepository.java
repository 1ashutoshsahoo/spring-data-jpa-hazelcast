package com.ashu.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import com.ashu.domain.Employee;

public interface EmployeeRepository extends KeyValueRepository<Employee, Integer> {

}
