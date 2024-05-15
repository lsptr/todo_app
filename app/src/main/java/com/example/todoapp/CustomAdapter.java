package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList user_id, user_name, user_username, user_password, user_role;
    private String activityType;
    CustomAdapter(Context context, ArrayList user_id, ArrayList user_name, ArrayList user_username, ArrayList user_password, ArrayList user_role, String activityType){
        this.context = context;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_role = user_role;
        this.activityType = activityType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if ("Admin".equals(activityType)) { // Проверяем тип активности
            View view = inflater.inflate(R.layout.admin_delete_recycle, parent, false); // Загружаем макет для AdminActivity
            return new MyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.view_recycle, parent, false); // Загружаем макет для UsersActivity
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user_name_txt.setText(String.valueOf(user_name.get(position)));
        holder.user_role_txt.setText(String.valueOf(user_role.get(position)));
    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView user_name_txt;
        TextView user_role_txt;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            user_name_txt = itemView.findViewById(R.id.user_name_txt);
            user_role_txt = itemView.findViewById(R.id.user_role_txt);
        }

    }
}
