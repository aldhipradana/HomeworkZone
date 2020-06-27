package com.example.homeworkzone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class EditTugasActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    DatabaseHelper dbHelper;
    Cursor cursor;
    Button btnSumbit;
    String matkul, desc, tenggat;
    String[] listData;
    Spinner spinner;
    TextInputEditText dateText,txtDesc;
    int idTugas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tugas);


        dbHelper = new DatabaseHelper(this);

        Button btnDate = findViewById(R.id.btnDate);

        spinner =  findViewById(R.id.spinner);
        txtDesc = findViewById(R.id.txtDeskripsiTugas);

        btnSumbit = findViewById(R.id.btnSumbit);
        dateText = findViewById(R.id.txtTanggalTugas);

        idTugas = getIntent().getIntExtra("idTugas", 0);
        desc = getIntent().getStringExtra("deskripsi").toString();
        tenggat = getIntent().getStringExtra("tenggat").toString();

        txtDesc.setText(desc);
        dateText.setText(tenggat);

        matkulSpinner();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });


    }


    public void matkulSpinner(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("Select * FROM matakuliah ORDER BY nama_matakuliah ASC", null);
        listData = new String[cursor.getCount()];

        cursor.moveToFirst();

        for (int i = 0; i<cursor.getCount(); i++ ){
            cursor.moveToPosition(i);
            listData[i] = cursor.getString(1).toString();
        }


        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_spinner_item, listData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter );

        matkul = getIntent().getStringExtra("matkul").toString();
        int spinnerPosition = adapter.getPosition(matkul);
        spinner.setSelection(spinnerPosition);

    }


    public void updateData( ){

        idTugas = getIntent().getIntExtra("idTugas", 0);
        matkul = spinner.getSelectedItem().toString();
        desc = txtDesc.getText().toString();
        tenggat = dateText.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insertSql = "UPDATE tugas " +
                "SET nama_matakuliah = '" + matkul + "', " +
                " deskripsi = '" + desc + "', " +
                " tenggat = '" + tenggat + "'" +
                " WHERE id = "+ idTugas +" ;";
        db.execSQL(insertSql);

        Toast.makeText( getApplicationContext(), "Sukses Edit Data Tugas!", Toast.LENGTH_SHORT).show();
        TugasActivity.ta.RefreshList();
        finish();

    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        dateText.findViewById(R.id.txtTanggalTugas);
        dateText.setText(date);
    }

}
