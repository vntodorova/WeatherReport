package com.example.venetatodorova.weatherreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> cities;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cities = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,cities);
        listView.setAdapter(adapter);
    }

    public void addCity(View view) {
        editText = (EditText) findViewById(R.id.addCity_editText);
        String city = editText.getText().toString();
        cities.add(city);
        editText.setText("");
        adapter.notifyDataSetChanged();
    }
}
