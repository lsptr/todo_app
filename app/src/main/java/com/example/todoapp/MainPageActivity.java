package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainPageActivity extends AppCompatActivity {
    private boolean isBackPressed = false;
    private MyDatabaseHelper dbHelper;

    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("id", -1);
        Log.d("DEBUG", "Received user ID: " + id);

        TextView userNameMain = findViewById(R.id.userNameMain);
        userNameMain.setText(name); // Устанавливаем имя пользователя в TextView

        // Инициализация dbHelper
        dbHelper = new MyDatabaseHelper(this);

        isBackPressed = false; // Инициализируем переменную

        Button usersButton = findViewById(R.id.users_button);
        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, UsersActivity.class);
                // Запускаем UsersActivity
                startActivity(intent);
            }
        });

        Button adminButton = findViewById(R.id.admin_button);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "Checking admin for user ID: " + id); // Логирование процесса проверки
                if (id!= -1) { // Проверяем, был ли ID пользователя передан
                    if (dbHelper.checkAdmin(id)) { // myDatabaseHelper - экземпляр вашего MyDatabaseHelper
                        Intent adminIntent = new Intent(MainPageActivity.this, AdminActivity.class);
                        startActivity(adminIntent);
                    } else {
                        Toast.makeText(MainPageActivity.this, "Страница доступна только администраторам", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainPageActivity.this, "Не удалось определить пользователя", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            finish(); // Завершаем активность
        } else {
            isBackPressed = true;
            handler.postDelayed(() -> {
                isBackPressed = false;
                showExitDialog(); // Показываем диалоговое окно
            }, 200); // Задержка
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выход из профиля");
        builder.setMessage("Вы уверены что хотите выйти из профиля?");
        builder.setPositiveButton("Да", (dialog, which) -> finish());
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}