package com.attendance.attendanceserver.api.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendance.attendanceserver.api.domain.EmployeeVO;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeVO, Long> {
	List<EmployeeVO> findAllByOrderByEmployeeIdAsc();
}
