package com.example.taskmaster1;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster1.Task;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>{
    List<com.amplifyframework.datastore.generated.model.Task> tasksList;
    CustomClickListener listener;

    public CustomRecyclerViewAdapter(List<com.amplifyframework.datastore.generated.model.Task> tasksList, CustomClickListener listener) {
        this.tasksList = tasksList;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setText(tasksList.get(position).getTitle());
        holder.body.setText(tasksList.get(position).getDescription());
        holder.state.setText(tasksList.get(position).getStatus().toString());
    }

    @NonNull
    @Override
    public CustomViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.activity_task, parent, false);
        return new CustomViewHolder(listItemView, listener);
    }




    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public interface CustomClickListener {
        void onTaskClicked(int position);
    }


    static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView body;
        TextView state;

        CustomClickListener listener;

        public CustomViewHolder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);

            this.listener = listener;

            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            state = itemView.findViewById(R.id.state);

            itemView.setOnClickListener(view -> listener.onTaskClicked(getAdapterPosition()));
        }
    }


}