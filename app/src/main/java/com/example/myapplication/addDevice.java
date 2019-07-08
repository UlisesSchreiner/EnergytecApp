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
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class addDevice extends AppCompatActivity {

    final Context context = this;

    NewDevice Objeto = null;

    Button escanear;
    TextView textDevice;
    ImageView imgDevice;
    EditText name;
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

    }



    class Task1 extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(String... strings) {

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


            if(Objeto.getConnectionStatus())
            {
                switch (Objeto.TIPO)
                {
                    case 1:
                        textDevice.setText("Termotanque solar Encontrado");
                        imgDevice.setImageResource(R.drawable.termotanque);
                        name.setText(Objeto.NOMBRE);
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


}
