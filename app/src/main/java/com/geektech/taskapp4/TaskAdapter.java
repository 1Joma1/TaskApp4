package com.geektech.taskapp4;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geektech.taskapp4.room.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    List<Task> list;
    private ClickListener clickListener;

    public interface ClickListener{
        void onClick(int pos);
        void onLongClick(int pos);
    }

    public TaskAdapter(List<Task> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_task, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textDesc, textTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tvtitle);
            textDesc = itemView.findViewById(R.id.tvdes);
            textTime = itemView.findViewById(R.id.tvtime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(Task task) {
            textTitle.setText((task.getTitle()));
            textDesc.setText((task.getDesc()));
            String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    .format(new Date(task.getTime()));
            textTime.setText(date);
        }
    }
}
