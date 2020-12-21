package com.engineersk.mvvmarchitecture.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.engineersk.mvvmarchitecture.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.engineersk.mvvmarchitecture.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.engineersk.mvvmarchitecture.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION
            = "com.engineersk.mvvmarchitecture.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.engineersk.mvvmarchitecture.EXTRA_PRIORITY";

    private TextInputEditText mTitleTextInputEditText;
    private TextInputEditText mDescriptionTextInputEditText;
    private NumberPicker mPriorityNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_note);

        this.mTitleTextInputEditText = findViewById(R.id.edit_text_title);
        this.mDescriptionTextInputEditText = findViewById(R.id.edit_text_description);
        this.mPriorityNumberPicker = findViewById(R.id.number_picker_priority);

        this.mPriorityNumberPicker.setMinValue(1);
        this.mPriorityNumberPicker.setMaxValue(10);

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        Intent intent = this.getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            this.setTitle("Edit Note");
            mTitleTextInputEditText.setText(intent.getStringExtra(EXTRA_TITLE));
            mDescriptionTextInputEditText.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            mPriorityNumberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }else
            this.setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_save_note)
            this.saveNote();

        else
            return super.onOptionsItemSelected(item);

        return true;
    }

    private void saveNote() {
        String title = this.mTitleTextInputEditText.getText().toString();
        String description = this.mDescriptionTextInputEditText.getText().toString();
        int priority = this.mPriorityNumberPicker.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title and description",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = this.getIntent().getIntExtra(EXTRA_ID, -1);

        if(id != -1)
            data.putExtra(EXTRA_ID, id);

        this.setResult(RESULT_OK, data);
        this.finish();
    }
}