package com.example.venetatodorova.weatherreport;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import java.util.ArrayList;

public class MyFragment extends Fragment {

    ArrayList<String> cities;
    DownloadListener listener;
    public STATE state = STATE.NEUTRAL;
    DownloadReportTask downloadTask;
    ProgressDialog progressDialog;

    interface DownloadListener {
        void onDownloadFinished(String report);
    }

    public enum STATE{
        NEUTRAL,
        DOWNLOADING,
        DOWNLOADED
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
        state = STATE.DOWNLOADING;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Downloading weather report...");
        progressDialog.show();

        downloadTask = new DownloadReportTask();
        //downloadTask.setListener(listener);
        downloadTask.setListener(new DownloadListener() {
            @Override
            public void onDownloadFinished(String report) {
                state = STATE.DOWNLOADED;
                listener.onDownloadFinished(report);
            }
        });
        downloadTask.execute(cities.get(position));
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

}
