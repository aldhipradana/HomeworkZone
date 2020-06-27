package com.example.homeworkzone;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class EditMatakuliahActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper dbHelper;

    Button btnSumbit;
    TextInputEditText namaMatkul, namaDosen, jmlSks;
    String matkul, dosen, semester;
    Spinner spinner;
    int sks, idMatkul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_matakuliah);

        dbHelper = new DatabaseHelper(this);

        spinner = findViewById(R.id.spinSemester);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter );
        spinner.setOnItemSelectedListener(this);

        namaMatkul = findViewById(R.id.txtNamaMataKuliah);
        namaDosen = findViewById(R.id.txtNamaDosen);
        jmlSks = findViewById(R.id.txtSKS);
        btnSumbit = findViewById(R.id.btnSumbit);

        matkul = getIntent().getStringExtra("matkul").toString();
        dosen = getIntent().getStringExtra("dosen").toString();
        semester = getIntent().getStringExtra("semester").toString();
        sks = getIntent().getIntExtra("sks", 0);


        namaMatkul.setText(matkul);
        namaDosen.setText(dosen);
        int spinnerPosition = adapter.getPosition(semester);
        spinner.setSelection(spinnerPosition);
        jmlSks.setText(String.valueOf(sks));

        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

    }

    public void updateData( ){

        idMatkul = getIntent().getIntExtra("idMatkul", 0);
        matkul = namaMatkul.getText().toString();
        dosen = namaDosen.getText().toString();
        semester = spinner.getSelectedItem().toString();
        sks = Integer.parseInt(jmlSks.getText().toString());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insertSql = "UPDATE matakuliah " +
                "SET nama_matakuliah = '" + matkul + "', " +
                    " nama_dosen = '" + dosen+ "', " +
                    " semester = '" + semester + "'," +
                    " sks = "+ sks +" WHERE id = "+ idMatkul +" ;";

        Log.d("SQLRender", insertSql);
        db.execSQL(insertSql);

        Toast.makeText( getApplicationContext(), "Sukses Edit Data "+ matkul +"!", Toast.LENGTH_SHORT).show();
        MatakuliahActivity.ma.RefreshList();
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
