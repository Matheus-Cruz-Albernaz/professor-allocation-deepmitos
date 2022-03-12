package com.project.professor.allocation.grupo2.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import com.project.professor.allocation.grupo2.entity.Allocation;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestPropertySource(locations = "classpath:application.properties")
public class AllocationRepositoryTest {

	SimpleDateFormat sdf = new SimpleDateFormat("HH:mmZ");

	@Autowired
	AllocationRepository allocationRepository;

	@Test
	public void findAll() {
		// Act
		List<Allocation> allocations = allocationRepository.findAll();

		// Print
		System.out.println(allocations);
	}

	@Test
	public void findById() {
		// Arrange

		// Act
		Optional<Allocation> optional = allocationRepository.findById(2L);

		// Print
		Allocation allocation = optional.orElse(null);
		System.out.println(allocation);
	}

	@Test
	public void findByProfessorId() {
		// Arrange

		// Act

		// Print

	}

	@Test
	public void findByCourseId() {
		// Arrange

		// Act

		// Print

	}

	@Test
	public void save_create() throws ParseException {
		// Arrange
		Allocation allocation = new Allocation();
		allocation.setDay(DayOfWeek.MONDAY);
		allocation.setEnd(sdf.parse("12:00-0300"));
		allocation.setStart(sdf.parse("10:00-0300"));
		allocation.setProfessorId(1L);
		allocation.setCourseId(2L);
		// Act
		Allocation alloc = allocationRepository.save(allocation);
		// Print
		System.out.println(alloc);
	}

	@Test
	public void save_update() throws ParseException {
		// Arrange
		Allocation allocation = new Allocation();
		allocation.setId(3L);
		allocation.setDay(DayOfWeek.MONDAY);
		allocation.setEnd(sdf.parse("12:00-0300"));
		allocation.setStart(sdf.parse("10:00-0300"));
		allocation.setProfessorId(1L);
		allocation.setCourseId(2L);
		// Act
		Allocation alloc = allocationRepository.save(allocation);
		// Print
		System.out.println(alloc);
	}

	@Test
	public void deleteById() {
		// Arrange

		// Act
		allocationRepository.deleteById(11L);
	}

	@Test
	public void deleteAll() {
		// Act
		allocationRepository.deleteAllInBatch();
	}
}
