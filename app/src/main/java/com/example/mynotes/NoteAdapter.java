package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.List;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_note_item_action, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.title);
        holder.subtitle.setText(note.subtitle);
        holder.date.setText(note.date);
        holder.leftBorder.getBackground().setTint(ContextCompat.getColor(holder.itemView.getContext(), note.colorResId));
        int btnColor = ContextCompat.getColor(holder.itemView.getContext(), note.buttonColorResId);
        holder.btnAction.setTextColor(btnColor);
        holder.btnAction.setStrokeColor(ColorStateList.valueOf(btnColor));
        holder.btnAction.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), note.buttonColorResId)).withAlpha(30));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, date;
        View leftBorder;
        MaterialButton btnAction;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNoteTitle);
            subtitle = itemView.findViewById(R.id.tvNoteSubtitle);
            date = itemView.findViewById(R.id.tvNoteDate);
            leftBorder = itemView.findViewById(R.id.leftBorder);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}
