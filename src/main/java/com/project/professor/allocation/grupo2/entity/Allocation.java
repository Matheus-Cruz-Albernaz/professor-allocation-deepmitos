package com.project.professor.allocation.grupo2.entity;

import java.time.DayOfWeek;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

@Entity
public class Allocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "day", nullable = false)
	@Enumerated(EnumType.STRING)
	private DayOfWeek day;

	@JsonFormat(pattern = "HH:mmZ")
	@JsonSerialize(using = DateSerializer.class)
	// JAVA -> JSON
	@JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
	// JSON -> JAVA
	@Column(name = "start", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date start;

	@JsonFormat(pattern = "HH:mmZ")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
	@Column(name = "end", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date end;

	@Column(name = "course_id", nullable = false)
	private Long courseId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "course_id", updatable = false, insertable = false, nullable = false)
	private Course course;

	@Column(name = "professor_id", nullable = false)
	private Long professorId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "professor_id", updatable = false, insertable = false, nullable = false)
	private Professor professor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;

	}

	@Override
	public String toString() {
		return "Allocation [id=" + id + ", day=" + day + ", start=" + start + ", end=" + end + ", courseId=" + courseId
				+ ", course=" + course + ", professorId=" + professorId + ", prof=" + professor + "]";
	}

}
