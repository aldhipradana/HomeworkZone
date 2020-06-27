package com.example.homeworkzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

public class DetailTugasActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    Button btnEdit;
    TextInputEditText namaMatkul, txtDesc, txtTanggal;
    String matkul, desc, tenggat;
    int idTugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);

        dbHelper = new DatabaseHelper(this);

        idTugas = getIntent().getIntExtra("idTugas", 0);
        matkul = getIntent().getStringExtra("matkul").toString();
        desc = getIntent().getStringExtra("deskripsi").toString();
        tenggat = getIntent().getStringExtra("tenggat").toString();

        namaMatkul = findViewById(R.id.txtMataKuliah);
        txtDesc = findViewById(R.id.txtDeskripsiTugas);
        txtTanggal = findViewById(R.id.txtTanggalTugas);
        btnEdit = findViewById(R.id.btnEditTugas);

        namaMatkul.setText(matkul);
        txtDesc.setText(desc);
        txtTanggal.setText(tenggat);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTugasActivity.class);
                intent.putExtra("matkul", matkul);
                intent.putExtra("deskripsi", desc);
                intent.putExtra("tenggat", tenggat);
                intent.putExtra("idTugas", idTugas);
                startActivity(intent);
                finish();
            }
        });


    }
}
