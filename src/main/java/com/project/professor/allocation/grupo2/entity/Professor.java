package com.project.professor.allocation.grupo2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(length = 14, unique = true, nullable = false)
	private String cpf;
	
	@Column(name="department_id", nullable = false)
	private Long departmentId;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "department_id", updatable = false, insertable = false, nullable = false)
	private Department depart;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "professor")
	private List<Allocation> allocations;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@JsonIgnoreProperties({ "professors" })
	@ManyToOne(optional = false)
	@JoinColumn(name = "department_id", nullable = false, insertable = false, updatable = false)
	private Department department;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}
	public List<Allocation> getAllocations() {
		return allocations;
	}

	public void setAllocations(List<Allocation> allocations) {
		this.allocations = allocations;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", name=" + name + ", cpf=" + cpf + ", departmentId=" + departmentId
				+ ", depart=" + depart + "]";
	}

}
