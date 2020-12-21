package com.engineersk.mvvmarchitecture.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String mTitle;
    private final String mDescription;
    private final int mPriority;

    public Note(String title, String description, int priority) {
        this.mTitle = title;
        this.mDescription = description;
        this.mPriority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getPriority() {
        return this.mPriority;
    }


}
