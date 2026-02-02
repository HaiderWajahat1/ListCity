package com.example.listcity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    // Buttons for adding, deleting, and confirming city operations
    Button add, delete, confirm;
    EditText etCity;

    int index = -1;

    boolean addModeActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        add = findViewById(R.id.btn_add_city);
        delete = findViewById(R.id.btn_delete_city);
        confirm = findViewById(R.id.btn_confirm);
        etCity = findViewById(R.id.et_city);


        etCity.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            index = position;
            Toast.makeText(this,
                    "Selected: " + dataList.get(position),
                    Toast.LENGTH_SHORT).show();
        });

        add.setOnClickListener(v -> {
            addModeActive = true;
            etCity.setText("");
            etCity.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            etCity.requestFocus();
        });


        confirm.setOnClickListener(v -> {

            if (!addModeActive) return;

            String newCity = etCity.getText().toString().trim();

            if (newCity.isEmpty()) {
                Toast.makeText(this,
                        "Please enter a city name.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged(); // refresh ListView

            etCity.setText("");
            etCity.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            addModeActive = false;

            Toast.makeText(this,
                    "City added successfully.",
                    Toast.LENGTH_SHORT).show();
        });

        // ===== NEW INFO =====
        // DELETE button removes the selected city
        delete.setOnClickListener(v -> {

            if (index == -1) {
                Toast.makeText(this,
                        "Select a city first.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(index);
            cityAdapter.notifyDataSetChanged(); // refresh ListView
            index = -1;

            Toast.makeText(this,
                    "City deleted successfully.",
                    Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}