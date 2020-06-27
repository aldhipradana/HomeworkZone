package com.example.homeworkzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        CardView cardMatkul = findViewById(R.id.cardMatkul);
        CardView cardTugas = findViewById(R.id.cardTugas);
        Button btnProfile = findViewById(R.id.btnProfile);
        Button btnExit = findViewById(R.id.btnExit);


        cardMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MatakuliahActivity.class);
                startActivity(intent);
            }
        });

        cardTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TugasActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeApplication(v);
            }
        });

    }

    public void closeApplication(View view) {
        editor.clear();
        editor.commit();
        finish();
        moveTaskToBack(true);
    }
}
