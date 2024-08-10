package com.example.simkampus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Mahasiswa extends AppCompatActivity {

    ActionBar actionBar;

    private EditText etNomor, etName, etDate, etGender, etAddress;
    private Button btnSave;

    private TextView tvTitleScreen;

    private DatabaseHelper dbHelper;
    private InfoMahasiswa infoMahasiswa;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        tvTitleScreen = findViewById(R.id.tvTitleScreen);
        etNomor = findViewById(R.id.etNomor);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etGender = findViewById(R.id.etGender);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        etDate.setOnClickListener(v -> showDatePickerDialog());

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("view_data_id")) {
            tvTitleScreen.setText("Lihat Data");
            btnSave.setVisibility(View.GONE);
            int id = getIntent().getIntExtra("view_data_id", -1);
            infoMahasiswa = dbHelper.getData(id);
            if (infoMahasiswa != null) {
                etNomor.setText(infoMahasiswa.getNIM());
                etName.setText(infoMahasiswa.getNAMA());
                etDate.setText(infoMahasiswa.getDOB());
                etGender.setText(infoMahasiswa.getGENDER());
                etAddress.setText(infoMahasiswa.getADDRESS());

                etNomor.setEnabled(false);
                etName.setEnabled(false);
                etDate.setEnabled(false);
                etGender.setEnabled(false);
                etAddress.setEnabled(false);
            }
        }

        if (getIntent().hasExtra("data_id")) {
            tvTitleScreen.setText("Edit Data");
            btnSave.setText("Edit");
            int id = getIntent().getIntExtra("data_id", -1);
            infoMahasiswa = dbHelper.getData(id);
            if (infoMahasiswa != null) {
                etNomor.setText(infoMahasiswa.getNIM());
                etName.setText(infoMahasiswa.getNAMA());
                etDate.setText(infoMahasiswa.getDOB());
                etGender.setText(infoMahasiswa.getGENDER());
                etAddress.setText(infoMahasiswa.getADDRESS());
                isEdit = true;
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nim = etNomor.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String gender = etGender.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                if (nim.isEmpty() || nim.isEmpty()) {
                    Toast.makeText(Mahasiswa.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEdit) {
                    infoMahasiswa.setNIM(nim);
                    infoMahasiswa.setNAMA(name);
                    infoMahasiswa.setDOB(date);
                    infoMahasiswa.setGENDER(gender);
                    infoMahasiswa.setADDRESS(address);
                    dbHelper.updateNote(infoMahasiswa);
                } else {
                    infoMahasiswa = new InfoMahasiswa();
                    infoMahasiswa.setNIM(nim);
                    infoMahasiswa.setNAMA(name);
                    infoMahasiswa.setDOB(date);
                    infoMahasiswa.setGENDER(gender);
                    infoMahasiswa.setADDRESS(address);
                    dbHelper.addData(infoMahasiswa);
                }
                Intent intent = new Intent(Mahasiswa.this, DataMahasiswa.class);
                startActivity(intent);
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Mahasiswa.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date and set it to EditText
                    String selectedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear);
                    etDate.setText(selectedDate);
                },
                day, month, year
        );

        datePickerDialog.show();
    }
}