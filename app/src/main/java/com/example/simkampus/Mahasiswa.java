package com.example.simkampus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Mahasiswa extends AppCompatActivity {

    ActionBar actionBar;

    private EditText etNomor, etName, etDate, etGender, etAddress;
    private Button btnSave;

    private DatabaseHelper dbHelper;
    private InfoMahasiswa infoMahasiswa;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        etNomor = findViewById(R.id.etNomor);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etGender = findViewById(R.id.etGender);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
//                    note.setTitle(title);
//                    note.setContent(content);
//                    note.setDate(date);
//                    dbHelper.updateNote(note);
                } else {
                    infoMahasiswa = new InfoMahasiswa();
                    infoMahasiswa.setNIM(nim);
                    infoMahasiswa.setNAMA(name);
                    infoMahasiswa.setDOB(date);
                    infoMahasiswa.setGENDER(gender);
                    infoMahasiswa.setADDRESS(address);
                    dbHelper.addData(infoMahasiswa);
                }
                finish();
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
}