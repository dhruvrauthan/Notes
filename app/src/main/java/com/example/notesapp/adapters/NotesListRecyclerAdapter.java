package com.example.notesapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.models.Note;

import java.util.ArrayList;

public class NotesListRecyclerAdapter extends RecyclerView.Adapter<NotesListRecyclerAdapter.ViewHolder> {

    private ArrayList<Note> mNotesList;
    private OnNoteListener mOnNoteListener;

    public NotesListRecyclerAdapter(ArrayList<Note> notesList, OnNoteListener onNoteListener) {

        this.mNotesList = notesList;
        this.mOnNoteListener = onNoteListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(mNotesList.get(position).getTitle());
        holder.content.setText(mNotesList.get(position).getContent());

    }

    @Override
    public int getItemCount() {

        return mNotesList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView content;
        private OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {

            super(itemView);
            title = itemView.findViewById(R.id.title_textview);
            content = itemView.findViewById(R.id.content_textview);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            onNoteListener.onNoteClick(getAdapterPosition());

        }

    }

    public interface OnNoteListener {

        void onNoteClick(int position);

    }

}
