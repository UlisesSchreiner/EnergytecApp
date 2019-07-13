package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class addDevice extends AppCompatActivity {

    final Context context = this;

    NewDevice Objeto = null;

    ProgressBar progressABM;
    Button escanear,connect;
    TextView textDevice;
    ImageView imgDevice;
    EditText name, ssid, pass;
    ImageButton buttonName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        escanear = (Button) findViewById(R.id.buttonEscan);
        textDevice = (TextView) findViewById(R.id.textDevice);
        imgDevice = (ImageView) findViewById(R.id.imageDevice);
        name = (EditText) findViewById(R.id.editTextName);
        buttonName = (ImageButton) findViewById(R.id.imageButtonName);
        connect = (Button) findViewById(R.id.buttonConectar);
        ssid = (EditText) findViewById(R.id.editTextSsid);
        pass = (EditText) findViewById(R.id.editTextPass);
        progressABM = (ProgressBar) findViewById(R.id.progressABM);

        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Task1().execute("holaa");
            }
        });

        buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable Name = name.getText();
                String aEnviar = "['ssidAP':'" + Name + "']";
                Objeto.SetParameters(context, aEnviar);
                Toast.makeText(context, aEnviar, Toast.LENGTH_LONG).show();
            }
        });

        connect.setOnClickListener(new View.OnClickListener() { // -> crashea al precionar
            @Override
            public void onClick(View v) {
            new Task2().execute("holaa");
            }
        });
        DiseabledOrEnabled(false);
        SetProgress(false);
    }

    void DiseabledOrEnabled(boolean state)
    {
        name.setEnabled(state);
        ssid.setEnabled(state);
        pass.setEnabled(state);
        buttonName.setEnabled(state);
        connect.setEnabled(state);
    }

    void SetProgress(boolean state)                         // -> los progress crashean !
    {
        try {
            if (state) {
                progressABM.setVisibility(View.VISIBLE);
            } else {
                progressABM.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e){}
    }

    class Task1 extends AsyncTask<String, Void, String> // -> Obtiene mal las credenciales de los edit text
    {

        @Override
        protected void onPreExecute()
        {
            SetProgress(true);
        }

        @Override
        protected String doInBackground(String... strings) {

            DiseabledOrEnabled(false);



            Objeto = new NewDevice(context, "http://192.168.4.1");


            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s)
        {

            SetProgress(false);
            if(Objeto.getConnectionStatus())
            {
                DiseabledOrEnabled(true);
                name.setText(Objeto.NOMBRE);

                switch (Objeto.TIPO)
                {
                    case 1:
                        textDevice.setText("Termotanque solar Encontrado");
                        imgDevice.setImageResource(R.drawable.termotanque);
                        break;
                        default:
                            textDevice.setText("No se encontro equipo");
                            break;
                }
            } else {
                textDevice.setText("No se encontro equipo");
            }

        }


    }

    class Task2 extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(String... strings) { //-> obtiene mal el string de los campos credenciales

            Editable Ssid = ssid.getText();
            Editable Pass = pass.getText();
            String Aenviar = "['ssid':'" + ssid + "','pass':'" + pass + "']";
            Objeto.SetParameters(context, Aenviar);


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

            String aEnviar = "['123':123]";
            Objeto.SetParameters(context, aEnviar);

        }


    }


}
