package com.attendance.attendanceserver.infra;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Description;

@Aspect
@Component
public class ResponseAspect {

	@Description("API Response 직렬화 AOP")
	@Around("execution(* com.attendance.attendanceserver..controller..*(..)))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();

		if(result instanceof ResponseEntity<?> responseEntity) {
			Object body = responseEntity.getBody();
			int statusCode = responseEntity.getStatusCode().value();

			if(statusCode >= 200 && statusCode < 300) {
				ApiResponseEntity<Object> apiResponse = new ApiResponseEntity<>("success", body);
				return ResponseEntity.status(responseEntity.getStatusCode())
					.headers(responseEntity.getHeaders())
					.body(apiResponse);
			}else {
				final String ERROR_MESSAGE = "Error Occurred";
				String errorMessage = (body != null) ? body.toString() : ERROR_MESSAGE;
				ApiResponseEntity<Object> apiResponse = new ApiResponseEntity<>("fail", errorMessage);
				return ResponseEntity.status(responseEntity.getStatusCode())
					.headers(responseEntity.getHeaders())
					.body(apiResponse);
			}
		}
		return new ApiResponseEntity<>("success", result);
	}
}
