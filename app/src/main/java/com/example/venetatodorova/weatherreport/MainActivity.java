package com.example.venetatodorova.weatherreport;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
            if(fragment.state != MyFragment.STATE.DOWNLOADED) fragment.progressDialog.show();
        } else {
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = MyFragment.newInstance();
            cities = new ArrayList<>();
            cities.add("London");
            fragment.setCities(cities);
            fragmentTransaction.add(android.R.id.content, fragment, "Fragment");
            fragmentTransaction.commit();
        }

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

    @Override
    protected void onPause() {
        fragment.setListener(null);
        if(fragment.progressDialog != null) fragment.progressDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onResume() {
        fragment.setListener(this);
        super.onResume();
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
        fragment.progressDialog.dismiss();
        dialogFragment = new ReportDialogFragment();
        dialogFragment = ReportDialogFragment.newInstance(report);
        dialogFragment.show(getFragmentManager(),"DialogFragment");
    }
}
