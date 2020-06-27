package com.example.homeworkzone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MatakuliahActivity extends AppCompatActivity {


    String[] listData, listDosen, listSemester;
    int[] listId, listSks;
    ListView listView;

    DatabaseHelper dbHelper;
    protected Cursor cursor;
    public static MatakuliahActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matakuliah);


        listView = findViewById(R.id.listMatkul);

        ma = this;
        dbHelper = new DatabaseHelper(this);

        FloatingActionButton btnAdd = findViewById(R.id.addMatkul);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddMatakuliahActivity.class);
                startActivity(intent);

            }
        });


        RefreshList();


    }

    public void RefreshList(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("Select * FROM matakuliah ORDER BY nama_matakuliah ASC", null);
        listData = new String[cursor.getCount()];
        listDosen = new String[cursor.getCount()];
        listSemester = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        listSks = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int i = 0; i<cursor.getCount(); i++ ){
            cursor.moveToPosition(i);
            listId[i] = cursor.getInt(0);
            listData[i] = cursor.getString(1).toString();
            listDosen[i] = cursor.getString(2).toString();
            listSemester[i] = cursor.getString(3).toString();
            listSks[i] = cursor.getInt(4);
        }

        listView = findViewById(R.id.listMatkul);
        listView.setAdapter(new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, listData));

        listView.setSelected(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = listData[position];
                final String dosen = listDosen[position];
                final String smt = listSemester[position];
                final int idMatkul = listId[position];
                final int sks = listSks[position];
                final CharSequence[] dialogItem = {"Edit", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MatakuliahActivity.this);
                builder.setTitle("Options");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), EditMatakuliahActivity.class);
                                intent.putExtra("matkul", selection);
                                intent.putExtra("dosen", dosen);
                                intent.putExtra("semester", smt);
                                intent.putExtra("idMatkul", idMatkul);
                                intent.putExtra("sks", sks);
                                startActivity(intent);
                                break;

                            case 1:
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                String sql = " DELETE FROM matakuliah WHERE id="+ idMatkul +"; ";
                                db.execSQL(sql);
                                RefreshList();
                                break;

                            default:
                                break;
                        }

                    }
                });
                builder.create().show();

            }
        });


    }
}
