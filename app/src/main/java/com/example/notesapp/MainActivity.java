package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.example.notesapp.adapters.NotesListRecyclerAdapter;
import com.example.notesapp.models.Note;
import com.example.notesapp.persistence.NoteRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListRecyclerAdapter.OnNoteListener,
        View.OnClickListener {

    //UI
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddNoteFab;

    //Variables
    public static ArrayList<Note> mNotesList;
    private NotesListRecyclerAdapter mRecyclerAdapter;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        mAddNoteFab = findViewById(R.id.add_note_fab);
        mNotesList = new ArrayList<>();
        mNoteRepository = new NoteRepository(this);

        initRecyclerView();
        retrieveNotes();

        setSupportActionBar((Toolbar) findViewById(R.id.notes_list_toolbar));

        mAddNoteFab.setOnClickListener(this);

    }

    private void retrieveNotes() {       //used to observe changes to live data object/database

        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) { //re query list of notes

                if (mNotesList.size() > 0) {

                    mNotesList.clear();
                }

                if (notes != null) {

                    mNotesList.addAll(notes);

                }

                mRecyclerAdapter.notifyDataSetChanged();


            }
        });

    }

    private void initRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerAdapter = new NotesListRecyclerAdapter(mNotesList, this);
        mRecyclerView.setAdapter(mRecyclerAdapter);

    }


    @Override
    public void onNoteClick(int position) {

        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("note", (Parcelable) mNotesList.get(position));
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

    }

}
