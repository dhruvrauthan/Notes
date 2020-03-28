package com.example.notesapp.async;

import android.os.AsyncTask;

import com.example.notesapp.models.Note;
import com.example.notesapp.persistence.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao mNoteDao;

    public UpdateAsyncTask(NoteDao noteDao) {

        this.mNoteDao=noteDao;

    }

    @Override
    protected Void doInBackground(Note... notes) {

        mNoteDao.update(notes);
        return null;

    }
}
