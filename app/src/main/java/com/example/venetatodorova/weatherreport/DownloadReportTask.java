package com.example.venetatodorova.weatherreport;
import android.os.AsyncTask;
import android.app.Activity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.app.ProgressDialog;
import java.net.HttpURLConnection;
import java.net.URL;


class DownloadReportTask extends AsyncTask<String,String,Void> {

    private JSONObject data;
    private MyFragment.DownloadListener listener;
    private ProgressDialog progressDialog;
    private Activity activity;

    void setListener(MyFragment.DownloadListener listener) {
        this.listener = listener;
    }

    DownloadReportTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Downloading weather report...");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        progressDialog.setProgress(Integer.parseInt(values[0]));
    }

    @Override
    protected Void doInBackground(String... strings) {
        String city = strings[0];
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=e9a1658cdd2c1971f6ae6521334d4277");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder json = new StringBuilder(1024);
            String tmp;
            while((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();
            data = new JSONObject(json.toString());
        } catch (java.io.IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        StringBuilder report = new StringBuilder();
        try {
            report.append("Temperature: ").append(data.getJSONObject("main").get("temp").toString()).append("K");
            report.append("\nAtmospheric pressure: ").append(data.getJSONObject("main").get("pressure").toString()).append("hPa");
            report.append("\nHumidity: ").append(data.getJSONObject("main").get("humidity").toString()).append("%");
            report.append("\nWind's speed: ").append(data.getJSONObject("wind").get("speed").toString()).append("m/s");
            report.append("\nClouds: ").append(data.getJSONObject("clouds").get("all").toString()).append("%");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.onDownloadFinished(report.toString());

    }

}
