package com.example.todoapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    MyDatabaseHelper myDB;
    ArrayList<String> user_id, user_name, user_username, user_password, user_role;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.textViewName);

        myDB = new MyDatabaseHelper(AdminActivity.this);
        user_id = new ArrayList<>();
        user_name = new ArrayList<>();
        user_username = new ArrayList<>();
        user_password = new ArrayList<>();
        user_role = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(AdminActivity.this, user_id, user_name, user_username, user_password, user_role, "Admin");
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));

    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllUsers();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                user_id.add(cursor.getString(0));
                user_name.add(cursor.getString(1));
                user_username.add(cursor.getString(2));
                user_password.add(cursor.getString(3));
                user_role.add(cursor.getString(4));

            }
        }
    }
}
