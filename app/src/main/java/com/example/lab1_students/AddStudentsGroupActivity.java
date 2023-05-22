package com.example.lab1_students;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudentsGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students_group);
    }

    public void onGrpAddClick(View view){
        EditText number = (EditText) findViewById(R.id.addGroupNumber);
        EditText faculty = (EditText) findViewById(R.id.addFaculty);

        SQLiteOpenHelper sqliteHelper = new StudentsDatabaseHelper(this);
        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("number", number.getText().toString());
            contentValues.put("facultyName", faculty.getText().toString());
            contentValues.put("educationLevel", 0);
            contentValues.put("contractExistsFlg", 0);
            contentValues.put("privilageExistsFlg", 0);
            db.insert("groups", null, contentValues);
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