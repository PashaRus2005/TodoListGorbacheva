package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> todoList;
    private ArrayAdapter<String> adapter;
    private EditText taskEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskEditText = findViewById(R.id.taskEditText);
        ListView taskListView = findViewById(R.id.taskListView);

        todoList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoList);
        taskListView.setAdapter(adapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTask(position);
            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTask(position);
                return true;
            }
        });
    }

    public void addTask(View view) {
        String task = taskEditText.getText().toString();
        if (!task.isEmpty()) {
            todoList.add(task);
            adapter.notifyDataSetChanged();
            taskEditText.setText("");
        }
    }

    private void editTask(int position) {
        String task = todoList.get(position);
        taskEditText.setText(task);
        taskEditText.setSelection(task.length());
        todoList.remove(position);
        adapter.notifyDataSetChanged();
    }

    private void deleteTask(int position) {
        todoList.remove(position);
        adapter.notifyDataSetChanged();
    }
}