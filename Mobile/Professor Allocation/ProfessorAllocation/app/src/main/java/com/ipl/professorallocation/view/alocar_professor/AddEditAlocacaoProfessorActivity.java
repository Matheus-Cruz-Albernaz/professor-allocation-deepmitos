package com.ipl.professorallocation.view.alocar_professor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.ipl.professorallocation.data.repositorio.AlocacaoRepositorio;
import com.ipl.professorallocation.data.repositorio.CursoRepositorio;
import com.ipl.professorallocation.data.repositorio.ProfessorRepositorio;
import com.ipl.professorallocation.data.service.RespositorioCallBack;
import com.ipl.professorallocation.databinding.ActivityAddEditAlocacaoProfessorBinding;
import com.ipl.professorallocation.model.AllocationRequest;
import com.ipl.professorallocation.model.AllocationsItem;
import com.ipl.professorallocation.model.Course;
import com.ipl.professorallocation.model.DiasDaSemana;
import com.ipl.professorallocation.model.Professor;

import java.time.LocalTime;
import java.util.List;

public class AddEditAlocacaoProfessorActivity extends AppCompatActivity {

    private ActivityAddEditAlocacaoProfessorBinding binding;
    public static final String EXTRA_EDITAR_ALOCACAO = "editar_alocacao";
    private ArrayAdapter<Course> cursoArrayAdapter;
    private ArrayAdapter<DiasDaSemana> diasDaSemanaArrayAdapter;
    private ArrayAdapter<Professor> professorArrayAdapter;
    private CursoRepositorio cursoRepositorio;
    private ProfessorRepositorio professorRepositorio;
    private AlocacaoRepositorio alocacaoRepositorio;
    private MaterialTimePicker timePicker;
    private int campoDeHoraQueIniciouTimePick;
    private static final int HORA_INICIO = 0;
    private static final int HORA_FIM = 1;
    private Course cursoSelecionado;
    private Professor professorSelecionado;
    private DiasDaSemana diaDaSemanaSelecionado;

    private LocalTime timeInicoSelecionada;
    private LocalTime timeFimSelecionado;

