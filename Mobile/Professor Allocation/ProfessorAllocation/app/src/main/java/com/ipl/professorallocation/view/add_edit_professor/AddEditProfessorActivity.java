package com.ipl.professorallocation.view.add_edit_professor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ipl.professorallocation.data.repositorio.DepartamentoRepositorio;
import com.ipl.professorallocation.data.repositorio.ProfessorRepositorio;
import com.ipl.professorallocation.data.service.RespositorioCallBack;
import com.ipl.professorallocation.databinding.ActivityAddEditProfessorBinding;
import com.ipl.professorallocation.model.Department;
import com.ipl.professorallocation.model.Professor;
import com.ipl.professorallocation.model.ProfessorRequest;

import java.util.List;

public class AddEditProfessorActivity extends AppCompatActivity {

    private ActivityAddEditProfessorBinding binding;
    private ArrayAdapter<Department> departamentoSpinner;
    private DepartamentoRepositorio depatamentoRepositorio;
    private ProfessorRepositorio professorRepositorio;
    public static final String EXTRA_EDITAR_PROFESSOR = "editar_professor";
    private Professor editarProfessor = null;
    Department departamentoSelecionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditProfessorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        depatamentoRepositorio = new DepartamentoRepositorio();
        professorRepositorio = new ProfessorRepositorio();
        setupSpinnerListaDepartamentos();
        setTitle("Adicionar professor");
        listarDepartamentos();
        carregarProfessorParaEditar();
        setupOnCLickListener();
    }

    private void setupOnCLickListener() {
        binding.botaoSalvar.setOnClickListener(view -> {
            ProfessorRequest professorRequest = criarProfessorParaEnviarAoBackend();
            if (editarProfessor != null) {
                atualizarDadosDoProfessor(editarProfessor.getId(), professorRequest);
            } else {
                criarProfessor(professorRequest);
            }
        });
    }

    private void atualizarDadosDoProfessor(int id, ProfessorRequest professorRequest) {
        professorRepositorio.atualizarDadosDoProfessor(id, professorRequest, new RespositorioCallBack<Professor>() {
            @Override
            public void onResponse(Professor response) {
                Toast.makeText(AddEditProfessorActivity.this, "Professor atualizado.", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: Erro ao criar professor: " + t);
            }
        });
    }

    private void criarProfessor(ProfessorRequest professorRequest) {
        professorRepositorio.criarProfessor(professorRequest, new RespositorioCallBack<Professor>() {
            @Override
            public void onResponse(Professor response) {
                Toast.makeText(AddEditProfessorActivity.this, "Professor criado.", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: Erro ao criar professor: " + t);
            }
        });
    }

    @NonNull
    private ProfessorRequest criarProfessorParaEnviarAoBackend() {
        String cpfProfessor = binding.cpf.getText().toString();
        String nomeProfessor = binding.nome.getText().toString();
        ProfessorRequest professorRequest = new ProfessorRequest(cpfProfessor, departamentoSelecionado.getId(), nomeProfessor);
        return professorRequest;
    }

    private void carregarProfessorParaEditar() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            setTitle("Editar professor");
            Professor professor = (Professor) bundle.getSerializable(EXTRA_EDITAR_PROFESSOR);
            if (professor != null) {
                editarProfessor = professor;
                binding.nome.setText(professor.getName());
                binding.cpf.setText(professor.getCpf());
            }
        }
    }

    private void setupSpinnerListaDepartamentos() {
        departamentoSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.spinner.setAdapter(departamentoSpinner);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departamentoSelecionado = departamentoSpinner.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddEditProfessorActivity.this, "onNothingSelected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listarDepartamentos() {
        depatamentoRepositorio.listarDepartamentos(new RespositorioCallBack<List<Department>>() {
            @Override
            public void onResponse(List<Department> response) {
                // Inicializa o array com o tamanho da lista
                Department[] array = new Department[response.size()];
                // Carrega o array com os dados da lista
                Department[] arrayDepartamentos = response.toArray(array); // fill the array
                departamentoSpinner.addAll(arrayDepartamentos);

                // Irá setar o departamento no spinner quando estiver editando um professor
                if (editarProfessor != null) {
                    departamentoSelecionado = editarProfessor.getDepartment();
                    int departamentoPosition = departamentoSpinner.getPosition(editarProfessor.getDepartment());
                    binding.spinner.setSelection(departamentoPosition, true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: " + t);
            }
        });
    }
}