package com.project.professor.allocation.grupo2.service;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.project.professor.allocation.grupo2.entity.Department;

@SpringBootTest
@TestPropertySource(locations = "classpath:department.properties")
public class DepartmentServiceTest {
	
	@Autowired
	DepartmentService departmentService;

	@Test
	public void findAll() {
		// Act
		List<Department> department = departmentService.findAll(null);

		// Print
		department.forEach(System.out::println);
	}
	
	public void findAllByName() {
		// Arrange
		String name = "department";
		
		// Act
		List<Department> dep = departmentService.findAll(name);

		// Print
		dep.forEach(System.out::println);
	}

	@Test
	public void findById() {
		// Arrange
		Long id = 1L;

		// Act
		Department department = departmentService.findById(id);

		// Print
		System.out.println(department);
	}

	@Test
	public void save() throws ParseException {
		// Arrange
		Department department = new Department();
		department.setId(null);
		department.setName("Exatas");
		
		// Act
		department = departmentService.create(department);

		// Print
		System.out.println(department);
	}

	@Test
	public void update() throws ParseException {
		// Arrange
		Department department = new Department();
		department.setId(1L);
		department.setName("Saúde");
		
		// Act
		department = departmentService.update(department);

		// Print
		System.out.println(department);
	}

	@Test
	public void deleteById() {
		// Arrange
		Long id = 1L;

		// Act
		departmentService.deleteById(id);
	}

	@Test
	public void deleteAll() {
		// Act
		departmentService.deleteAll();
	}

}
