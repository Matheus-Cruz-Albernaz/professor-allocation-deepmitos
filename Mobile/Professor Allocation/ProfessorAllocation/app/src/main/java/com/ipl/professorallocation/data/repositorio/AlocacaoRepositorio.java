package com.ipl.professorallocation.data.repositorio;

import android.util.Log;

import com.ipl.professorallocation.data.service.AlocacaoService;
import com.ipl.professorallocation.data.service.RespositorioCallBack;
import com.ipl.professorallocation.model.AllocationRequest;
import com.ipl.professorallocation.model.AllocationsItem;
import com.ipl.professorallocation.model.Professor;
import com.ipl.professorallocation.model.ProfessorRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlocacaoRepositorio {

    private final AlocacaoService service;

    public AlocacaoRepositorio() {
        service = RetrofitClient.getAlocacaoService();
    }

    public void listarProfessores(RespositorioCallBack<List<AllocationsItem>> respositorioCallBack) {
        Call<List<AllocationsItem>> call = service.listaTodosAsAlocacaoes();
        call.enqueue(new Callback<List<AllocationsItem>>() {
            @Override
            public void onResponse(Call<List<AllocationsItem>> call, Response<List<AllocationsItem>> response) {
                List<AllocationsItem> list = response.body();
                respositorioCallBack.onResponse(list);
            }

            @Override
            public void onFailure(Call<List<AllocationsItem>> call, Throwable t) {
                respositorioCallBack.onFailure(t);
            }
        });
    }

    public void criarAlocacao(AllocationRequest allocationRequest, RespositorioCallBack<AllocationsItem> callBack) {
        Call<AllocationsItem> call = service.criarAlocacao(allocationRequest);
        call.enqueue(new Callback<AllocationsItem>() {
            @Override
            public void onResponse(Call<AllocationsItem> call, Response<AllocationsItem> response) {
                callBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AllocationsItem> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void deletarProfessor(int alocacaoId, RespositorioCallBack<Void> callBack) {
        Call<Void> call = service.deletarAlocacao(alocacaoId);
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

    public void editarAlocacao(int allocationId, AllocationRequest allocationRequest, RespositorioCallBack<AllocationsItem> callBack) {
        Call<AllocationsItem> call = service.atualizarDadosDaAlocacao(allocationId, allocationRequest);
        call.enqueue(new Callback<AllocationsItem>() {
            @Override
            public void onResponse(Call<AllocationsItem> call, Response<AllocationsItem> response) {
                callBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AllocationsItem> call, Throwable t) {
                Log.d("IPL1", "onFailure: Erro ao deletar!" + t);
                callBack.onFailure(t);
            }
        });
    }
}