    private AllocationsItem allocationsItemParaEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditAlocacaoProfessorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Adicionar Alocação");
        cursoRepositorio = new CursoRepositorio();
        professorRepositorio = new ProfessorRepositorio();
        alocacaoRepositorio = new AlocacaoRepositorio();
        setupOnClickListener();
        carregarAlocacaoParaEditar();
        setupSpinnerListaCurso();
        setupSpinnerListaProfessor();
        setupSpinnerDiaDaSemana();
        listarCursos();
        listarProfessores();
        setupCalendarioDatePicker();
    }

    private void carregarAlocacaoParaEditar() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            setTitle("Editar Alocação");
            AllocationsItem allocationsItem = (AllocationsItem) bundle.getSerializable(EXTRA_EDITAR_ALOCACAO);
            if (allocationsItem != null) {
                allocationsItemParaEditar = allocationsItem;
                timeInicoSelecionada = allocationsItemParaEditar.getStartHour();
                timeFimSelecionado = allocationsItemParaEditar.getEndHour();
                binding.horaDeInicio.setText(timeInicoSelecionada.toString());
                binding.horaFim.setText(timeFimSelecionado.toString());
            }
        }
    }

    private void setupCalendarioDatePicker() {
        // COmponente vidual que mostra o calendario para o usuário escolher uma data
        timePicker = new MaterialTimePicker
                .Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(10)
                .build();
        // Listener fica escutando o calendario e quando o usuário clica no botão ok ele
        // retorna a data em milisegundos
        timePicker.addOnPositiveButtonClickListener(selection -> {
            if (campoDeHoraQueIniciouTimePick == HORA_INICIO) {
                String timeInicio = timePicker.getHour() + ":" + timePicker.getMinute();
                timeInicoSelecionada = LocalTime.parse(timeInicio);
                binding.horaDeInicio.setText(timeInicio);
            } else {
                String timefim = timePicker.getHour() + ":" + timePicker.getMinute();
                timeFimSelecionado = LocalTime.parse(timefim);
                binding.horaFim.setText(timefim);
            }
        });
    }

    private void setupOnClickListener() {
        binding.salvarAlocacao.setOnClickListener(view -> {
            mostrarProgressBar(View.VISIBLE);
            AllocationRequest allocationRequest = getAllocationRequest();
            if(allocationsItemParaEditar != null) {
                editarAlocacao(allocationRequest);
            } else  {
                criarAlocacao(allocationRequest);
            }
        });
        binding.horaDeInicio.setOnClickListener(v -> {
            campoDeHoraQueIniciouTimePick = HORA_INICIO;
            timePicker.show(getSupportFragmentManager(), "TIME_PICKER");
        });
        binding.horaFim.setOnClickListener(v -> {
            campoDeHoraQueIniciouTimePick = HORA_FIM;
            timePicker.show(getSupportFragmentManager(), "TIME_PICKER");
        });
    }

    private void editarAlocacao(AllocationRequest allocationRequest) {
        alocacaoRepositorio.editarAlocacao(allocationsItemParaEditar.getId(), allocationRequest, new RespositorioCallBack<AllocationsItem>() {
            @Override
            public void onResponse(AllocationsItem response) {
                mostrarProgressBar(View.INVISIBLE);
                Toast.makeText(AddEditAlocacaoProfessorActivity.this, "Alocação Editada.", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                mostrarProgressBar(View.INVISIBLE);
                Log.d("IPL1", "onFailure: " + t);
            }
        });
    }

    private void criarAlocacao(AllocationRequest allocationRequest) {
        alocacaoRepositorio.criarAlocacao(allocationRequest, new RespositorioCallBack<AllocationsItem>() {
            @Override
            public void onResponse(AllocationsItem response) {
                mostrarProgressBar(View.INVISIBLE);
                Toast.makeText(AddEditAlocacaoProfessorActivity.this, "Alocação criado.", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                mostrarProgressBar(View.INVISIBLE);
                Log.d("IPL1", "onFailure: " + t);
            }
        });
    }

    @NonNull
    private AllocationRequest getAllocationRequest() {
        int cursoId = cursoSelecionado.getId();
        int professorId = professorSelecionado.getId();
        String diaDaSemana = diaDaSemanaSelecionado.name();

        AllocationRequest allocationRequest = new AllocationRequest(cursoId, diaDaSemana, timeInicoSelecionada, timeFimSelecionado, professorId);
        return allocationRequest;
    }

    private void setupSpinnerListaCurso() {
        cursoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.spinnerCurso.setAdapter(cursoArrayAdapter);
        binding.spinnerCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cursoSelecionado = cursoArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddEditAlocacaoProfessorActivity.this, "onNothingSelected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listarCursos() {
        cursoRepositorio.listarCursos(new RespositorioCallBack<List<Course>>() {
            @Override
            public void onResponse(List<Course> response) {
                cursoArrayAdapter.addAll(response);

                // Irá setar o departamento no spinner quando estiver editando o registro
                if (allocationsItemParaEditar != null) {
                    cursoSelecionado = allocationsItemParaEditar.getCourse();
                    int cursoPosition = cursoArrayAdapter.getPosition(cursoSelecionado);
                    binding.spinnerCurso.setSelection(cursoPosition, true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: " + t);
            }
        });
    }

    private void setupSpinnerDiaDaSemana() {
        diasDaSemanaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.spinnerDiaDaSemana.setAdapter(diasDaSemanaArrayAdapter);
        diasDaSemanaArrayAdapter.addAll(DiasDaSemana.listarDiasDaSemana());
        binding.spinnerDiaDaSemana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diaDaSemanaSelecionado = diasDaSemanaArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddEditAlocacaoProfessorActivity.this, "onNothingSelected", Toast.LENGTH_LONG).show();
            }
        });

        // Irá setar o departamento no spinner quando estiver editando um professor
        if (allocationsItemParaEditar != null) {
            diaDaSemanaSelecionado = DiasDaSemana.valueOf(allocationsItemParaEditar.getDayOfWeek());
            int departamentoPosition = diasDaSemanaArrayAdapter.getPosition(diaDaSemanaSelecionado);
            binding.spinnerDiaDaSemana.setSelection(departamentoPosition, true);
        }
    }

    private void setupSpinnerListaProfessor() {
        professorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.spinnerProfessor.setAdapter(professorArrayAdapter);
        binding.spinnerProfessor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                professorSelecionado = professorArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddEditAlocacaoProfessorActivity.this, "onNothingSelected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listarProfessores() {
        professorRepositorio.listarProfessores(new RespositorioCallBack<List<Professor>>() {
            @Override
            public void onResponse(List<Professor> response) {
                professorArrayAdapter.addAll(response);

                // Irá setar o departamento no spinner quando estiver editando o registro
                if (allocationsItemParaEditar != null) {
                    professorSelecionado = allocationsItemParaEditar.getProfessor();
                    int professorPosition = professorArrayAdapter.getPosition(professorSelecionado);
                    binding.spinnerProfessor.setSelection(professorPosition, true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: " + t);
            }
        });
    }

    private void mostrarProgressBar(int mostrarProgressBar) {
        binding.progressBar.setVisibility(mostrarProgressBar);
    }
}