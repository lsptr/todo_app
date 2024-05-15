package com.example.todoapp;

import android.database.Cursor;
import models.User;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button; // Импорт класса Button
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Создаем экземпляр MyDatabaseHelper
        dbHelper = new MyDatabaseHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Находим кнопку "Вход" по ID
        Button loginButton = findViewById(R.id.loin_input_button);

// Добавляем обработчик нажатия на кнопку "Вход"
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextLogin = findViewById(R.id.editTextLogin);
                String username = editTextLogin.getText().toString();
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String password = editTextPassword.getText().toString();

                User user = dbHelper.checkUserCredentials(username, password); // Используем новую версию функции

                if (user!= null) { // Проверяем, что пользователь найден
                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                    intent.putExtra("name", user.getName()); // Добавляем имя пользователя в Intent
                    Log.d("DEBUG", "Sending user ID: " + user.getId());
                    intent.putExtra("id", user.getId()); // Добавляем ID пользователя в Intent
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Находим кнопку по ID
        Button registerButton = findViewById(R.id.register_button);

        // Добавляем обработчик нажатия на кнопку
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к RegisterActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                // Запускаем RegisterActivity
                startActivity(intent);
            }
        });


    }


}