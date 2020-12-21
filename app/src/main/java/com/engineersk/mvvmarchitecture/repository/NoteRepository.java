package com.engineersk.mvvmarchitecture.repository;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.engineersk.mvvmarchitecture.databases.NoteDatabase;
import com.engineersk.mvvmarchitecture.model.Note;
import com.engineersk.mvvmarchitecture.model.NoteDao;

import java.util.List;

public class NoteRepository {

    private final NoteDao mNoteDao;
    private final LiveData<List<Note>> mAllNotes;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        this.mNoteDao = noteDatabase.noteDao();
        this.mAllNotes = this.mNoteDao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(this.mNoteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(this.mNoteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(this.mNoteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(this.mNoteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return this.mAllNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private final NoteDao mNoteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.mNoteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private final NoteDao mNoteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.mNoteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private final NoteDao mNoteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.mNoteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{

        private final NoteDao mNoteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.mNoteDao.deleteAllNotes();
            return null;
        }
    }
}
