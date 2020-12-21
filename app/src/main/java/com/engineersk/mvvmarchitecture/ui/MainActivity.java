package com.engineersk.mvvmarchitecture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.engineersk.mvvmarchitecture.R;
import com.engineersk.mvvmarchitecture.adapter.NoteRecyclerListAdapter;
import com.engineersk.mvvmarchitecture.model.Note;
import com.engineersk.mvvmarchitecture.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
        implements NoteRecyclerListAdapter.OnItemClickListener{

    public static final int ADD_NOTE_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_REQUEST_CODE = 2;
    private NoteViewModel mNoteViewModel;
    private RecyclerView mRecyclerView;
    private NoteRecyclerListAdapter mNoteRecyclerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.initializeRecyclerView();

        FloatingActionButton addNoteButton = findViewById(R.id.add_note_fab);
        addNoteButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditNoteActivity.class);
            this.startActivityForResult(intent, ADD_NOTE_REQUEST_CODE);
        });

        this.mNoteViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);
        this.mNoteViewModel.getAllNotes().observe(this,
                notes -> {
                    Toast.makeText(MainActivity.this, "On changed",
                            Toast.LENGTH_SHORT).show();

                    this.mNoteRecyclerListAdapter.submitList(notes);
                });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MainActivity.this.mNoteViewModel
                        .delete(MainActivity.this.mNoteRecyclerListAdapter
                                .getNoteAt(viewHolder.getAdapterPosition()));

                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT)
                        .show();
            }
        }).attachToRecyclerView(this.mRecyclerView);

        mNoteRecyclerListAdapter.setOnItemClickListener(this);
    }

    private void initializeRecyclerView() {

        this.mRecyclerView = findViewById(R.id.recycler_view);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRecyclerView.setHasFixedSize(true);

        this.mNoteRecyclerListAdapter = new NoteRecyclerListAdapter();
        this.mRecyclerView.setAdapter(this.mNoteRecyclerListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((resultCode == RESULT_OK) && data != null) {

            if (requestCode == ADD_NOTE_REQUEST_CODE) {

                String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

                Note note = new Note(title, description, priority);
                this.mNoteViewModel.insert(note);

                Toast.makeText(this, "Note Saved!!!", Toast.LENGTH_SHORT).show();

            }

            else if(requestCode == EDIT_NOTE_REQUEST_CODE){

                int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

                if(id == -1) {
                    Toast.makeText(this, "Error retrieving note", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                    String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                    int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

                    Note note = new Note(title, description, priority);
                    note.setId(id);
                    this.mNoteViewModel.update(note);
                    Toast.makeText(this, "Note Updated!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        else
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_delete_all_notes){
            this.mNoteViewModel.deleteAllNotes();
            Toast.makeText(this,"All notes deleted", Toast.LENGTH_SHORT).show();
        }
        else
            return super.onOptionsItemSelected(item);

        return true;
    }

    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(this, AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId())
                .putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle())
                .putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription())
                .putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());

        this.startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);

    }
}