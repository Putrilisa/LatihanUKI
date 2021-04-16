package com.putri.latihanuki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ToDo> list = new ArrayList<ToDo>();
    TodoAdapter todoAdapter;
    DatabaseHelper myDb;
    RecyclerView rvTodo;
    FloatingActionButton fabadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        list.addAll(myDb.getAllData());
        rvTodo = findViewById(R.id.rvTodo);
        fabadd = findViewById(R.id.fab);
        rvTodo.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter(MainActivity.this,list);
        todoAdapter.notifyDataSetChanged();
        rvTodo.setAdapter(todoAdapter);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTodo.class);
                startActivity(intent);
            }
        });
    }
}