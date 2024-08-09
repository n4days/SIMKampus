package com.example.simkampus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DataMahasiswa extends AppCompatActivity {

    ActionBar actionBar;
    private ListView listView;
    private TextView tvListView;
    private FloatingActionButton fab;
    private EditText etSearch;
    private DatabaseHelper dbHelper;
    private List<InfoMahasiswa> dataList;
    private ArrayAdapter<String> adapter;
    private List<String> nimList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mahasiswa);

        fab = findViewById(R.id.fab);
        listView = findViewById(R.id.listView);
        etSearch = findViewById(R.id.etSearch);
        tvListView = findViewById(R.id.tvListView);
        dbHelper = new DatabaseHelper(this);
        dataList = new ArrayList<>();
        nimList = new ArrayList<>();

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataMahasiswa.this, Mahasiswa.class);
                startActivity(intent);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int
                    i1, int i2) {
                searchData(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        dataList = dbHelper.getAllData();
        nimList.clear();
        for (InfoMahasiswa infoMahasiswa : dataList) {
            nimList.add(infoMahasiswa.getNIM()+ "\n" + infoMahasiswa.getNAMA());
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, nimList);
        listView.setAdapter(adapter);
        if (adapter.getCount() > 0) {
            tvListView.setText("List Data Mahasiswa");
        } else {
            tvListView.setText("No Data Mahasiswa");
        }
    }

    private void searchData(String keyword) {
        dataList = dbHelper.searchData(keyword);
        nimList.clear();
        for (InfoMahasiswa infoMahasiswa : dataList) {
            nimList.add(infoMahasiswa.getNIM()+ "\n" + infoMahasiswa.getNAMA());
        }
        adapter.notifyDataSetChanged();
    }
}