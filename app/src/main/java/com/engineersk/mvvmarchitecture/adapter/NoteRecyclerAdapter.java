package com.engineersk.mvvmarchitecture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.engineersk.mvvmarchitecture.R;
import com.engineersk.mvvmarchitecture.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder> {

    private List<Note> mNotes = new ArrayList<>();
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Note currentNote = this.mNotes.get(position);

        holder.mNoteTitleTextView.setText(currentNote.getTitle());
        holder.mNoteDescriptionTextView.setText(currentNote.getDescription());
        holder.mNotePriorityTextView.setText(String.valueOf(currentNote.getPriority()));

    }

    @Override
    public int getItemCount() {
        return this.mNotes.size();
    }

    public void setNotes(List<Note> notes){
        this.mNotes = notes;
        this.notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return this.mNotes.get(position);
    }

    protected class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNoteTitleTextView;
        private final TextView mNoteDescriptionTextView;
        private final TextView mNotePriorityTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mNoteTitleTextView = itemView.findViewById(R.id.text_view_title);
            mNoteDescriptionTextView = itemView.findViewById(R.id.text_view_description);
            mNotePriorityTextView = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(v -> {

                if(NoteRecyclerAdapter.this.mListener != null
                        && NoteViewHolder.this.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    NoteRecyclerAdapter.this.mListener.onItemClick(
                            NoteRecyclerAdapter.this.mNotes
                                    .get(NoteViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
