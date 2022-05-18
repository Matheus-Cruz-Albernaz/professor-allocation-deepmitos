package com.ipl.professorallocation.model.curso;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Curso{

	@SerializedName("allocations")
	private List<AllocationsItem> allocations;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString() {
		return name;
	}
}