package com.example.myapplication;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class PowerMeeter extends ObjetoHTTP {


    private double volt, amper, watt;
    private int calVolt, calAmper, calWatt;
    private int EstadoPasChange;
    private boolean estConexion;


    PowerMeeter(String url, final Context context, String nombre, int tipo)
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
                                volt = (double) reader.get("volt");
                            } catch (Exception e) {
                                volt = (int) reader.getInt("volt");
                            }
                            try{
                                amper = (double) reader.get("amper");
                            } catch (Exception e){
                                amper = (int) reader.getInt("amper");
                            }
                            try {
                                watt = (double) reader.get("watt");
                            } catch (Exception e) {
                                watt = (int) reader.getInt("watt");
                            }
                            calVolt = (int) reader.getInt("calVolt");
                            calAmper = (int) reader.getInt("calAmper");
                            calWatt = (int) reader.getInt("calWatt");
                            NOMBRE = reader.getString("Nombre");
                            ID = (int) reader.get("id");
                            EstadoPasChange = (int) reader.get("estPassChang");
                            // estConexion = (boolean) reader.get("estadoConexion");
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
                                volt = (double) reader.get("volt");
                            } catch (Exception e) {
                                volt = (int) reader.getInt("volt");
                            }
                            try{
                                amper = (double) reader.get("amper");
                            } catch (Exception e){
                                amper = (int) reader.getInt("amper");
                            }
                            try {
                                watt = (double) reader.get("watt");
                            } catch (Exception e) {
                                watt = (int) reader.getInt("watt");
                            }
                            calVolt = (int) reader.getInt("calVolt");
                            calAmper = (int) reader.getInt("calAmper");
                            calWatt = (int) reader.getInt("calWatt");
                            NOMBRE = reader.getString("Nombre");
                            ID = (int) reader.get("id");
                            EstadoPasChange = (int) reader.get("estPassChang");
                            //estConexion = (boolean) reader.get("estadoConexion");
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



    public void SetParameters(Context context, final String data)
    {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public String getBodyContentType() { return "application/json; charset=utf-8"; }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

        };
        requestQueue.add(postRequest);
    }


    public double getVolt() {
        return volt;
    }

    public double getAmper() {
        return amper;
    }

    public double getWatt(){
        return watt;
    }

    public String getStringVolt(){
        String res = "" + volt;
        return res;
    }

    public String getStringAmper(){
        String res = "" + amper;
        return res;
    }

    public String getStringWatt() {
        String res = "" + watt;
        return res;
    }

    public String getStringCalVolt() {
        String res = "" + calVolt;
        return res;
    }

    public String getStringCalAmper(){
        String res = "" + calAmper;
        return res;
    }

    public String getStringCalWatt(){
        String res = "" + calWatt;
        return res;
    }

}
