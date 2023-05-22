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
import android.widget.TextView;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {
    public static final String STUDENT_NAME = "studentname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        EditText nameStudent = (EditText) findViewById(R.id.editStudent);

        Intent intent = getIntent();
        String studentName = intent.getStringExtra(STUDENT_NAME);

        if (studentName != null) {
            nameStudent.setText(studentName);
        }
    }

    public void onStudentOkClick(View view){
        SQLiteOpenHelper sqliteHelper = new StudentsDatabaseHelper(this);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",
                ((TextView) findViewById(R.id.editStudent)).getText().toString());

        Intent intent = getIntent();
        String studentName = intent.getStringExtra(STUDENT_NAME);

        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            db.update("students",
                    contentValues,
                    "name=?",
                    new String[]{studentName});
            db.close();
            NavUtils.navigateUpFromSameTask(this);
            Toast toast = Toast.makeText(this,
                    "Зміни збережено",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        catch (SQLException e){
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onDeleteStudent(View view){
        SQLiteOpenHelper sqliteHelper = new StudentsDatabaseHelper(this);

        Intent intent = getIntent();
        String studentName = intent.getStringExtra(STUDENT_NAME);

        try {
            SQLiteDatabase db = sqliteHelper.getWritableDatabase();
            db.delete("students",
                    "name=?",
                    new String[]{studentName});
            db.close();
            NavUtils.navigateUpFromSameTask(this);
            Toast toast = Toast.makeText(this,
                    "Видалено",
                    Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}