package com.example.taskmaster1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//
//public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>{
//
//    List<Task> dataList;
//    CustomClickListener listener;
//
//    public CustomRecyclerViewAdapter(List<Task> tasksList, CustomClickListener listener) {
//        this.dataList = tasksList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        return null;
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View listItemView = layoutInflater.inflate(R.layout.activity_task ,parent , false);
//        return new CustomViewHolder(listItemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CustomRecyclerViewAdapter.CustomViewHolder holder, int position) {
//
//        holder.title.setText(dataList.get(position).getTitle());
//        holder.body.setText(dataList.get(position).getBody());
//        holder.state.setText(dataList.get(position).getState());
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    public interface CustomClickListener {
//        void onTaskClicked(int position);
//    }
//
//    public class CustomViewHolder extends RecyclerView.ViewHolder{
//        TextView title ;
//        TextView body ;
//        TextView state;
//        CustomClickListener listener;
//        public CustomViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.listener = listener;
//            title = itemView.findViewById(R.id.title);
//            body = itemView.findViewById(R.id.body);
//            state = itemView.findViewById(R.id.state);
//            itemView.setOnClickListener(view -> listener.onTaskClicked(getAdapterPosition()));
//        }
//
//    }
//}


public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder> {

    List<Task> tasksList;
    CustomClickListener listener;

    public CustomRecyclerViewAdapter(List<Task> tasksList, CustomClickListener listener) {
        this.tasksList = tasksList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // task Layout has the three elements
        View listItemView = layoutInflater.inflate(R.layout.activity_task, parent, false);
        return new CustomViewHolder(listItemView, listener);
    }

    // will be called multiple times to inject the data into the view holder object
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setText(tasksList.get(position).getTitle());
        holder.body.setText(tasksList.get(position).getBody());
        holder.state.setText(tasksList.get(position).getState().toString());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public interface CustomClickListener {

        void onTaskClicked(int position);
    }

    // CustomViewHolder //
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