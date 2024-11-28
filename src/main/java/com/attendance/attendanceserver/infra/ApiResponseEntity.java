package com.attendance.attendanceserver.infra;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseEntity<T> {
	private String statusCode;
	private T data;
	private String message;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime requestTime;

	public ApiResponseEntity() {
		super();
	}

	public ApiResponseEntity(String statusCode, T data) {
		this.statusCode = statusCode;
		this.data = data;
		this.requestTime = LocalDateTime.now();
	}

	public ApiResponseEntity(String statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
		this.requestTime = LocalDateTime.now();
	}
}
