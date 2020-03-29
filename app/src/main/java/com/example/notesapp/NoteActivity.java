package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.notesapp.models.Note;
import com.example.notesapp.persistence.NoteRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    private static final String TAG = "NoteActivity";

    //UI
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private ImageButton mBackArrowButton;
    private Button mSaveButton;
    private Toolbar mNoteToolbar;

    //Variables
    private Note mInitialNote;
    private Note mFinalNote;
    private boolean mIsNewNote;
    private NoteRepository mNoteRepository;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mTitleEditText = findViewById(R.id.title_edittext);
        mContentEditText = findViewById(R.id.content_edittext);
        mBackArrowButton = findViewById(R.id.note_toolbar_back_arrow_button);
        mSaveButton = findViewById(R.id.save_button);
        mNoteToolbar=findViewById(R.id.note_toolbar);

        setSupportActionBar(mNoteToolbar);

        mNoteRepository = new NoteRepository(this);

        mBackArrowButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);

        if (getIncomingIntent()) {

            setNewNoteProperties();
            enableEditMode();

        } else {

            setNoteProperties();
            disableContentInteraction();

        }

    }

    private void enableEditMode() {

        mMode = EDIT_MODE_ENABLED;
        enableContentInteraction();

    }

    private void disableEditMode() {

        Log.d(TAG, "disableEditMode: called");
        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();

        if (mTitleEditText.getText().toString().trim().length() != 0 && mContentEditText.getText().toString().trim().length() != 0) {

            mFinalNote.setTitle(mTitleEditText.getText().toString());
            mFinalNote.setContent(mContentEditText.getText().toString());
            mFinalNote.setTimestamp(getDate());

            Log.d(TAG, "disableEditMode: initial=" + mInitialNote.toString());
            Log.d(TAG, "disableEditMode: final=" + mFinalNote.toString());

            if(!mFinalNote.getContent().equals(mInitialNote.getContent())||!mFinalNote.getTitle().equals(mInitialNote.getTitle())){

                Log.d(TAG, "saved note");
                saveChanges();

            }

        }

    }

    private String getDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd Mmm yy");
        return sdf.format(calendar.getTime());

    }

    private void enableContentInteraction() {

        mContentEditText.setKeyListener(new EditText(this).getKeyListener());
        mContentEditText.setFocusable(true);
        mContentEditText.setFocusableInTouchMode(true);
        mContentEditText.setCursorVisible(true);
        mContentEditText.requestFocus();

    }

    private void disableContentInteraction() {

        /*mContentEditText.setKeyListener(null);
        mContentEditText.setFocusable(false);
        mContentEditText.setFocusableInTouchMode(false);
        mContentEditText.setCursorVisible(false);
        mContentEditText.clearFocus();*/

    }


    private boolean getIncomingIntent() {

        if (getIntent().hasExtra("note")) {

            mInitialNote = getIntent().getParcelableExtra("note");

            mFinalNote = new Note();
            mFinalNote.setId(mInitialNote.getId());
            mFinalNote.setTitle(mInitialNote.getTitle());
            mFinalNote.setContent(mInitialNote.getContent());
            mFinalNote.setTimestamp(mInitialNote.getTimestamp());

            mMode = EDIT_MODE_ENABLED;
            mIsNewNote = false;
            return false;

        }

        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;

    }

    private void saveChanges() {

        if (mIsNewNote) {

            mNoteRepository.insertNoteTask(mFinalNote);       //if it is a new note

        } else {

            mNoteRepository.updateNote(mFinalNote);

        }

    }

    private void setNoteProperties() {

        mTitleEditText.setText(mInitialNote.getTitle());
        mContentEditText.setText(mInitialNote.getContent());

    }

    private void setNewNoteProperties() {

        mFinalNote = new Note();
        mInitialNote = new Note();

        mTitleEditText.setText("Title");
        mContentEditText.setText(null);

        mInitialNote.setTitle("Title");
        mInitialNote.setContent(null);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.title_edittext: {

                mTitleEditText.requestFocus();
                mTitleEditText.setSelection(mTitleEditText.length());
                break;

            }

            case R.id.content_edittext: {

                mContentEditText.requestFocus();
                mContentEditText.setSelection(mContentEditText.length());
                break;

            }

            case R.id.note_toolbar_back_arrow_button: {

                finish();
                break;

            }

            case R.id.save_button: {

                disableEditMode();
                break;

            }

        }

    }

    @Override
    public void onBackPressed() {

        if (mMode == EDIT_MODE_ENABLED) {

            onClick(mSaveButton);

        } else {

            super.onBackPressed();

        }

    }
}
