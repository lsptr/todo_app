package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import models.User;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ToDo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String USERS_TABLE_NAME = "users";
    private static final String TODO_LISTS_TABLE_NAME = "todo_lists";
    private static final String USERS_LISTS_TABLE_NAME = "users_lists";
    private static final String TODO_ITEMS_TABLE_NAME = "todo_items";
    private static final String LISTS_ITEMS_TABLE_NAME = "lists_items";
    private static final String ROLES_TABLE_NAME = "roles";
    private static final String USERS_ROLES_TABLE_NAME = "users_roles";
    private static final String STATUSES_TABLE_NAME = "statuses";
    private static final String USERS_STATUSES_TABLE_NAME = "users_statuses";
    private static final String COMMENTS_TABLE_NAME = "comments";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "_name";
    private static final String COLUMN_USERNAME = "_username";
    private static final String COLUMN_PASSWORD = "_password";
    private static final String COLUMN_TITLE = "_title";
    private static final String COLUMN_DESCRIPTION = "_description";
    private static final String COLUMN_DONE = "_done";
    private static final String COLUMN_ROLE_TITLE = "_role_title";
    private static final String COLUMN_ROLE_DESCRIPTION = "_role_description";
    private static final String COLUMN_STATUS_DESCRIPTION = "_status_description";
    private static final String COLUMN_AUTHOR_ID = "_author_id";
    private static final String COLUMN_LIST_ID = "_list_id";
    private static final String COLUMN_ITEM_ID = "_item_id";
    private static final String COLUMN_USER_ID = "_user_id";
    private static final String COLUMN_ROLE_ID = "_role_id";
    private static final String COLUMN_STATUS_ID = "_status_id";
    private static final String COLUMN_RECEIVER_ID = "_receiver_id";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
                // Создание таблицы users
        String usersQuery = "CREATE TABLE " + USERS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " + COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " + COLUMN_PASSWORD + " TEXT NOT NULL);";
        db.execSQL(usersQuery);

        // Создание таблицы todo_lists
        String todoListsQuery = "CREATE TABLE " + TODO_LISTS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " + COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(todoListsQuery);

        // Создание таблицы users_lists
        String usersListsQuery = "CREATE TABLE " + USERS_LISTS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER NOT NULL REFERENCES " + USERS_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE, " +
                COLUMN_LIST_ID + " INTEGER NOT NULL REFERENCES " + TODO_LISTS_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE);";
        db.execSQL(usersListsQuery);

        // Создание таблицы todo_items
        String todoItemsQuery = "CREATE TABLE " + TODO_ITEMS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_DONE + " BOOLEAN NOT NULL DEFAULT FALSE);";
        db.execSQL(todoItemsQuery);

        // Создание таблицы lists_items
        String listsItemsQuery = "CREATE TABLE " + LISTS_ITEMS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_ID + " INTEGER NOT NULL REFERENCES " + TODO_ITEMS_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE, " +
                COLUMN_LIST_ID + " INTEGER NOT NULL REFERENCES " + TODO_LISTS_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE);";
        db.execSQL(listsItemsQuery);

        // Создание таблицы roles
        String rolesQuery = "CREATE TABLE " + ROLES_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROLE_TITLE + " TEXT NOT NULL, " + COLUMN_ROLE_DESCRIPTION + " TEXT);";
        db.execSQL(rolesQuery);

        // Создание таблицы users_roles
        String usersRolesQuery = "CREATE TABLE " + USERS_ROLES_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER NOT NULL REFERENCES " + USERS_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE, " +
                COLUMN_ROLE_ID + " INTEGER NOT NULL REFERENCES " + ROLES_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE);";
        db.execSQL(usersRolesQuery);

        // Создание таблицы statuses
        String statusesQuery = "CREATE TABLE " + STATUSES_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STATUS_DESCRIPTION + " TEXT);";
        db.execSQL(statusesQuery);

        // Создание таблицы users_statuses
        String usersStatusesQuery = "CREATE TABLE " + USERS_STATUSES_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER NOT NULL REFERENCES " + USERS_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE, " +
                COLUMN_STATUS_ID + " INTEGER NOT NULL REFERENCES " + STATUSES_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE);";
        db.execSQL(usersStatusesQuery);

        // Создание таблицы comments
        String commentsQuery = "CREATE TABLE " + COMMENTS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DESCRIPTION + " TEXT, " + COLUMN_AUTHOR_ID + " INTEGER NOT NULL REFERENCES " + USERS_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE, " +
                COLUMN_RECEIVER_ID + " INTEGER NOT NULL REFERENCES " + USERS_TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE);";
        db.execSQL(commentsQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление существующих таблиц
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TODO_LISTS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + USERS_LISTS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TODO_ITEMS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + LISTS_ITEMS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ROLES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + USERS_ROLES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + STATUSES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + USERS_STATUSES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + COMMENTS_TABLE_NAME + ";");

        // Повторное создание таблиц
        onCreate(db);
    }

    public long addUser(Context context, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());

        long newRowId = db.insert(USERS_TABLE_NAME, null, values);

        return newRowId;
    }

    public User checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + COLUMN_USERNAME + " =? AND " + COLUMN_PASSWORD + " =?", new String[]{username, password});

        if (cursor.moveToFirst()) {
            int columnIndexId = cursor.getColumnIndex(COLUMN_ID);
            int columnIndexName = cursor.getColumnIndex(COLUMN_NAME);

            if (columnIndexId >= 0 && columnIndexName >= 0) {
                int id = cursor.getInt(columnIndexId);
                String name = cursor.getString(columnIndexName);
                cursor.close();
                db.close();
                return new User(id, name, username, password); // Возвращаем объект User
            } else {
                cursor.close();
                db.close();
                return null; // Возвращаем null, если пользователь не найден
            }
        } else {
            cursor.close();
            db.close();
            return null; // Возвращаем null, если пользователь не найден
        }
    }

    Cursor readAllUsers() {
        //"SELECT u.id, u.name, COALESCE(s.description, '') AS status, COALESCE(r.title, '') AS role
        // FROM %s u LEFT JOIN %s us ON u.id = us.user_id LEFT JOIN %s s ON us.status_id = s.id LEFT
        // JOIN %s ur ON u.id = ur.user_id LEFT JOIN %s r ON ur.role_id = r.id", usersTable, usersStatusesTable, statusesTable, usersRolesTable, rolesTable)
//        String query = "SELECT * FROM " + USERS_TABLE_NAME;
        String query = "SELECT u.*, IFNULL(r._role_title, '') AS role_title FROM " + USERS_TABLE_NAME + " u LEFT JOIN " + USERS_ROLES_TABLE_NAME + " ur ON u." + COLUMN_ID + " = ur." + COLUMN_USER_ID + " LEFT JOIN " + ROLES_TABLE_NAME + " r ON ur." + COLUMN_ROLE_ID + " = r." + COLUMN_ID + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean checkAdmin(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + USERS_TABLE_NAME + " u INNER JOIN " + USERS_ROLES_TABLE_NAME + " ur ON u." + COLUMN_ID + " = ur." + COLUMN_USER_ID + " INNER JOIN " + ROLES_TABLE_NAME + " r ON ur." + COLUMN_ROLE_ID + " = r." + COLUMN_ID + " WHERE u._id =?", new String[]{Integer.toString(userId)});

        Log.d("DEBUG", "UserId: " + userId); // Логирование ID пользователя
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            db.close();
            Log.d("DEBUG", "Count: " + count); // Логирование количества записей
            return count > 0; // Возвращает true, если пользователь является администратором
        } else {
            cursor.close();
            db.close();
            Log.d("DEBUG", "No records found"); // Логирование отсутствия записей
            return false; // Возвращает false, если пользователь не является администратором
        }
    }

}
