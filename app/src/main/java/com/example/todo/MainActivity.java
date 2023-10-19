package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todo.Adapter.todoAdapter;
import com.example.todo.Model.todoModel;
import com.example.todo.Utils.dbHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView rvTasks;
    private todoAdapter taskAdp;
    private FloatingActionButton fab;

    private List<todoModel> taskList;
    private dbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new dbHandler(this);
        db.openDatabase();

//        taskList = new ArrayList<>();
        rvTasks = findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        taskAdp = new todoAdapter(db,this);
        rvTasks.setAdapter(taskAdp);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdp));
        itemTouchHelper.attachToRecyclerView(rvTasks);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdp.setTasks(taskList);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdp.setTasks(taskList);
        taskAdp.notifyDataSetChanged();
    }
}