package com.ipl.professorallocation.data.service;

import com.ipl.professorallocation.model.AllocationRequest;
import com.ipl.professorallocation.model.AllocationsItem;
import com.ipl.professorallocation.model.Professor;
import com.ipl.professorallocation.model.ProfessorRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlocacaoService {

    @GET("/allocations")
    Call<List<AllocationsItem>> listaTodosAsAlocacaoes();

    @DELETE("/allocations/{id}")
    Call<Void> deletarAlocacao(@Path("id") int professorId);

    @POST("/allocations")
    Call<AllocationsItem> criarAlocacao(@Body AllocationRequest allocationRequest);

    @PUT("/allocations/{id}")
    Call<AllocationsItem> atualizarDadosDaAlocacao(@Path("id") int idAlocacao,
                                              @Body AllocationRequest professorRequest);
}
