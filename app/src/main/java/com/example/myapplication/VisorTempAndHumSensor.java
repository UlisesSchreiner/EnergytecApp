package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class VisorTempAndHumSensor extends AppCompatActivity {

    TempAndHumSensor Objeto = null;

   ProgressBar progressTemp, progresHum;
    TextView Temperatura, Humedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_temp_and_hum_sensor);

        progresHum = (ProgressBar) findViewById(R.id.progressVerdevsh);
        progresHum.setProgress(5);

        progressTemp = (ProgressBar) findViewById(R.id.progressVerdevsh2);
        progressTemp.setProgress(5);


        Temperatura = (TextView) findViewById(R.id.textViewTemp);
        Humedad = (TextView) findViewById(R.id.textViewHum);

        Bundle objetoEnviado = getIntent().getExtras();

        if(objetoEnviado != null){

            Objeto = (TempAndHumSensor) objetoEnviado.getSerializable("objeto");

        }

setProgressH(Objeto.getHumedad());
        setProgressT(Objeto.getTemperatura());

        Temperatura.setText(Objeto.getStringTem());
        Humedad.setText(Objeto.getStringHum());

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


}
