package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

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

public class NewDevice extends ObjetoHTTP {

    private boolean connectionStatus;

    NewDevice(final Context context, String url)
    {
        URL = url;


        final RequestQueue colaSolicitudes = Volley.newRequestQueue(context);


        StringRequest sRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {



                        try {
                            JSONObject reader = new JSONObject(respuesta);

                            NOMBRE = reader.getString("Nombre");
                            ID = (int) reader.get("id");
                            TIPO = (int) reader.get("Dispositivo");
                            connectionStatus = true;
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        connectionStatus = false;
                    }
                });



        sRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        colaSolicitudes.add(sRequest);

    }

/*
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
*/


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


    public boolean getConnectionStatus() {
        return connectionStatus;
    }


}
