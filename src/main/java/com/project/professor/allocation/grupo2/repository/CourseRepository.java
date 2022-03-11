package com.project.professor.allocation.grupo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.professor.allocation.grupo2.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

}
