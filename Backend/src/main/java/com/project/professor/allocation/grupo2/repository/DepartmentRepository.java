package com.project.professor.allocation.grupo2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.professor.allocation.grupo2.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	List<Department> findByNameContainingIgnoreCase(String name);

}
