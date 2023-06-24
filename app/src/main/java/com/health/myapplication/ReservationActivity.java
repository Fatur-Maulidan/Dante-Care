package com.health.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Database.DBHandler;

public class ReservationActivity extends AppCompatActivity {

    private Spinner dokterSpinner;
    private String[] lokasiKlinik = {"Sumedang", "Bandung", "Cimahi"};
    private Map<String, List<String>> dokterPerLokasi;
    private String selectedValue, selectedDokter;
    private String varDenteNama, varDenteLokasi, varDenteNomor, varDenteTanggal, varDenteDokter ,varDenteKeluhan;
    private EditText varNama, varNomor, varKeluhan;
    private DatePicker varTanggal;
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        Spinner spinnerLokasi = findViewById(R.id.lokasiSpinner);
        dokterSpinner = findViewById(R.id.dokterSpinner);
        Button varBtnReservasi = findViewById(R.id.btnReservasi);

        varNama = findViewById(R.id.etNama);
        varNomor = findViewById(R.id.etNomor);
        varTanggal = findViewById(R.id.dpTanggal);
        varKeluhan = findViewById(R.id.etKeluhan);

        dbHandler = new DBHandler(ReservationActivity.this);

        // Ambil daftar lokasi dari Array lokasiKlinik
        List<String> lokasiList = Arrays.asList(lokasiKlinik);

// Buat adapter untuk Spinner dengan menggunakan daftar lokasi
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lokasiList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Set adapter ke Spinner
        spinnerLokasi.setAdapter(adapter);

        spinnerLokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedValue = spinnerLokasi.getSelectedItem().toString();

                // Ambil daftar dokter untuk lokasi yang dipilih dari HashMap
                List<String> dokterList = dokterPerLokasi.get(selectedValue);

                // Update Spinner dokter dengan daftar dokter yang sesuai
                updateDokterSpinner(dokterList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Metode ini dipanggil saat tidak ada yang dipilih
            }
        });

//      Ketika Dropdown Dokter dipilih
        dokterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDokter = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Kosongkan implementasi
            }
        });


        // Ketika Button Reservasi sudah di klik
        varBtnReservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varDenteNama = varNama.getText().toString();
                varDenteLokasi = selectedValue;
                varDenteNomor = varNomor.getText().toString();
                varDenteDokter = selectedDokter;

                // Mendapatkan tanggal yang dipilih dari varTanggal
                int year = varTanggal.getYear();
                int month = varTanggal.getMonth();
                int dayOfMonth = varTanggal.getDayOfMonth();

                // Membuat objek Calendar
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Format tanggal menjadi string
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                varDenteTanggal = dateFormat.format(calendar.getTime());

                varDenteKeluhan = varKeluhan.getText().toString();


                if(varDenteNama.isEmpty() || varDenteNomor.isEmpty() || varDenteKeluhan.isEmpty()){
                    Toast.makeText(ReservationActivity.this, "Tolong isi semua data", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    dbHandler.addNewDataTblData(varDenteNama,varDenteLokasi,varDenteNomor, varDenteTanggal, varDenteDokter, varDenteKeluhan, String.valueOf(dbHandler.countDataLokasiDokter(varDenteLokasi, varDenteDokter, varDenteTanggal) + 1));

                    Toast.makeText(ReservationActivity.this, "Nomor Antrian anda " + dbHandler.countDataLokasiDokter(varDenteLokasi, varDenteDokter, varDenteTanggal), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReservationActivity.this,MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        });
    }

    public ReservationActivity() {
        dokterPerLokasi = new HashMap<>();
        dokterPerLokasi.put("Sumedang", createDokterSumedang());
        dokterPerLokasi.put("Bandung", createDokterBandung());
        dokterPerLokasi.put("Cimahi", createDokterCimahi());
    }

    private List<String> createDokterSumedang() {
        List<String> dokterSumedang = new ArrayList<>();
        dokterSumedang.add("Dokter Arief");
        dokterSumedang.add("Dokter Rahman");
        dokterSumedang.add("Dokter Aldrin");
        return dokterSumedang;
    }

    private List<String> createDokterBandung() {
        List<String> dokterBandung = new ArrayList<>();
        dokterBandung.add("Dokter Rayhan");
        dokterBandung.add("Dokter Putra");
        dokterBandung.add("Dokter Vin");
        return dokterBandung;
    }

    private List<String> createDokterCimahi() {
        List<String> dokterCimahi = new ArrayList<>();
        dokterCimahi.add("Dokter Fatur");
        dokterCimahi.add("Dokter Maul");
        dokterCimahi.add("Dokter Vina");
        return dokterCimahi;
    }

    private void updateDokterSpinner(List<String> dokterList) {
        ArrayAdapter<String> dokterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dokterList);
        dokterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dokterSpinner.setAdapter(dokterAdapter);
    }
}