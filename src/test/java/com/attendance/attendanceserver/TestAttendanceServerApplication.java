package com.attendance.attendanceserver;

import org.springframework.boot.SpringApplication;

public class TestAttendanceServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(AttendanceServerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
