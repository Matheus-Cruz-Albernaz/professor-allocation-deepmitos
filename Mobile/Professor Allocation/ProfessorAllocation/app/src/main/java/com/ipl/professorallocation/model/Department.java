package com.ipl.professorallocation.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Department implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public void setName(String name) {
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}


	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}
		final Department other = (Department) obj;
		return this.id == other.id;
	}
}