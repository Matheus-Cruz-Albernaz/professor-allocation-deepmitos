package com.project.professor.allocation.grupo2.service;

import java.util.List; 
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.grupo2.entity.Allocation;
import com.project.professor.allocation.grupo2.entity.Course;
import com.project.professor.allocation.grupo2.entity.Professor;
import com.project.professor.allocation.grupo2.repository.AllocationRepository;

@Service
public class AllocationService {

	private final AllocationRepository allocationRepository;
	private final CourseService courseService;
	private final ProfessorService professorService;

	public AllocationService(AllocationRepository allocationRepository, 
							 CourseService courseService, 
							 ProfessorService professorService) {
		super();
		this.allocationRepository = allocationRepository;
		this.courseService = courseService;
		this.professorService = professorService;
	}

	public List<Allocation> findAll() {

		List<Allocation> allocations = allocationRepository.findAll();
		return allocations;
	}

	public Allocation findById(Long id) {
		Optional<Allocation> optional = allocationRepository.findById(id);
		Allocation allocations = optional.orElse(null);
		return allocations;
	}

	public List<Allocation> findByCourseId(Long courseId) {

		return allocationRepository.findByCourseId(courseId);
	}

	public List<Allocation> findByProfessorId(Long professorId) {

		return allocationRepository.findByProfessorId(professorId);
	}

	public Allocation create(Allocation allocation) {

		allocation.setId(null);
		return saveInternal(allocation);
	}

	public Allocation update(Allocation allocation) {

		Long id = allocation.getId();
		if (id != null && allocationRepository.existsById(id)) {

			return saveInternal(allocation);
		} else {
			return null;
		}
	}

	private Allocation saveInternal(Allocation allocation) {
		if (!isEndHourGreaterThanStartHour(allocation) || hasCollision(allocation)) {
			throw new RuntimeException();
		} else {
			allocation = allocationRepository.save(allocation);

			Professor professor = professorService.findById(allocation.getProfessorId());
			allocation.setProfessor(professor);

			Course course = courseService.findById(allocation.getCourseId());
			allocation.setCourse(course);

			return allocation;
		}
	}

	public void deleteById(Long id) {

		if (id != null && allocationRepository.existsById(id)) {
			allocationRepository.deleteById(id);
		}
	}

	public void deleteAll() {

		allocationRepository.deleteAllInBatch();
	}

	boolean hasCollision(Allocation newAllocation) {
		boolean hasCollision = false;

		List<Allocation> currentAllocations = allocationRepository.findByProfessorId(newAllocation.getProfessorId());

		for (Allocation currentAllocation : currentAllocations) {
			hasCollision = hasCollision(currentAllocation, newAllocation);
			if (hasCollision) {
				break;
			}
		}

		return hasCollision;
	}

	private boolean hasCollision(Allocation currentAllocation, Allocation newAllocation) {
		return !currentAllocation.getId().equals(newAllocation.getId())
				&& currentAllocation.getDay() == newAllocation.getDay()
				&& currentAllocation.getStart().compareTo(newAllocation.getEnd()) < 0
				&& newAllocation.getStart().compareTo(currentAllocation.getEnd()) < 0;
	}

	boolean isEndHourGreaterThanStartHour(Allocation allocation) {
		return allocation != null && allocation.getStart() != null && allocation.getEnd() != null
				&& allocation.getEnd().compareTo(allocation.getStart()) > 0;
	}
}
