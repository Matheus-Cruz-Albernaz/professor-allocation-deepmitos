package com.ipl.professorallocation.data.repositorio;

import android.util.Log;

import com.ipl.professorallocation.data.service.CursoService;
import com.ipl.professorallocation.data.service.RespositorioCallBack;
import com.ipl.professorallocation.model.Course;
import com.ipl.professorallocation.model.curso.Curso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CursoRepositorio {

    private final CursoService service;

    public CursoRepositorio() {
        service = RetrofitClient.getCursoService();
    }

    public void listarCursos(RespositorioCallBack<List<Course>> respositorioCallBack) {
        Call<List<Course>> call = service.listarCursos();
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                respositorioCallBack.onResponse(response.body());
                Log.d("IPL1", "onResponse sucesso: " + response.body());
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                respositorioCallBack.onFailure(t);
                Log.d("IPL1", "onFailure error: " + t);
            }
        });
    }
}
