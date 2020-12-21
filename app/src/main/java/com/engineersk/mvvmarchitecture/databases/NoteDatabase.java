package com.engineersk.mvvmarchitecture.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.engineersk.mvvmarchitecture.model.Note;
import com.engineersk.mvvmarchitecture.model.NoteDao;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase sInstance;
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(sRoomDatabaseCallBack)
                    .build();
        }

        return sInstance;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(sInstance).execute();
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void>{

        private final NoteDao mNoteDao;

        private PopulateDatabaseAsyncTask(NoteDatabase noteDatabase) {
            this.mNoteDao = noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.mNoteDao.insert(new Note("Title 1", "Description 1", 1));
            this.mNoteDao.insert(new Note("Title 2", "Description 2", 2));
            this.mNoteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}
