package com.ipl.professorallocation.data.repositorio;

import android.util.Log;

import com.ipl.professorallocation.data.service.ProfessorService;
import com.ipl.professorallocation.data.service.RespositorioCallBack;
import com.ipl.professorallocation.model.Professor;
import com.ipl.professorallocation.model.ProfessorRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessorRepositorio {

    private final ProfessorService service;

    public ProfessorRepositorio() {
        service = RetrofitClient.getProfessorService();
    }

    public void listarProfessores(RespositorioCallBack<List<Professor>> respositorioCallBack) {
        Call<List<Professor>> call = service.listaTodosOsProfessores();
        call.enqueue(new Callback<List<Professor>>() {
            @Override
            public void onResponse(Call<List<Professor>> call, Response<List<Professor>> response) {
                List<Professor> list = response.body();
                respositorioCallBack.onResponse(list);
            }

            @Override
            public void onFailure(Call<List<Professor>> call, Throwable t) {
                respositorioCallBack.onFailure(t);
            }
        });
    }

    public void criarProfessor(ProfessorRequest professorRequest, RespositorioCallBack<Professor> callBack) {
        Call<Professor> call = service.criarProfessor(professorRequest);
        call.enqueue(new Callback<Professor>() {
            @Override
            public void onResponse(Call<Professor> call, Response<Professor> response) {
                callBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Professor> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void deletarProfessor(int professorId, RespositorioCallBack<Void> callBack) {
        Call<Void> call = service.deletarProfessor(professorId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("IPL1", "onFailure: Erro ao deletar!" + t);
                callBack.onFailure(t);
            }
        });
    }

    public void atualizarDadosDoProfessor(int professorId,
                                          ProfessorRequest professorRequest,
                                          RespositorioCallBack<Professor> callBack) {
        Call<Professor> call = service.atualizarDadosDoProfessor(professorId, professorRequest);
        call.enqueue(new Callback<Professor>() {
            @Override
            public void onResponse(Call<Professor> call, Response<Professor> response) {
                callBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Professor> call, Throwable t) {
                Log.d("IPL1", "onFailure: Erro ao deletar!" + t);
                callBack.onFailure(t);
            }
        });
    }

}
