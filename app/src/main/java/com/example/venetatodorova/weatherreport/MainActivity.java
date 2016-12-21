package com.example.venetatodorova.weatherreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Button;
import android.app.Dialog;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyFragment.DownloadListener {

    ArrayAdapter<String> adapter;
    ArrayList<String> cities;
    MyFragment fragment;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragment = (MyFragment) fragmentManager.findFragmentByTag("Tag");

        if (fragment != null) {
            cities = fragment.getCities();
        } else {
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = MyFragment.newInstance();
            cities = new ArrayList<>();
            fragment.setCities(cities);
            fragmentTransaction.add(android.R.id.content,fragment,"Tag");
            fragmentTransaction.commit();
        }
        fragment.setListener(this);

        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,cities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                fragment.getReport(position);
            }
        });
    }

    public void addCity(View view) {
        EditText editText = (EditText) findViewById(R.id.addCity_editText);
        String city = editText.getText().toString();
        cities.add(city);
        editText.setText("");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDownloadFinished(String report) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Weather report..");

        TextView text = (TextView) dialog.findViewById(R.id.textView);
        text.setText(report);
        Button button = (Button) dialog.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
