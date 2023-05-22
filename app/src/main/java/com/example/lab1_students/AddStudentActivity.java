package com.example.lab1_students;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddStudentActivity extends AppCompatActivity {

    private int id_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        Intent intent = getIntent();
        id_group = intent.getIntExtra("PARAMETER_KEY",0);
    }

    public void onStudentAddClick(View view){
        EditText student = (EditText) findViewById(R.id.addStudent);

        SQLiteOpenHelper sqliteHelper = new StudentsDatabaseHelper(this);
        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", student.getText().toString());
            contentValues.put("group_id", id_group);
            db.insert("students", null, contentValues);
            db.close();
            NavUtils.navigateUpFromSameTask(this);
            Toast toast = Toast.makeText(this,
                    "Створено",
                    Toast.LENGTH_SHORT);
        }
        catch (SQLException e){
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}