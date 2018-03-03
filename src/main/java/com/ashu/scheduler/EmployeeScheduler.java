package com.ashu.scheduler;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ashu.domain.Employee;
import com.ashu.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;

@Service
@Slf4j
public class EmployeeScheduler {

	private static final int LOCK_TIME_DURATION = 14 * 60 * 1000;

	private final EmployeeRepository employeeRepository;

	public EmployeeScheduler(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Scheduled(fixedRate = 15 * 60 * 1000)
	@SchedulerLock(name = "scheduledTaskAddEmployee", lockAtMostFor = LOCK_TIME_DURATION, lockAtLeastFor = LOCK_TIME_DURATION)
	private void scheduledTask() {
		log.debug("Starting scheduler ...");
		final Employee emp = new Employee();
		emp.setId(RandomUtils.nextInt());
		emp.setName(RandomStringUtils.randomAlphabetic(12));
		employeeRepository.save(emp);
		log.debug("Employee Saved : {}", emp.toString());
	}

}
