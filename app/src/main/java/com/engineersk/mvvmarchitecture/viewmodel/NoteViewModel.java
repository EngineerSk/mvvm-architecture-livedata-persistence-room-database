package com.engineersk.mvvmarchitecture.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.engineersk.mvvmarchitecture.model.Note;
import com.engineersk.mvvmarchitecture.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository mNoteRepository;
    private final LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.mNoteRepository = new NoteRepository(application);
        this.mAllNotes = this.mNoteRepository.getAllNotes();
    }

    public void insert(Note note){
        this.mNoteRepository.insert(note);
    }

    public void update(Note note){
        this.mNoteRepository.update(note);
    }

    public void delete(Note note){
        this.mNoteRepository.delete(note);
    }

    public void deleteAllNotes(){
        this.mNoteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return this.mAllNotes;
    }
}
