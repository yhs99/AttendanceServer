package com.attendance.attendanceserver.api;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.attendance.attendanceserver.api.domain.EmployeeVO;
import com.attendance.attendanceserver.api.infra.TestDataSourceConfig;
import com.attendance.attendanceserver.api.persistence.EmployeeRepository;

import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestDataSourceConfig.class)
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	@Transactional
	void setUp() throws Exception {
		System.out.println("Count :: " + employeeRepository.count());
		EmployeeVO employee1 = new EmployeeVO(1L, "희", EmployeeVO.EmployeeStatus.활동_중, "CARD-1234");
		EmployeeVO employee2 = new EmployeeVO(2L, "성", EmployeeVO.EmployeeStatus.퇴사, "CARD-5678");
		System.out.println(employee1);
		System.out.println(employee2);
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.flush(); // 영속성 컨텍스트의 변경사항을 즉시 DB에 반영
		System.out.println("Count :: " + employeeRepository.count());
	}

	@Test
	@Transactional
	public void testFindAllEmployees() {
		List<EmployeeVO> employees = employeeRepository.findAllByOrderByEmployeeIdAsc();
		System.out.println("employee :: " + employees);
		assert(employees != null);
		assert employees.size() == 2;
	}

	@AfterEach
	@Transactional
	void tearDown() throws Exception {
		employeeRepository.deleteAll();
	}
}
