package com.ipl.professorallocation.view.listar_professor;

import static com.ipl.professorallocation.view.add_edit_professor.AddEditProfessorActivity.EXTRA_EDITAR_PROFESSOR;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.professorallocation.data.repositorio.ProfessorRepositorio;
import com.ipl.professorallocation.data.service.RespositorioCallBack;
import com.ipl.professorallocation.databinding.ActivityListarProfessoresBinding;
import com.ipl.professorallocation.model.Professor;
import com.ipl.professorallocation.view.add_edit_professor.AddEditProfessorActivity;
import com.ipl.professorallocation.view.listar_professor.adapter.ListaProfessorAdapter;

import java.util.List;

public class ListarProfessoresActivity extends AppCompatActivity {

    private ActivityListarProfessoresBinding binding;
    private ListaProfessorAdapter adapter;
    private ProfessorRepositorio professorRepositorio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarProfessoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        professorRepositorio = new ProfessorRepositorio();
        setupRecyclerView();
        setupOnCLickListener();
        setTitle("Listar professor");
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarTodosOsProfessores();
    }

    private void setupOnCLickListener() {
        binding.botaoAtualizarListaProfessor.setOnClickListener(view -> {
            listarTodosOsProfessores();
        });
        binding.botaoAdicionarProfessor.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditProfessorActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        adapter = new ListaProfessorAdapter(new ListaProfessorAdapter.RecyclerViewCallback() {
            @Override
            public void onClickExcluirUsuario(Professor deletarProfessor) {
                deletarProfessor(deletarProfessor);
            }

            @Override
            public void onClickEditarUsuario(Professor editarProfessor) {
                Intent intent = new Intent(ListarProfessoresActivity.this, AddEditProfessorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_EDITAR_PROFESSOR, editarProfessor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.listaProfessor.setAdapter(adapter);
        binding.listaProfessor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void listarTodosOsProfessores() {
        mostrarProgressBar(View.VISIBLE);

        professorRepositorio.listarProfessores(new RespositorioCallBack<List<Professor>>() {
            @Override
            public void onResponse(List<Professor> response) {
                adapter.addListaProfessor(response);
                mostrarProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                mostrarProgressBar(View.GONE);
            }
        });
    }

    private void deletarProfessor(Professor professor) {
        mostrarProgressBar(View.VISIBLE);
        professorRepositorio.deletarProfessor(professor.getId(), new RespositorioCallBack<Void>() {
            @Override
            public void onResponse(Void response) {
                adapter.removerUsuario(professor);
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