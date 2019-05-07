package com.geektech.taskapp4.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.geektech.taskapp4.App;
import com.geektech.taskapp4.R;
import com.geektech.taskapp4.TaskActivity;
import com.geektech.taskapp4.TaskAdapter;
import com.geektech.taskapp4.room.Task;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    RecyclerView recyclerView;
    private List<Task> list;
    private TaskAdapter taskAdapter;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.my_recyclerView);
        initList();
        return root;
    }

    public void initList() {
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(list);
        recyclerView.setAdapter(taskAdapter);
        App.getInstance().getDatabase().taskDao().getAll().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                taskAdapter.notifyDataSetChanged();
            }
        });
        taskAdapter.setClickListener(new TaskAdapter.ClickListener() {
            @Override
            public void onClick(int pos) {
                Task task = list.get(pos);
                Intent intent = new Intent(getContext(), TaskActivity.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int pos) {
                Task task = list.get(pos);
                showAlert(task);
            }
        });
    }

    private void showAlert(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(task.getTitle());
        builder.setMessage("Do you want to delete this task?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getInstance()
                        .getDatabase().taskDao().delete(task);
            }
        });
        builder.create().show();
    }
}
