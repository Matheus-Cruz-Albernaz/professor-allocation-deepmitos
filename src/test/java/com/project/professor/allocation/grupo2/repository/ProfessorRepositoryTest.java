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

import com.project.professor.allocation.grupo2.entity.Professor;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestPropertySource(locations = "classpath:application.properties")
public class ProfessorRepositoryTest {

	@Autowired
	ProfessorRepository professorRepository;

	@Test
	public void findAll() {
		// Act
		List<Professor> prof = professorRepository.findAll();

		// Print
		prof.forEach(System.out::println);
	}

	@Test
	public void findById() {
		// Arrange
		Long id = 1L;

		// Act
		Professor prof = professorRepository.findById(id).orElse(null);

		// Print
		System.out.println(prof);

	}

	@Test
    public void findByNameContainingIgnoreCase() {
        // Arrange
        String name = "Professor";

        // Act
        List<Professor> professors = professorRepository.findByNameContainingIgnoreCase(name);

        // Print
        professors.forEach(System.out::println);
    }

	@Test
    public void findByDepartmentId() {
        // Arrange
        Long departmentId = 1L;

        // Act
        List<Professor> professors = professorRepository.findByDepartmentId(departmentId);

        // Print
        professors.forEach(System.out::println);
    }
	
	@Test
	public void save_create() throws ParseException {
		// Arrange
		Professor professor = new Professor();
		professor.setCpf("96573561203");
		professor.setDepartmentId(6L);
		professor.setName("Rafael Duarte");

		// Act
		professor = professorRepository.save(professor);

		// Print
		System.out.println(professor);

	}

	@Test
	public void save_update() throws ParseException {
		// Arrange
		Professor professor = new Professor();
		professor.setCpf("96573561203");
		professor.setDepartmentId(6L);
		professor.setName("Matheus Cruz Albernaz");

		// Act
		professor = professorRepository.save(professor);

		// Print
		System.out.println(professor);

	}

	@Test
	public void deleteById() {
		// Arrange
		Long id = 1L;

		// Act
		professorRepository.deleteById(id);

	}

	@Test
	public void deleteAll() {
		// Act
		professorRepository.deleteAllInBatch();

	}
}