package com.project.professor.allocation.grupo2.service;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.project.professor.allocation.grupo2.entity.Professor;

@SpringBootTest
@TestPropertySource(locations = "classpath:professor.properties")
public class ProfessorServiceTest {
	
	@Autowired
	ProfessorService professorService;

	@Test
	public void findAll() {
		// Act
		List<Professor> professor = professorService.findAll(null);

		// Print
		professor.forEach(System.out::println);
	}
	
	@Test
	public void findAllbyName() {
		// Arrange
		String name = "professor";
		
		// Act
		List<Professor> prof = professorService.findAll(name);

		// Print
		prof.forEach(System.out::println);
	}

	@Test
	public void findById() {
		// Arrange
		Long id = 1L;

		// Act
		Professor professor = professorService.findById(id);

		// Print
		System.out.println(professor);
	}
	
	@Test
	public void findByDepartmentId() {
		// Arrange
		Long id = 1L;

		// Act
		List <Professor> prof = professorService.findByDepartmentId(id);

		// Print
		System.out.println(prof);
	}

	@Test
	public void save() throws ParseException {
		// Arrange
		Professor professor = new Professor();
		professor.setId(null);
		professor.setName("Amirton");
		professor.setCpf("06001234567");
		professor.setDepartmentId(4L);
		
		// Act
		professor = professorService.create(professor);

		// Print
		System.out.println(professor);
	}

	@Test
	public void update() throws ParseException {
		// Arrange
		Professor professor = new Professor();
		professor.setId(1L);
		professor.setName("Rafael");
		professor.setCpf("12345678000");
		professor.setDepartmentId(2L);
	
		// Act
		professor = professorService.update(professor);

		// Print
		System.out.println(professor);
	}

	@Test
	public void deleteById() {
		// Arrange
		Long id = 1L;

		// Act
		professorService.deleteById(id);
	}

	@Test
	public void deleteAll() {
		// Act
		professorService.deleteAll();
	}
	
}
