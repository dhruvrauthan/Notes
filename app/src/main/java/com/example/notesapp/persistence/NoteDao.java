package com.example.notesapp.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesapp.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long[] insert (Note... notes);  //returns long array that contains all rows inserted in database

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Delete
    int delete(Note... notes);  //returns number of rows that were updated/deleted

    @Update
    int update(Note... notes); //returns number of rows affected by update

}
