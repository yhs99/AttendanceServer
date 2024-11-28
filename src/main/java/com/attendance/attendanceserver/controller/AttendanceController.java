package com.attendance.attendanceserver.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class AttendanceController {

	@GetMapping("employees")
	public ResponseEntity<Object> getEmployees() {

		return ResponseEntity.ok("employees list");
	}
}
