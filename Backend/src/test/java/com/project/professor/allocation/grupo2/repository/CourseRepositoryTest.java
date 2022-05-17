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

import com.project.professor.allocation.grupo2.entity.Course;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestPropertySource(locations = "classpath:application.properties")
public class CourseRepositoryTest {

	@Autowired
	CourseRepository courseRepository;

	@Test
	public void findAll() {
		// Act
		List<Course> course = courseRepository.findAll();

		// Print
		course.forEach(System.out::println);
	}

	@Test
	public void findById() {
		// Arrange
		Long id = 5L;

		// Act
		Course course = courseRepository.findById(id).orElse(null);

		// Print
		System.out.println(course);

	}

	@Test
	public void findByNameContainingIgnoreCase() {
		// Arrange
		String name = "ia";

		// Act
		List<Course> courses = courseRepository.findByNameContainingIgnoreCase(name);

		// Print
		courses.forEach(System.out::println);
	}

	@Test
	public void save_create() throws ParseException {
		// Arrange
		Course course = new Course();
		course.setName("'Design");
		// Act
		Course curso = courseRepository.save(course);
		// Print
		System.out.println(curso);
	}

	@Test
	public void save_update() throws ParseException {
		// Arrange
		Course course = new Course();
		course.setId(7L);
		course.setName("Deep Code 1");

		// Act
		course = courseRepository.save(course);

		// Print
		System.out.println(course);

	}

	public void deleteById() {
		// Arrange
		Long id = 6L;

		// Act
		courseRepository.deleteById(id);

	}

	@Test
	public void deleteAll() {
		// Act
		courseRepository.deleteAllInBatch();

	}
}
