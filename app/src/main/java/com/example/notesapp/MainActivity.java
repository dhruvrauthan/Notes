package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;

import com.example.notesapp.adapters.NotesListRecyclerAdapter;
import com.example.notesapp.models.Note;
import com.example.notesapp.persistence.NoteRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListRecyclerAdapter.OnNoteListener,
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //UI
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddNoteFab;
    private DrawerLayout mDrawerLayout;
    private Toolbar mMainToolbar;
    private NavigationView mNavigationView;

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
        mDrawerLayout=findViewById(R.id.drawer_layout);
        mMainToolbar=findViewById(R.id.notes_list_toolbar);
        mNavigationView=findViewById(R.id.nav_view);

        mNotesList = new ArrayList<>();
        mNoteRepository = new NoteRepository(this);

        initRecyclerView();
        drawerToggle();
        retrieveNotes();

        setSupportActionBar(mMainToolbar);

        mAddNoteFab.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){

            mDrawerLayout.closeDrawer(GravityCompat.START);

        }else{

            super.onBackPressed();

        }
    }

    private void drawerToggle() {

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, mDrawerLayout, mMainToolbar,
                R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            mDrawerLayout.openDrawer(GravityCompat.START);

        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.notes_nav_menu:{

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            }

            case R.id.settings_nav_menu:{

                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            }

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
