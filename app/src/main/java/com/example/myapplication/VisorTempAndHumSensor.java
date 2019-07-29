package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class VisorTempAndHumSensor extends AppCompatActivity implements View.OnClickListener, Fragment_TYH_info.OnFragmentInteractionListener{


    final Context context = this;
    TempAndHumSensor Objeto = null;

   ProgressBar progressTemp, progresHum;
    TextView Temperatura, Humedad, CaliTemp, CalibHum;

    Fragment_TYH_info fragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_temp_and_hum_sensor);

        progresHum = (ProgressBar) findViewById(R.id.progressVerdevsh);
        progresHum.setProgress(5);

        progressTemp = (ProgressBar) findViewById(R.id.progressVerdevsh2);
        progressTemp.setProgress(5);

        CaliTemp = (TextView) findViewById(R.id.textViewCalTemp);
        CalibHum = (TextView) findViewById(R.id.textViewCalHum);
        Temperatura = (TextView) findViewById(R.id.textViewTemp);
        Humedad = (TextView) findViewById(R.id.textViewHum);


        Bundle objetoEnviado = getIntent().getExtras();

        if(objetoEnviado != null){

            Objeto = (TempAndHumSensor) objetoEnviado.getSerializable("objeto");

        }


        findViewById(R.id.buttonPlusTemp).setOnClickListener(this);
        findViewById(R.id.buttonLessTemp).setOnClickListener(this);
        findViewById(R.id.buttonPlusHum).setOnClickListener(this);
        findViewById(R.id.buttonLessHum).setOnClickListener(this);
        findViewById(R.id.imageButtonInfo).setOnClickListener(this);
        findViewById(R.id.imageButtonConfig).setOnClickListener(this);

        refresh();
        new VisorTempAndHumSensor.Task1().execute("holaa");

    }


    public void setProgressT(double temperatura)
    {
        int progreso = (int) temperatura;

        int nProgreso = (progreso*70) / 60;

        if(nProgreso >= 0 && nProgreso <= 60 ){
            progressTemp.setProgress(nProgreso);
        }
        if(nProgreso > 70)
        {
            progressTemp.setProgress(70);
        }
        if(nProgreso < 0) {progressTemp.setProgress(1);}
    }

    public void setProgressH(double humedad)
    {
        int progreso = (int) humedad;

        int nProgreso = (progreso*70) / 100;

        if(nProgreso >= 0 && nProgreso <= 60 ){
            progresHum.setProgress(nProgreso);
        }
        if(nProgreso > 70)
        {
            progresHum.setProgress(70);
        }
        if(nProgreso < 0) {progresHum.setProgress(1);}
    }


    void refresh(){

        Objeto.Refresh(context);
        setProgressH(Objeto.getHumedad());
        setProgressT(Objeto.getTemperatura());

        Temperatura.setText(Objeto.getStringTem());
        Humedad.setText(Objeto.getStringHum());
        CaliTemp.setText(Objeto.getCalTempString());
        CalibHum.setText(Objeto.getCalHumString());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPlusTemp:
                Objeto.SetParameters(context, "['calTemp':2]");
                Toast.makeText(context, "plus temp", Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonLessTemp:
                Toast.makeText(context, "less temp", Toast.LENGTH_LONG).show();
                Objeto.SetParameters(context, "['calTemp':1]");
                break;
            case R.id.buttonPlusHum:
                Toast.makeText(context, "plus hum", Toast.LENGTH_LONG).show();
                Objeto.SetParameters(context, "['calHum':2]");
                break;
            case R.id.buttonLessHum:
                Toast.makeText(context, "less temp", Toast.LENGTH_LONG).show();
                Objeto.SetParameters(context, "['calHum':1]");
                break;
            case R.id.imageButtonConfig:
                Toast.makeText(context, "less temp", Toast.LENGTH_LONG).show();
                break;
            case R.id.imageButtonInfo:
                fragmento = new Fragment_TYH_info();
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentInfoTempYHum,fragmento).commit();
                break;
            }

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
                    new VisorTempAndHumSensor.Task1().execute("holaa");
                } catch (Exception e) {
                }

        }


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
