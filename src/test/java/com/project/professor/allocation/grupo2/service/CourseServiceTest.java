package com.project.professor.allocation.grupo2.service;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.professor.allocation.grupo2.entity.Course;

public class CourseServiceTest {
	

	@Autowired
	CourseService courseService;

	@Test
	public void findAll() {
		// Act
		List<Course> courses = courseService.findAll(null);

		// Print
		courses.forEach(System.out::println);
	}
	
	

	@Test
	public void findById() {
		// Arrange
		Long id = 1L;

		// Act
		Course course = courseService.findById(id);

		// Print
		System.out.println(course);
	}

	@Test
	public void save() throws ParseException {
		// Arrange
		Course course = new Course();
		course.setId(null);
		course.setName("Analise e Desenvolvimento de Sistemas");
		
		// Act
		course = courseService.create(course);

		// Print
		System.out.println(course);
	}

	@Test
	public void update() throws ParseException {
		// Arrange
		Course course = new Course();
		course.setId(1L);
		course.setName("Enfermagem");
	
		

		// Act
		course = courseService.update(course);

		// Print
		System.out.println(course);
	}

	@Test
	public void deleteById() {
		// Arrange
		Long id = 1L;

		// Act
		courseService.deleteById(id);
	}

	@Test
	public void deleteAll() {
		// Act
		courseService.deleteAll();
	}

}
