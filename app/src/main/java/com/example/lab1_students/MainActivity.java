package com.example.lab1_students;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_group2);
    }

    public void onBtnShowGroupsClick(View view){
        Intent intent = new Intent(this, GroupsListActivity.class);
        startActivity(intent);
    }
}