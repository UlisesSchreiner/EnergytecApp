package com.example.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    final Context context = this;




    final ArrayList <ObjetoHTTP> listaEquipos = new ArrayList<ObjetoHTTP>();

    private ProgressBar progres;
    private TextView mTextMessage, errorText;
    ListView lista;
    private ImageView error;
    private Button errorBoton;
    private ImageButton resfreshBoton, addBoton;
    //++++++++++++++++//


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                   //++++++++++++//

        error = (ImageView)findViewById(R.id.error);
        progres = (ProgressBar)findViewById(R.id.progressBar);
        lista = (ListView) findViewById(R.id.lvLista);
        errorText = (TextView)findViewById(R.id.errorText);
        errorBoton = (Button)findViewById(R.id.errorBoton);
        resfreshBoton = (ImageButton)findViewById(R.id.refreshbuton);
        addBoton = (ImageButton) findViewById(R.id.addBoton);

        new Task1().execute("holaa");


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (listaEquipos.get(position).TIPO)
                {
                    case 1: Intent visorTermotanque = new Intent(view.getContext(), VisorTermotanque.class);

                        Bundle bundle = new Bundle();

                        bundle.putSerializable("objeto",listaEquipos.get(position));
                        visorTermotanque.putExtras(bundle);

                        startActivity(visorTermotanque);
                        break;
                    case 2:
                        Intent visorTempandHum = new Intent(view.getContext(), VisorTempAndHumSensor.class);

                        Bundle bundle2 = new Bundle();

                        bundle2.putSerializable("objeto",listaEquipos.get(position));
                        visorTempandHum.putExtras(bundle2);

                        startActivity(visorTempandHum);
                        break;
                    case 3:
                        Intent visorPowerMeeter = new Intent(view.getContext(), VisorPowerMeeter.class);

                        Bundle bundle3 = new Bundle();
                        bundle3.putSerializable("objeto", listaEquipos.get(position));
                        visorPowerMeeter.putExtras(bundle3);
                        startActivity(visorPowerMeeter);
                        break;

                }

            }
        });

        errorBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEquipos.clear();
                new Task1().execute("holaa");
            }
        });


        resfreshBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEquipos.clear();
                new Task1().execute("holaa");
            }
        });

        addBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addDevice = new Intent(context, addDevice.class);
                startActivity(addDevice);

            }
        });

        }



    class Task1 extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {
            progres.setVisibility(View.VISIBLE);
            error.setVisibility(View.INVISIBLE);
            errorBoton.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {


                EscanearRed();

            return null;
        }


        @Override
        protected void onPostExecute(String s)
        {

            if(listaEquipos.size() > 0) {
                lista.setAdapter(new Adaptador(context, listaEquipos));
            }else {error.setVisibility(View.VISIBLE);
                   errorBoton.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.VISIBLE);
            }
            progres.setVisibility(View.INVISIBLE);

        }


    }


    private void EscanearRed() {         // -> mejorar el algoritmo, si encuentra en la .0 que no escanee la .0 ...


        for (int i = 0; i < 2; i++){
            String num0 = "" + i;

            for (int x = 2; x < 255; x++) {
                String num = "" + x;
                final String url = "http://192.168." + num0 + "." + num + ":43257/";


                StringRequest sRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String respuesta) {

                             Toast.makeText(context, respuesta, Toast.LENGTH_LONG).show();

                             try {
                                    JSONObject reader = new JSONObject(respuesta);

                                    int tipo = reader.getInt("Dispositivo");
                                    String nombre = reader.getString("Nombre");

                                    switch (tipo) {

                                        case 1:
                                            listaEquipos.add(new TermotanqueSolar(url, context, nombre, tipo));
                                            break;
                                        case 2:
                                            listaEquipos.add(new TempAndHumSensor(url, context,nombre,tipo));
                                            break;
                                        case 3:
                                            listaEquipos.add(new PowerMeeter(url,context,nombre,tipo));
                                            break;
                                      default:
                                            Toast.makeText(MainActivity.this, "error de switch ESCANEAR RED", Toast.LENGTH_LONG).show();
                                            break;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "error de parcear json", Toast.LENGTH_LONG).show();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                sRequest.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue colaSolicitudes = Volley.newRequestQueue(this);
                colaSolicitudes.add(sRequest);
            }
        }
    }


    }




