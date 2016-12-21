package com.example.venetatodorova.weatherreport;

import android.support.v4.app.FragmentManager;
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
    ReportDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragment = (MyFragment) fragmentManager.findFragmentByTag("Fragment");

        if (fragment != null) {
            cities = fragment.getCities();
        } else {
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = MyFragment.newInstance();
            cities = new ArrayList<>();
            fragment.setCities(cities);
            fragmentTransaction.add(android.R.id.content,fragment,"Fragment");
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
        android.app.FragmentManager fm = getFragmentManager();
        dialogFragment = new ReportDialogFragment();
        dialogFragment = ReportDialogFragment.newInstance();
        dialogFragment.setData(report);
        dialogFragment.show(fm,"DialogFragment");
    }

    public void closeDialog() {
        dialogFragment.dismiss();
    }
}
