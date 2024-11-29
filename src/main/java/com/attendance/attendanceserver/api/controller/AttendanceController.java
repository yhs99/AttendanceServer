package com.attendance.attendanceserver.api.controller;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.attendanceserver.api.persistence.EmployeeRepository;
import com.attendance.attendanceserver.api.service.AttendanceService;

@RestController
@RequestMapping("/api/")
public class AttendanceController {

	private final AttendanceService attendanceService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	public AttendanceController(AttendanceService attendanceService) throws SQLException {
		this.attendanceService = attendanceService;
	}

	@GetMapping("employees")
	public ResponseEntity<Object> getEmployees() throws SQLException {
		return ResponseEntity.ok(attendanceService.findAllEmployees());
	}

}
