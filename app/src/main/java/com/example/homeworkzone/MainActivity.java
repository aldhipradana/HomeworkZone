package com.example.homeworkzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    protected Cursor cursor;

    String nama;
    String username;
    String password;
    String myName, myUsername, myPassword;
    TextInputEditText txtUsername, txtPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.btnLogin);
        ImageButton close = findViewById(R.id.closeBtn);

        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        session = sharedPreferences.edit();

        sesssionChecker();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginChecker()) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    Toast.makeText( getApplicationContext(),"Halo "+sharedPreferences.getString("usname", ""), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else {
                    Toast.makeText( getApplicationContext(), "Enter Correct Username and Password!", Toast.LENGTH_SHORT).show();
                    Snackbar failedLogin = Snackbar.make(findViewById(android.R.id.content), "Use Author's name for Username and Password!", Snackbar.LENGTH_LONG);
                    View view = failedLogin.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    failedLogin.show();
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeApplication(v);
            }
        });

    }

    public void closeApplication(View view) {
        finish();
        moveTaskToBack(true);
    }

    public void sesssionChecker(){

        boolean mysession = sharedPreferences.getBoolean("session", false);

        if (mysession){
            Toast.makeText( getApplicationContext(),"Halo "+sharedPreferences.getString("usname", ""), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText( getApplicationContext(),"Silahkan Login", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean loginChecker(){

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();

        Boolean login = dbHelper.loginsession(username, password);

        if (login == true){
            session.putString("usname", username);
            session.putBoolean("session", true);
            session.commit();
            return true;
        }else {
            return false;
        }

    }

}
