package com.attendance.attendanceserver.infra;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;

@Getter
@Aspect
@Component
@Slf4j
public class ApiLoggingAspect {

	private final HttpServletRequest hsr;

	public ApiLoggingAspect(HttpServletRequest hsr) {
		this.hsr = hsr;
	}

	@Around("execution(* com.attendance.attendanceserver..controller..*(..))")
	public Object apiLogging(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		HttpServletRequest request = hsr;
		Object result;
		Object[] args = joinPoint.getArgs();
		String params = Arrays.toString(args);
		try {
			result = joinPoint.proceed(args);
		}finally {
			long endTime = System.currentTimeMillis();

			String logMessage = String.format(
				"API CALLED :: [%s] %s - %s, Params: %s, 처리시간 :: %dms",
				LocalDateTime.now(),
				request.getMethod(),
				request.getRequestURI(),
				params,
				endTime
			);

			log.info(logMessage);
		}
		return result;
	}
}
