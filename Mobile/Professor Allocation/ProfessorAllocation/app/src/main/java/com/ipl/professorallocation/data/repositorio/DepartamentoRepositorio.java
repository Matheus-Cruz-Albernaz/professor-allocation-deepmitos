package com.ipl.professorallocation.data.repositorio;

import com.ipl.professorallocation.data.service.DepartamentoService;
import com.ipl.professorallocation.data.service.RespositorioCallBack;
import com.ipl.professorallocation.model.Department;
import com.ipl.professorallocation.model.Professor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartamentoRepositorio {

    private final DepartamentoService service;

    public DepartamentoRepositorio() {
        service = RetrofitClient.getDepartamentoService();
    }

    public void listarDepartamentos(RespositorioCallBack<List<Department>> respositorioCallBack) {
        Call<List<Department>> call = service.listaTodosOsDepartamentos();
        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                List<Department> list = response.body();
                respositorioCallBack.onResponse(list);
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                respositorioCallBack.onFailure(t);
            }
        });
    }
}
