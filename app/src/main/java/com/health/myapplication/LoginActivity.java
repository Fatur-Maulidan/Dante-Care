package com.health.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import CustomClass.CustomLayout;
import Database.DBHandler;

public class LoginActivity extends AppCompatActivity {
    TextView btnDaftar;
    EditText username,password;
    DBHandler db;
    DBHandler dbHandler;
    Button btnMasuk;
    CustomLayout customLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            customLayout = new CustomLayout();

            btnDaftar = findViewById(R.id.textViewDaftar);
            username = findViewById(R.id.editTextUsername);
            password = findViewById(R.id.editTextKataSandi);
            btnMasuk = findViewById(R.id.buttonMasuk);
            ImageView passwordIcon = findViewById(R.id.imageViewShowPassword);

            customLayout.passwordToggle(password,passwordIcon);

            db = new DBHandler(LoginActivity.this);
            dbHandler = new DBHandler(LoginActivity.this);

            btnMasuk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Isi username dengan password", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Boolean check = db.auth(username.getText().toString(),password.getText().toString());
                        if(check){
                            Toast.makeText(LoginActivity.this, "Berhasil Melakkukan Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("username", username.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Pastikan Username dan Password sesuai", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


            btnDaftar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                }
            });
    }
}