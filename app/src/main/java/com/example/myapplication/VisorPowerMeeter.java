package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class VisorPowerMeeter extends AppCompatActivity implements View.OnClickListener{

    Context context = this;
    PowerMeeter Objeto = null;

    ProgressBar progressWatt, progressVolt, progressAmper;
    TextView Watt, Volt, Amper, caliWatt, caliVolt, caliAmper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_power_meeter);

        Bundle objetoEnviado = getIntent().getExtras();

        if(objetoEnviado != null){

            Objeto = (PowerMeeter) objetoEnviado.getSerializable("objeto");

        }

        progressWatt = (ProgressBar) findViewById(R.id.progressVerdeWatt);
        progressWatt.setProgress(5);

        progressVolt = (ProgressBar) findViewById(R.id.progressVerdeVolt);
        progressVolt.setProgress(5);

        progressAmper = (ProgressBar) findViewById(R.id.progressVerdeAmper);
        progressAmper.setProgress(5);

        Watt = (TextView) findViewById(R.id.textViewWatt);
        Volt = (TextView) findViewById(R.id.textViewVolt);
        Amper = (TextView) findViewById(R.id.textViewAmper);
        caliWatt = (TextView) findViewById(R.id.textViewCalWatt);
        caliVolt = (TextView) findViewById(R.id.textViewCalVolt);
        caliAmper = (TextView) findViewById(R.id.textViewCalAmper);

        findViewById(R.id.buttonPlusWatt).setOnClickListener(this);
        findViewById(R.id.buttonLessWatt).setOnClickListener(this);
        findViewById(R.id.buttonPlusVolt).setOnClickListener(this);
        findViewById(R.id.buttonLessVolt).setOnClickListener(this);
        findViewById(R.id.buttonPlusAmper).setOnClickListener(this);
        findViewById(R.id.buttonLessAmper).setOnClickListener(this);

        refresh();
        new Task1().execute("holaa");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPlusWatt:
                //Objeto.SetParameters(context, "{\"lessTemp\":2}");
                //Toast.makeText(context, "plus temp", Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonLessWatt:
                //Toast.makeText(context, "less temp", Toast.LENGTH_LONG).show();
                //Objeto.SetParameters(context, "{\"calTemp\":1}");
                break;
            case R.id.buttonPlusVolt:
                //Toast.makeText(context, "plus hum", Toast.LENGTH_LONG).show();
                //Objeto.SetParameters(context, "{\"calHum\":2}");
                break;
            case R.id.buttonLessVolt:
                //Toast.makeText(context, "less temp", Toast.LENGTH_LONG).show();
                //Objeto.SetParameters(context, "{\"calHum\":1}");
                break;
            case R.id.buttonPlusAmper:
                //Toast.makeText(context, "posteo", Toast.LENGTH_LONG).show();
                //Objeto.SetParameters(context, "{\"ssid\":\"WIFI lcdtm!\"}");
                break;
            case R.id.buttonLessAmper:
                //fragmento = new Fragment_TYH_info();
                //getSupportFragmentManager().beginTransaction().add(R.id.fragmentInfoTempYHum,fragmento).commit();
                break;
        }

    }

    public void setProgressW(double watt)
    {
        int progreso = (int) watt;

        int nProgreso = (progreso*70) / 6000;

        if(nProgreso >= 0 && nProgreso <= 70 ){
            progressWatt.setProgress(nProgreso);
        }
        if(nProgreso > 70)
        {
            progressWatt.setProgress(70);
        }
        if(nProgreso < 0) {progressWatt.setProgress(1);}
    }

    public void setProgressV(double volt)
    {
        int progreso = (int) volt;

        int nProgreso = (progreso*70) / 250;

        if(nProgreso >= 0 && nProgreso <= 70 ){
            progressVolt.setProgress(nProgreso);
        }
        if(nProgreso > 70)
        {
            progressVolt.setProgress(70);
        }
        if(nProgreso < 0) {progressVolt.setProgress(1);}
    }

    public void setProgressA(double amper)
    {
        int progreso = (int) amper;

        int nProgreso = (progreso*70) / 30;

        if(nProgreso >= 0 && nProgreso <= 70 ){
            progressAmper.setProgress(nProgreso);
        }
        if(nProgreso > 70)
        {
            progressAmper.setProgress(70);
        }
        if(nProgreso < 0) {progressAmper.setProgress(1);}
    }

    void refresh(){

        Objeto.Refresh(context);
        setProgressA(Objeto.getAmper());
        setProgressW(Objeto.getWatt());
        setProgressV(Objeto.getVolt());

        Watt.setText(Objeto.getStringWatt());
        Volt.setText(Objeto.getStringVolt());
        Amper.setText(Objeto.getStringAmper());
        caliWatt.setText(Objeto.getStringCalWatt());
        caliAmper.setText(Objeto.getStringCalAmper());
        caliVolt.setText(Objeto.getStringCalVolt());

    }

    class Task1 extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {

            try {
                refresh();
            } catch (Exception e) {
            }

            try {
                new Task1().execute("holaa");
            } catch (Exception e) {
            }

        }


    }


}
