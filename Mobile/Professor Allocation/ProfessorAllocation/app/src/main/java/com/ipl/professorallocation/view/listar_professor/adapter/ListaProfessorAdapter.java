package com.ipl.professorallocation.view.listar_professor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.professorallocation.databinding.ItemListaProfessorBinding;
import com.ipl.professorallocation.model.Professor;

import java.util.ArrayList;
import java.util.List;

public class ListaProfessorAdapter extends RecyclerView.Adapter<ListaProfessorAdapter.ProfessorViewHolder> {

    List<Professor> usuarios;
    RecyclerViewCallback callback;

    public ListaProfessorAdapter(RecyclerViewCallback callback) {
        usuarios = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override
    public ProfessorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListaProfessorBinding binding = ItemListaProfessorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProfessorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorViewHolder holder, int position) {
        Professor professor = usuarios.get(position);
        holder.binding.nomeProfessor.setText(professor.getName());
        holder.binding.departamentoProfessor.setText(professor.getDepartment().getName());
        holder.binding.cpfProfessor.setText(professor.getCpf());
        holder.binding.excluirUsuario.setOnClickListener(view -> {
            callback.onClickExcluirUsuario(professor);
        });
        holder.binding.editarProfessor.setOnClickListener(view -> {
            callback.onClickEditarUsuario(professor);
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void addUsuario(Professor professor) {
        usuarios.add(professor);
        notifyDataSetChanged();
    }

    public void removerUsuario(Professor professor) {
        usuarios.remove(professor);
        notifyDataSetChanged();
    }

    public void addListaProfessor(List<Professor> listaProfessor) {
        usuarios.clear();
        usuarios.addAll(listaProfessor);
        notifyDataSetChanged();
    }

    public static class ProfessorViewHolder extends RecyclerView.ViewHolder {
        private final ItemListaProfessorBinding binding;

        public ProfessorViewHolder(@NonNull ItemListaProfessorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RecyclerViewCallback {
        void onClickExcluirUsuario(Professor deletarProfessor);
        void onClickEditarUsuario(Professor editarProfessor);
    }
}
