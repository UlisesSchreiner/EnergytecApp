package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class VisorTermotanque extends AppCompatActivity implements View.OnClickListener {
    final Context context = this;

    TermotanqueSolar Objeto = null;

    private TextView temperatura;
    private EditText tempObj, HoraMinimaUmbralUno, HoraMaximaimaUmbralUno, TempObjUmbralUno, HoraMinimaUmbralDos, HoraMaximaUmbralDos, TempObjUmbralDos;
    private Switch SwitchEstadoManual, SwitchSuperUmbrales, SwitchEstadoUmbralUno, SwitchEstadoUmbralDos;
    // private ImageButton botonManual, botonUmbralUno, botonUmbralDos;
    private ProgressBar progreVerde;
    private byte contadorRefrescar = 2;
    private boolean estadoActividad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_termotanque);

        temperatura = (TextView) findViewById(R.id.Temperatura);
        tempObj = (EditText) findViewById(R.id.tempObj);
        HoraMinimaUmbralUno = (EditText) findViewById(R.id.horaMinimaUbralUno);
        HoraMaximaimaUmbralUno = (EditText) findViewById(R.id.horaMaximaUmbralUno);
        HoraMinimaUmbralDos = (EditText) findViewById(R.id.horaMinimaUmbralDos);
        HoraMaximaUmbralDos = (EditText) findViewById(R.id.horaMaximaUmbralDos);
        TempObjUmbralUno = (EditText) findViewById(R.id.tempObjUmbralUno);
        TempObjUmbralDos = (EditText) findViewById(R.id.tempObjDos);
        SwitchEstadoManual = (Switch) findViewById(R.id.switchEstSistManual);
        SwitchSuperUmbrales = (Switch) findViewById(R.id.switchEstSupUmbrales);
        SwitchEstadoUmbralUno = (Switch) findViewById(R.id.switchEstUmbralUno);
        SwitchEstadoUmbralDos = (Switch) findViewById(R.id.switchEstUmbralDos);


        progreVerde = (ProgressBar) findViewById(R.id.progressVerde);
        progreVerde.setProgress(5);

        findViewById(R.id.manual).setOnClickListener(this);
        findViewById(R.id.umbralUno).setOnClickListener(this);
        findViewById(R.id.umbralDos).setOnClickListener(this);

        Bundle objetoEnviado = getIntent().getExtras();

        if(objetoEnviado != null){

            Objeto = (TermotanqueSolar)objetoEnviado.getSerializable("objeto");

        }



        refresh();
        SwitchEstadoManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 contadorRefrescar = 0;
                if (isChecked)
                {
                    Objeto.SetParameters(context, "['estadoSist':1]");
                } else {
                    Objeto.SetParameters(context, "['estadoSist':2]");
                }
            }
        });

        SwitchSuperUmbrales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Objeto.SetParameters(context, "['estadoSupUmbrales':1]");
                } else {
                    Objeto.SetParameters(context, "['estadoSupUmbrales':2]");
                }
            }
        });

        SwitchEstadoUmbralUno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Objeto.SetParameters(context, "['estadoUmbral1':1]");
                } else {
                    Objeto.SetParameters(context, "['estadoUmbral1':2]");
                }
            }
        });

        SwitchEstadoUmbralDos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Objeto.SetParameters(context, "['estadoUmbral2':1]");
                } else {
                    Objeto.SetParameters(context, "['estadoUmbral2':2]");
                }
            }
        });

        estadoActividad = true;
        new Task1().execute("holaa");
    }

    @Override
    protected void onStop() {
        super.onStop();
        estadoActividad = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        estadoActividad = true;
        new Task1().execute("holaa");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.manual:
                String temp = tempObj.getText().toString();
                String temperaturaAenviar = "['tempObj':'" + temp + "']";
                Toast.makeText(context, temperaturaAenviar, Toast.LENGTH_LONG).show();
                Objeto.SetParameters(context, temperaturaAenviar);
                break;
            case R.id.umbralUno:
                String hminima = HoraMinimaUmbralUno.getText().toString();
                String hmaxima = HoraMaximaimaUmbralUno.getText().toString();
                String temp2 = TempObjUmbralUno.getText().toString();
                String aEnviar = "['HoraMinimaUmbral1':" + hminima + ",'HoraMaximaUmbral1':" + hmaxima + ",'tempObjUmbral1':" + temp2 + "]";
                Toast.makeText(context, aEnviar, Toast.LENGTH_LONG).show();
                Objeto.SetParameters(context, aEnviar);
                break;
            case R.id.umbralDos:
                String hminima2 = HoraMinimaUmbralDos.getText().toString();
                String hmaxima2 = HoraMaximaUmbralDos.getText().toString();
                String temp3 = TempObjUmbralDos.getText().toString();
                String aEnviar3 = "['HoraMinimaUmbral2':" + hminima2 + ",'HoraMaximaUmbral2':" + hmaxima2 + ",'tempObjUmbral2':" + temp3 + "]";
                Toast.makeText(context, aEnviar3, Toast.LENGTH_LONG).show();
                Objeto.SetParameters(context, aEnviar3);
                break;

        }
    }

    void refresh()
    {
        Objeto.Refresh(context);
        temperatura.setText(Objeto.getTemp() + "°");
        setProgressB(Objeto.getTempDouble());
        tempObj.setText(Objeto.getTempObj());


        HoraMinimaUmbralUno.setText(Objeto.getHoraMinimaUmbral1());
        HoraMaximaimaUmbralUno.setText(Objeto.getHoraMaximaUmbral1());
        HoraMinimaUmbralDos.setText(String.valueOf(Objeto.getHoraMinimaUmbral2()));
        HoraMaximaUmbralDos.setText(String.valueOf(Objeto.getHoraMaximaUmbral2()));

        TempObjUmbralUno.setText(Objeto.getTempObjUmbral1());
        TempObjUmbralDos.setText(Objeto.getTempObjUmbral2());

        if(contadorRefrescar >= 2) {
        if(Objeto.getEstadoSist() == 1){SwitchEstadoManual.setChecked(true);}else {SwitchEstadoManual.setChecked(false);}
        if(Objeto.getEstadoSupUmbrales() == 1){SwitchSuperUmbrales.setChecked((true));} else {SwitchSuperUmbrales.setChecked(false);}
        if(Objeto.getEstadoUmbral1() == 1){SwitchEstadoUmbralUno.setChecked(true);} else {SwitchEstadoUmbralUno.setChecked(false);}
        if(Objeto.getEstadoUmbral2() ==1){SwitchEstadoUmbralDos.setChecked(true);} else {SwitchEstadoUmbralDos.setChecked(false);}
        }

    }


    public void setProgressB(double temperatura)
    {
        int progreso = (int) temperatura;

        int nProgreso = (progreso*70) / 90;

        if(nProgreso >= 0 && nProgreso <= 70 ){
            progreVerde.setProgress(nProgreso);
        }
        if(nProgreso > 70)
        {
            progreVerde.setProgress(70);
        }
        if(nProgreso < 0) {progreVerde.setProgress(1);}
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
                if (estadoActividad == true) {
                    try {
                        new Task1().execute("holaa");
                    } catch (Exception e) {
                    }
                }
            contadorRefrescar ++;
            if(contadorRefrescar > 4){contadorRefrescar = 2;}
            String a = "" + contadorRefrescar;
            Toast.makeText(context, a, Toast.LENGTH_LONG).show();
        }


    }

}


