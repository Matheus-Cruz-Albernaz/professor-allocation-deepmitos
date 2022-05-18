package com.ipl.professorallocation.data.service;

import com.ipl.professorallocation.model.Department;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DepartamentoService {

    @GET("/departments")
    Call<List<Department>> listaTodosOsDepartamentos();
}
