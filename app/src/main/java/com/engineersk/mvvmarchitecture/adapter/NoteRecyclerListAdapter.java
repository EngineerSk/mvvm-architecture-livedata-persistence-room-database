package com.engineersk.mvvmarchitecture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.engineersk.mvvmarchitecture.R;
import com.engineersk.mvvmarchitecture.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerListAdapter
        extends ListAdapter<Note, NoteRecyclerListAdapter.NoteViewHolder> {

    private OnItemClickListener mListener;

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };

    public NoteRecyclerListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = this.getItem(position);

        holder.mNoteTitleTextView.setText(currentNote.getTitle());
        holder.mNoteDescriptionTextView.setText(currentNote.getDescription());
        holder.mNotePriorityTextView.setText(String.valueOf(currentNote.getPriority()));
    }

    public Note getNoteAt(int position) {
        return this.getItem(position);
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

                if (NoteRecyclerListAdapter.this.mListener != null
                        && NoteViewHolder.this.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    NoteRecyclerListAdapter.this.mListener.onItemClick(
                            NoteRecyclerListAdapter.this.getItem(
                                    NoteViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
