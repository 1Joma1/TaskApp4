package com.geektech.taskapp4;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.geektech.taskapp4.room.Task;

import java.util.Calendar;

public class TaskActivity extends AppCompatActivity {

    TextView etTitle, etDes;
    long time;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        etTitle = findViewById(R.id.etTitle);
        etDes = findViewById(R.id.etDes);

        Task task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            etTitle.setText(task.getTitle());
            etDes.setText(task.getDesc());
        }

    }

    public void onClickSave(View view) {
        String title = etTitle.getText().toString().trim();
        String desc = etDes.getText().toString().trim();
        if (time == 0) time = System.currentTimeMillis();
        if (task != null) {
            task.setTitle(title);
            task.setDesc(desc);
            task.setTime(time);
            App.getInstance().getDatabase().taskDao().update(task);
        } else {
            Task task = new Task();
            task.setTitle(title);
            task.setDesc(desc);
            task.setTime(time);
            App.getInstance().getDatabase().taskDao().insert(task);
            finish();
        }
    }

    public void onClickDate(View view) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog picker = new DatePickerDialog(this, 0, null,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        picker.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int d = picker.getDatePicker().getDayOfMonth();
                int m = picker.getDatePicker().getMonth();
                int y = picker.getDatePicker().getYear();
                Calendar c = Calendar.getInstance();
                c.set(y, m, d);
                time = c.getTimeInMillis();
            }
        });
        picker.show();
    }
}
