package com.example.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
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
    private TextView mTextMessage;
    ListView lista;

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



        progres = (ProgressBar)findViewById(R.id.progressBar);
        lista = (ListView) findViewById(R.id.lvLista);

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

                }

            }
        });

    }


    class Task1 extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {
            progres.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {


               //   escanerRedDos();
                EscanearRed();

            return null;
        }


        @Override
        protected void onPostExecute(String s)
        {


            lista.setAdapter(new Adaptador(context, listaEquipos));

            progres.setVisibility(View.INVISIBLE);



        }


    }


    private void EscanearRed() {         // -> mejorar el algoritmo, no parcea el json aveces

        for (int x = 2; x < 254; x++) {
            String num = "" + x;
            final String url = "http://192.168.1." + num + "/";


            StringRequest sRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String respuesta) {

                            Toast.makeText(context, respuesta, Toast.LENGTH_LONG).show();

                            try {
                                JSONObject reader = new JSONObject(respuesta);

                                int tipo = reader.getInt("Dispositivo");
                                String nombre =  reader.getString("Nombre");

                                switch (tipo)
                                {

                                    case 1: listaEquipos.add(new TermotanqueSolar(url, context, nombre, tipo));
                                    break;
                                    default: Toast.makeText(MainActivity.this, "error de switch ESCANEAR RED", Toast.LENGTH_LONG).show();
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




