package com.example.venetatodorova.weatherreport;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.Button;

public class ReportDialogFragment extends DialogFragment{

    static String data;

    public static ReportDialogFragment newInstance(String report) {
        data = report;
        return new ReportDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog,container,false);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        textView.setText(data);
        Button button = (Button)v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }
}
