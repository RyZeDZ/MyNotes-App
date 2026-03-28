package com.example.mynotes;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public int getItemViewType(int position) {
        return notes.get(position) instanceof NoteAction ? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_note_item_action, parent, false);
            return new NoteActionViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_note_item_labels, parent, false);
            return new NoteLabelViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Note note = notes.get(position);

        if (holder instanceof NoteActionViewHolder) {
            NoteActionViewHolder h = (NoteActionViewHolder) holder;
            NoteAction noteAction = (NoteAction) note;
            h.title.setText(noteAction.title);
            h.subtitle.setText(noteAction.subtitle);
            h.date.setText(noteAction.date);
            h.date.setTextColor(ContextCompat.getColor(h.itemView.getContext(), noteAction.dateColorResId));
            h.leftBorder.getBackground().setTint(
                    ContextCompat.getColor(h.itemView.getContext(), noteAction.colorResId));
            int btnColor = ContextCompat.getColor(h.itemView.getContext(), noteAction.buttonColorResId);
            h.btnAction.setTextColor(btnColor);
            h.btnAction.setStrokeColor(ColorStateList.valueOf(btnColor));
            h.btnAction.setBackgroundTintList(ColorStateList.valueOf(btnColor).withAlpha(30));

        } else if (holder instanceof NoteLabelViewHolder) {
            NoteLabelViewHolder h = (NoteLabelViewHolder) holder;
            NoteLabel noteLabel = (NoteLabel) note;
            h.title.setText(noteLabel.title);
            h.subtitle.setText(noteLabel.subtitle);
            h.date.setText(noteLabel.date);
            h.date.setTextColor(ContextCompat.getColor(h.itemView.getContext(), noteLabel.dateColorResId));
            h.leftBorder.getBackground().setTint(
                    ContextCompat.getColor(h.itemView.getContext(), noteLabel.colorResId));
            h.chipGroup.removeAllViews();
            for (int i = 0; i < noteLabel.topics.size(); i++) {
                Chip chip = new Chip(h.itemView.getContext());
                int chipColor = ContextCompat.getColor(h.itemView.getContext(), noteLabel.topicColors.get(i));
                int transparentColor = Color.argb(30, Color.red(chipColor), Color.green(chipColor), Color.blue(chipColor));
                chip.setText(noteLabel.topics.get(i));
                chip.setTextColor(chipColor);
                chip.setChipStrokeColor(ColorStateList.valueOf(0));
                chip.setChipBackgroundColor(ColorStateList.valueOf(
                        Color.argb(50, Color.red(chipColor), Color.green(chipColor), Color.blue(chipColor))
                ));
                chip.setClickable(false);
                h.chipGroup.addView(chip);
            }
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteActionViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, date;
        View leftBorder;
        MaterialButton btnAction;

        public NoteActionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNoteActionTitle);
            subtitle = itemView.findViewById(R.id.tvNoteActionSubtitle);
            date = itemView.findViewById(R.id.tvNoteActionDate);
            leftBorder = itemView.findViewById(R.id.noteActionLeftBorder);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }

    static class NoteLabelViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, date;
        View leftBorder;
        ChipGroup chipGroup;

        public NoteLabelViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNoteLabelTitle);
            subtitle = itemView.findViewById(R.id.tvNoteLabelSubtitle);
            date = itemView.findViewById(R.id.tvNoteLabelDate);
            leftBorder = itemView.findViewById(R.id.noteLabelLeftBorder);
            chipGroup = itemView.findViewById(R.id.chipGroup);
        }
    }
}
