package com.attendance.attendanceserver.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.attendanceserver.api.domain.EmployeeVO;
import com.attendance.attendanceserver.api.persistence.EmployeeRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private final EmployeeRepository employeeRepository;

	@Autowired
	public AttendanceServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	/**
	 *
	 * @return List<EmployeeVO></EmployeeVO>
	 */
	@Override
	public List<EmployeeVO> findAllEmployees() {
		return employeeRepository.findAllByOrderByEmployeeIdAsc();
	}
}
