package com.example.mynotes;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TOPIC = 0;
    private static final int TYPE_FOOTER = 1;

    List<Topic> topics;
    private DeleteCallback deleteCallback;

    public interface DeleteCallback {
        void onDelete(int position);
    }

    public TopicAdapter(List<Topic> topics, DeleteCallback deleteCallback) {
        this.topics = topics;
        this.deleteCallback = deleteCallback;
    }

    @Override
    public int getItemViewType(int position) {
        return position == topics.size() ? TYPE_FOOTER : TYPE_TOPIC;
    }

    @Override
    public int getItemCount() { return topics.size() + 1; }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOPIC) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_topic_item, parent, false);
            return new TopicViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_topic_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopicViewHolder) {
            TopicViewHolder h = (TopicViewHolder) holder;
            Topic topic = topics.get(position);
            h.topicName.setText(topic.name);
            h.topicCount.setText(holder.itemView.getContext()
                    .getString(R.string.topic_notes_count, topic.noteCount));
            h.topicIcon.setText(topic.emoji);
            h.topicIcon.getBackground().setTint(
                    ContextCompat.getColor(holder.itemView.getContext(), topic.iconBgColor));

            h.ibMore.setOnClickListener(v -> {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete topic?")
                        .setMessage("\"" + topic.name + "\" will be deleted.")
                        .setPositiveButton("Delete", (d, w) -> {
                            if (deleteCallback != null) deleteCallback.onDelete(holder.getAdapterPosition());
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });

        } else if (holder instanceof FooterViewHolder) {
            holder.itemView.setOnClickListener(v -> {
                if (v.getContext() instanceof TopicsActivity) {
                    ((TopicsActivity) v.getContext()).showCreateTopicDialog();
                }
            });
        }
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView topicName, topicCount, topicIcon;
        ImageButton ibMore;
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.tvTopicName);
            topicCount = itemView.findViewById(R.id.tvTopicCount);
            topicIcon = itemView.findViewById(R.id.tvTopicIcon);
            ibMore = itemView.findViewById(R.id.ibTopicMore);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) { super(itemView); }
    }
}