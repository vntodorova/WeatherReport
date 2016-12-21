package com.example.venetatodorova.weatherreport;

import android.app.Fragment;
import android.os.Bundle;
import java.util.ArrayList;

public class MyFragment extends Fragment {

    ArrayList<String> cities;
    DownloadListener listener;

    interface DownloadListener {
        void onDownloadFinished(String report);
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void getReport(int position){
        DownloadReportTask downloadTask = new DownloadReportTask(getActivity());
        downloadTask.setListener(listener);
        downloadTask.execute(cities.get(position));
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

}
