package com.attendance.attendanceserver.api.service;

import java.util.List;

import com.attendance.attendanceserver.api.domain.EmployeeVO;

public interface AttendanceService {
	List<EmployeeVO> findAllEmployees();
}
