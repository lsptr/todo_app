package com.example.todoapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import models.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Находим кнопку регистрации по ID
        Button registerButton = findViewById(R.id.register_input_button);
        // Добавляем обработчик нажатия на кнопку регистрации
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });

        // Находим кнопку по ID
        Button loginButton = findViewById(R.id.login_button);
        // Добавляем обработчик нажатия на кнопку
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода к MainActivity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                // Запускаем MainActivity
                startActivity(intent);
            }
        });
    }
    public void register(View view) {
        EditText editTextName = findViewById(R.id.editTextName);
        String name = editTextName.getText().toString();
        EditText editTextUsername = findViewById(R.id.editTextLogin);
        String username = editTextUsername.getText().toString();
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String password = editTextPassword.getText().toString();

        // Используем конструктор, который принимает имя, логин и пароль
        User newUser = new User(name, username, password);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        long newRowId = dbHelper.addUser(this, newUser);

        User newUser2 = new User("1", "1", "1");
        long newRowId2 = dbHelper.addUser(this, newUser2);

        // Проверяем, не равен ли newRowId -1, чтобы убедиться, что вставка прошла успешно
        if (newRowId == -1) {
            // Вставка не удалась, выводим сообщение об ошибке
            Toast.makeText(this, "Ошибка при добавлении пользователя", Toast.LENGTH_SHORT).show();
        } else {
            // Вставка прошла успешно, можно выполнить дополнительные действия, например, обновить UI
            Toast.makeText(this, "Пользователь успешно добавлен", Toast.LENGTH_SHORT).show();
        }
    }
}

