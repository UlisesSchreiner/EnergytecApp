package com.example.myapplication;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TempAndHumSensor extends ObjetoHTTP {


    private double temperatura, humedad;
    private int calTemp, calHum;
    private int ZonaHoraria;
    private int EstadoPasChange;
    private boolean estConexion;


    TempAndHumSensor(String url, final Context context, String nombre, int tipo)
    {
        TIPO = tipo;
        NOMBRE = nombre;
        URL = url;


        final RequestQueue colaSolicitudes = Volley.newRequestQueue(context);


        StringRequest sRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {



                        try {
                            JSONObject reader = new JSONObject(respuesta);

                            try {
                                temperatura = (double) reader.get("temperatura");
                            } catch (Exception e) {
                                temperatura = (int) reader.getInt("temperatura");
                            }
                            try{
                                humedad = (double) reader.get("humedad");
                            } catch (Exception e){
                                humedad = (int) reader.getInt("humedad");
                            }
                            calHum = (int) reader.getInt("calHum");
                            calTemp = (int) reader.getInt("calTemp");
                            NOMBRE = reader.getString("Nombre");
                            ZonaHoraria = (int) reader.get("ZonaHoraria");
                            ID = (int) reader.get("id");
                            EstadoPasChange = (int) reader.get("estPassChang");
                            estConexion = (boolean) reader.get("estadoConexion");
                            TIPO = (int) reader.get("Dispositivo");

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });



        sRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        colaSolicitudes.add(sRequest);


    }


    public void Refresh(Context context)
    {

        final RequestQueue colaSolicitudes = Volley.newRequestQueue(context);


        StringRequest sRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {



                        try {
                            JSONObject reader = new JSONObject(respuesta);

                            try {
                                temperatura = (double) reader.get("temperatura");
                            } catch (Exception e) {
                                temperatura = (int) reader.getInt("temperatura");
                            }
                            try{
                                humedad = (double) reader.get("humedad");
                            } catch (Exception e){
                                humedad = (int) reader.getInt("humedad");
                            }
                            calHum = (int) reader.getInt("calHum");
                            calTemp = (int) reader.getInt("calTemp");
                            NOMBRE = reader.getString("Nombre");
                            ZonaHoraria = (int) reader.get("ZonaHoraria");
                            ID = (int) reader.get("id");
                            EstadoPasChange = (int) reader.get("estPassChang");
                            estConexion = (boolean) reader.get("estadoConexion");
                            TIPO = (int) reader.get("Dispositivo");

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });



        sRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        colaSolicitudes.add(sRequest);


    }

    public void SetParameters(Context context, String mensaje)
    {
        String url = URL + mensaje;

        final RequestQueue colaSolicitudes = Volley.newRequestQueue(context);

        StringRequest sRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        sRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        colaSolicitudes.add(sRequest);
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public String getStringTem(){
        String res = "" + temperatura;
        return res;
    }

    public String getStringHum(){
        String res = "" + humedad;
        return res;
    }

    public String getCalTempString() {
        String res = "" + calTemp;
        return res;
    }

    public String getCalHumString() {
        String res = "" + calHum;
        return res;
    }
}
