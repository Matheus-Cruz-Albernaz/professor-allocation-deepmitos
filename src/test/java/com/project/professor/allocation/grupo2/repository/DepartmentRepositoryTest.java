package com.project.professor.allocation.grupo2.repository;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import com.project.professor.allocation.grupo2.entity.Department;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestPropertySource(locations = "classpath:application.properties")
public class DepartmentRepositoryTest {

	@Autowired
	DepartmentRepository departmentRepository;

	@Test
	public void findAll() {
		// Act
		List<Department> dep = departmentRepository.findAll();

		// Print
		dep.forEach(System.out::println);
	}

	@Test
	public void findById() {
		// Arrange
		Long id = 1L;

		// Act
		Department department = departmentRepository.findById(id).orElse(null);

		// Print
		System.out.println(department);

	}

	@Test
    public void findByNameContainingIgnoreCase() {
        // Arrange
        String name = "ud";

        // Act
        List<Department> departments = departmentRepository.findByNameContainingIgnoreCase(name);

        // Print
        departments.forEach(System.out::println);
    }

	@Test
	public void save_create() throws ParseException {
		// Arrange
		Department department = new Department();
		department.setName("Saúde");

		// Act
		department = departmentRepository.save(department);

		// Print
		System.out.println(department);

	}

	@Test
	public void save_update() throws ParseException {
		// Arrange
		Department department = new Department();
		department.setId(9L);
		department.setName("Universidade Qualiti");

		// Act
		department = departmentRepository.save(department);

		// Print
		System.out.println(department);

	}

	@Test
	public void deleteById() {
		// Arrange
		Long id = 1L;

		// Act
		departmentRepository.deleteById(id);

	}

	@Test
	public void deleteAll() {
		// Act
		departmentRepository.deleteAllInBatch();

	}
}