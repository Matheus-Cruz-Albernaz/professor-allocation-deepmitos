package com.ipl.professorallocation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Professor implements Serializable {

	@SerializedName("allocations")
	private List<AllocationsItem> allocations;

	@SerializedName("name")
	private String name;

	@SerializedName("cpf")
	private String cpf;

	@SerializedName("id")
	private int id;

	@SerializedName("department")
	private Department department;

	public void setAllocations(List<AllocationsItem> allocations){
		this.allocations = allocations;
	}

	public List<AllocationsItem> getAllocations(){
		return allocations;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public String getCpf(){
		return cpf;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDepartment(Department department){
		this.department = department;
	}

	public Department getDepartment(){
		return department;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Professor professor = (Professor) o;
		return id == professor.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(allocations, name, cpf, id, department);
	}

	@Override
 	public String toString() {
		return name;
	}
}