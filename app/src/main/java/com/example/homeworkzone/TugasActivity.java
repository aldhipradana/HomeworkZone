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

public class TugasActivity extends AppCompatActivity {



    String[] listDataTugas, descTugas, tenggat;
    int[] listId;
    ListView listTugas;

    DatabaseHelper dbHelper;
    protected Cursor cursor;
    public static TugasActivity ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tugas);


        listTugas = findViewById(R.id.listTugas);

        ta = this;
        dbHelper = new DatabaseHelper(this);

        FloatingActionButton btnAdd = findViewById(R.id.addTugas);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddTugasActivity.class);
                startActivity(intent);

            }
        });

        RefreshList();

    }

    public void RefreshList() {


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("Select * FROM tugas ORDER BY nama_matakuliah ASC", null);
        listDataTugas = new String[cursor.getCount()];
        descTugas = new String[cursor.getCount()];
        tenggat = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int i = 0; i<cursor.getCount(); i++ ){
            cursor.moveToPosition(i);
            listId[i] = cursor.getInt(0);
            listDataTugas[i] = cursor.getString(1).toString();
            descTugas[i] = cursor.getString(2).toString();
            tenggat[i] = cursor.getString(3).toString();
        }

        listTugas = findViewById(R.id.listTugas);
        listTugas.setAdapter(new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, listDataTugas));

        listTugas.setSelected(true);


        listTugas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = listDataTugas[position];
                final String deskripsi = descTugas[position];
                final String batas = tenggat[position];
                final int idTugas = listId[position];
                final CharSequence[] dialogItem = {"Detail Tugas", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(TugasActivity.this);
                builder.setTitle("Options");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), DetailTugasActivity.class);
                                intent.putExtra("matkul", selection);
                                intent.putExtra("deskripsi", deskripsi);
                                intent.putExtra("tenggat", batas);
                                intent.putExtra("idTugas", idTugas);
                                startActivity(intent);
                                break;

                            case 1:
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                String sql = " DELETE FROM tugas WHERE id="+ idTugas +"; ";
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
