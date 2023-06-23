package com.health.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import CustomClass.CustomLayout;
import Database.DBHandler;

public class RegistrationActivity extends AppCompatActivity {
    EditText varUsername, varNama, varNomor, varPassword, varConfirmPassword;
    String username, nama, nomor, password, confirmPassword;
    CustomLayout customLayout;
    ImageView passwordIcon, confirmPasswordIcon;
    Button btnDaftar;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_activity);

        varUsername = findViewById(R.id.editTextUsername);
        varNama = findViewById(R.id.editTextNamaLengkap);
        varNomor = findViewById(R.id.editTextNoTelepon);
        varPassword = findViewById(R.id.editTextKataSandi);
        varConfirmPassword = findViewById(R.id.editTextKonfirmasiKataSandi);

        passwordIcon = findViewById(R.id.imageViewShowPassword1);
        confirmPasswordIcon = findViewById(R.id.imageViewShowPassword2);
        btnDaftar = findViewById(R.id.buttonDaftar);

        customLayout = new CustomLayout();

        customLayout.passwordToggle(varPassword,passwordIcon);
        customLayout.passwordToggle(varConfirmPassword,confirmPasswordIcon);

        db = new DBHandler(RegistrationActivity.this);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = varUsername.getText().toString();
                nama = varNama.getText().toString();
                nomor = varNomor.getText().toString();
                password = varPassword.getText().toString();
                confirmPassword = varConfirmPassword.getText().toString();

                if(username.isEmpty() || nama.isEmpty() || nomor.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Isi semua Form"+username+nama+nomor+password+confirmPassword, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(!password.equals(confirmPassword)){
                        Toast.makeText(RegistrationActivity.this, "Password dan Konfirmasi Password tidak sama", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        db.addUserData(username,nama,nomor, password);
                        Toast.makeText(RegistrationActivity.this, "Data Berhasil Dimasukkan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }
                }
            }
        });



    }
}