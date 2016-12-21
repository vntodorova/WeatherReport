package com.example.venetatodorova.weatherreport;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.Button;

public class ReportDialogFragment extends DialogFragment{

    TextView textView;
    static String data;
    Button button;

    public static ReportDialogFragment newInstance(String report) {
        data = report;
        return new ReportDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog,container,false);
        textView = (TextView) v.findViewById(R.id.textView);
        textView.setText(data);
        button = (Button)v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }
}
