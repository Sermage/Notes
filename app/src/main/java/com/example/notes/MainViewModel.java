package com.example.notes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class MainViewModel extends AndroidViewModel {
    private static NotesDatabase database;
    private LiveData<List<Note>> notes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database=NotesDatabase.getInstance(getApplication());
        notes=database.notesDao().getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void InsertNote(Note note){
        new InsertTask().execute(note);
    }

    public void DeleteNote(Note note){
        new DeleteTask().execute(note);
    }
    public void DeleteAllNote(){
        new DeleteAllTask().execute();
    }

    public static class InsertTask extends AsyncTask<Note,Void,Void>{

        @Override
        protected Void doInBackground(Note... notes) {
            if(notes!=null && notes.length>0){
                database.notesDao().InsertNote(notes[0]);
            }
            return null;
        }
    }

    public static class DeleteTask extends AsyncTask<Note,Void,Void>{

        @Override
        protected Void doInBackground(Note... notes) {
            if(notes!=null && notes.length>0){
                database.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    public static class DeleteAllTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... notes) {
            database.notesDao().deleteAllNotes();
            return null;
        }
    }



}
