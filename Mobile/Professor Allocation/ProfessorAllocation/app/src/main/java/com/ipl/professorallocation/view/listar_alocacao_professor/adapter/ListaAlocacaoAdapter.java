package com.ipl.professorallocation.view.listar_alocacao_professor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.professorallocation.databinding.ItemListaAlocacaoBinding;
import com.ipl.professorallocation.model.AllocationsItem;

import java.util.ArrayList;
import java.util.List;

public class ListaAlocacaoAdapter extends RecyclerView.Adapter<ListaAlocacaoAdapter.AlocationViewHolder> {

    List<AllocationsItem> usuarios;
    RecyclerViewCallback callback;

    public ListaAlocacaoAdapter(RecyclerViewCallback callback) {
        usuarios = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public AlocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListaAlocacaoBinding binding = ItemListaAlocacaoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AlocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlocationViewHolder holder, int position) {
        AllocationsItem alocacao = usuarios.get(position);
        holder.binding.professor.setText(alocacao.getProfessor().getName());
        holder.binding.horaInicioFim.setText(alocacao.getStartHour() +" - "+ alocacao.getEndHour());
        holder.binding.diaSemana.setText(alocacao.getDayOfWeek());
        holder.binding.departamento.setText(alocacao.getProfessor().getDepartment().getName());
        holder.binding.curso.setText(alocacao.getCourse().getName());
        holder.binding.excluirAlocacao.setOnClickListener(view -> {
            callback.onClickExcluirAlocacao(alocacao);
        });
        holder.binding.editarAlocacao.setOnClickListener(view -> {
            callback.onClickEditarAlocacao(alocacao);
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void addUsuario(AllocationsItem alocacao) {
        usuarios.add(alocacao);
        notifyDataSetChanged();
    }

    public void removerUsuario(AllocationsItem professor) {
        usuarios.remove(professor);
        notifyDataSetChanged();
    }

    public void addListaProfessor(List<AllocationsItem> listaProfessor) {
        usuarios.clear();
        usuarios.addAll(listaProfessor);
        notifyDataSetChanged();
    }

    public static class AlocationViewHolder extends RecyclerView.ViewHolder {
        private final ItemListaAlocacaoBinding binding;

        public AlocationViewHolder(@NonNull ItemListaAlocacaoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RecyclerViewCallback {
        void onClickExcluirAlocacao(AllocationsItem deletarAlocacao);
        void onClickEditarAlocacao(AllocationsItem editarAlocacaco);
    }
}
