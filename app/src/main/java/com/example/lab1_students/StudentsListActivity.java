package com.example.lab1_students;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class StudentsListActivity extends AppCompatActivity {
    public static final String GROUP_NUMBER="groupnumber";
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(GROUP_NUMBER, 0);

        ListView listView = (ListView) findViewById(R.id.studentsList);
        SimpleCursorAdapter adapter = getDataFromDB(grpNumber);
        if (adapter != null){
            listView.setAdapter(adapter);
        }

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                if (cursor != null && cursor.moveToPosition(i)) {
                    int columnIndex = cursor.getColumnIndex("name");
                    if (columnIndex >= 0) {
                        String name = cursor.getString(columnIndex);
                        Intent intent = new Intent(StudentsListActivity.this, StudentActivity.class);
                        intent.putExtra(StudentActivity.STUDENT_NAME, name);
                        startActivity(intent);
                    }
                }
            }
        };

        listView.setOnItemClickListener(listener);
    }

    private SimpleCursorAdapter getDataFromDB(int groupId){
        SimpleCursorAdapter listAdapter = null;

        SQLiteOpenHelper sqliteHelper = new StudentsDatabaseHelper(this);
        try{
            db = sqliteHelper.getReadableDatabase();
            cursor = db.rawQuery("select s.id _id, name, number\n" +
                    "from students s inner join groups g on s.group_id = g.id\n" +
                    "where g.id = ?", new String[] {Integer.toString(groupId)});
            listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[] {"name"},
                    new int[] {android.R.id.text1},
                    0);
        }
        catch (SQLException e){
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        return listAdapter;
    }

    protected void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    public void onSendBtnClick(View view){
        TextView textView = (TextView) findViewById(R.id.text);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, "Список студентів");
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.students_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_student:{
                Intent intentgrp = getIntent();
                int grpNumber = intentgrp.getIntExtra(GROUP_NUMBER, 0);
                Intent intent = new Intent(this, AddStudentActivity.class);
                intent.putExtra("PARAMETER_KEY", grpNumber);
                startActivity(intent);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}