package com.ipl.professorallocation.view.listar_alocacao_professor;

import static com.ipl.professorallocation.view.alocar_professor.AddEditAlocacaoProfessorActivity.EXTRA_EDITAR_ALOCACAO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.professorallocation.data.repositorio.AlocacaoRepositorio;
import com.ipl.professorallocation.data.service.RespositorioCallBack;
import com.ipl.professorallocation.databinding.ActivityListarAlocacaoBinding;
import com.ipl.professorallocation.model.AllocationsItem;
import com.ipl.professorallocation.view.alocar_professor.AddEditAlocacaoProfessorActivity;
import com.ipl.professorallocation.view.listar_alocacao_professor.adapter.ListaAlocacaoAdapter;

import java.util.List;

public class ListarAlocacaoActivity extends AppCompatActivity {

    private ActivityListarAlocacaoBinding binding;
    private ListaAlocacaoAdapter adapter;
    private AlocacaoRepositorio alocacaoRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarAlocacaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        alocacaoRepositorio = new AlocacaoRepositorio();
        setupOnCLickListener();
        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarAlocacoes();
    }

    private void setupOnCLickListener() {
        binding.botaoAtualizarListaAlocacao.setOnClickListener(view -> {
            listarAlocacoes();
        });
        binding.botaoAdicionarAlocacao.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditAlocacaoProfessorActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        adapter = new ListaAlocacaoAdapter(new ListaAlocacaoAdapter.RecyclerViewCallback() {
            @Override
            public void onClickExcluirAlocacao(AllocationsItem deletarAlocacao) {
                deletarAlocacao(deletarAlocacao);
            }

            @Override
            public void onClickEditarAlocacao(AllocationsItem editarAlocacaco) {
                Intent intent = new Intent(ListarAlocacaoActivity.this, AddEditAlocacaoProfessorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_EDITAR_ALOCACAO, editarAlocacaco);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.listaProfessor.setAdapter(adapter);
        binding.listaProfessor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void listarAlocacoes() {
        mostrarProgressBar(View.VISIBLE);
        alocacaoRepositorio.listarProfessores(new RespositorioCallBack<List<AllocationsItem>>() {
            @Override
            public void onResponse(List<AllocationsItem> response) {
                adapter.addListaProfessor(response);
                mostrarProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                mostrarProgressBar(View.GONE);
            }
        });
    }

    private void deletarAlocacao(AllocationsItem alocacao) {
        mostrarProgressBar(View.VISIBLE);
        alocacaoRepositorio.deletarProfessor(alocacao.getId(), new RespositorioCallBack<Void>() {
            @Override
            public void onResponse(Void response) {
                adapter.removerUsuario(alocacao);
                mostrarProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                mostrarProgressBar(View.GONE);
            }
        });
    }

    private void mostrarProgressBar(int mostrarProgressBar) {
        binding.progressBar.setVisibility(mostrarProgressBar);
    }
}