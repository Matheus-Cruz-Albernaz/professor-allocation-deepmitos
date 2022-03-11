package com.project.professor.allocation.grupo2.service;

import java.util.List;
import java.util.Optional;

import com.project.professor.allocation.grupo2.entity.Allocation;
import com.project.professor.allocation.grupo2.entity.Course;
import com.project.professor.allocation.grupo2.repository.AllocationRepository;
import com.project.professor.allocation.grupo2.repository.CourseRepository;

public class CourseService {
	private final CourseRepository courseRepository;
	private final AllocationRepository allocationRepository;

	public CourseService(CourseRepository courseRepository, AllocationRepository allocationRepository) {
		super();
		this.courseRepository = courseRepository;
		this.allocationRepository = allocationRepository;
	}
	// CRUD READ all
		public List<Course> findAll(String name) {
			if(name==null) {
				return courseRepository.findAll();
			}else {
				return courseRepository.findByNameContainingIgnoreCase(name);
			}
		}

		// CRUD READ by ID
		public Course findById(Long id) {
			Optional<Course> optional = courseRepository.findById(id);
			Course courses = optional.orElse(null);
			return courses;
		}
		
		public Course create(Course course) {

			course.setId(null);
			return saveInternal(course);
		}

		// CRUD: Update
		public Course update(Course course) {

			Long id = course.getId();
			if (id != null && courseRepository.existsById(id)) {

				return saveInternal(course);
			} else {
				return null;
			}
		}

		public void deleteById(Long id) {
			if (id != null && courseRepository.existsById(id)) {
				courseRepository.deleteById(id);
			}
		}

		public void deleteAll() {

			courseRepository.deleteAllInBatch();
		}
		private Course saveInternal(Course course) {
	        course = courseRepository.save(course);

	        List<Allocation> allocations = allocationRepository.findByCourseId(course.getId());
	        course.setAllocations(allocations);

	        return course;
	    }

}
