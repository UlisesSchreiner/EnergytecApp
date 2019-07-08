package com.example.myapplication;

import android.content.Context;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import static java.lang.Thread.sleep;

public class TermotanqueSolar extends ObjetoHTTP {

    private double estadoTemperatura;
    private int estadoSist;
    private int tempObj;
    private int estadoSupUmbrales;
    private int estadoUmbral1;
    private int tempObjUmbral1;
    private int HoraMinimaUmbral1;
    private int HoraMaximaUmbral1;
    private int estadoUmbral2;
    private int tempObjUmbral2;
    private int HoraMinimaUmbral2;
    private int HoraMaximaUmbral2;
    private int estCalefactor;
    private int ZonaHoraria;
    private int EstadoPasChange;
    private String Hora;
    private boolean estConexion;




    TermotanqueSolar(String url, final Context context, String nombre, int tipo)
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
                                        estadoTemperatura = (double) reader.get("estadoTemperatura");
                                    } catch (Exception e) {
                                        estadoTemperatura = (int) reader.getInt("estadoTemperatura");
                                    }
                                    NOMBRE = reader.getString("Nombre");
                                    estadoSist = (int) reader.get("estadoSist");
                                    tempObj = (int) reader.get("tempObj");
                                    estadoSupUmbrales = (int) reader.get("estadoSupUmbrales");
                                    estadoUmbral1 = (int) reader.get("estadoUmbral1");
                                    tempObjUmbral1 = (int) reader.get("tempObjUmbral1");
                                    HoraMinimaUmbral1 = (int) reader.get("HoraMinimaUmbral1");
                                    HoraMaximaUmbral1 = (int) reader.get("HoraMaximaUmbral1");
                                    estadoUmbral2 = (int) reader.get("estadoUmbral2");
                                    tempObjUmbral2 = (int) reader.get("tempObjUmbral2");
                                    HoraMinimaUmbral2 = (int) reader.get("HoraMinimaUmbral2");
                                    HoraMaximaUmbral2 = (int) reader.get("HoraMaximaUmbral2");
                                    estCalefactor = (int) reader.get("estCalefactor");
                                    ZonaHoraria = (int) reader.get("ZonaHoraria");
                                    ID = (int) reader.get("id");
                                    EstadoPasChange = (int) reader.get("estPassChang");
                                    Hora = (String) reader.get("hora");
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
                                estadoTemperatura = (double) reader.get("estadoTemperatura");
                            } catch (Exception e) {
                                estadoTemperatura = (int) reader.getInt("estadoTemperatura");
                            }

                            NOMBRE = reader.getString("Nombre");
                            estadoSist = (int) reader.get("estadoSist");
                            tempObj = (int) reader.get("tempObj");
                            estadoSupUmbrales = (int) reader.get("estadoSupUmbrales");
                            estadoUmbral1 = (int) reader.get("estadoUmbral1");
                            tempObjUmbral1 = (int) reader.get("tempObjUmbral1");
                            HoraMinimaUmbral1 = (int) reader.get("HoraMinimaUmbral1");
                            HoraMaximaUmbral1 = (int) reader.get("HoraMaximaUmbral1");
                            estadoUmbral2 = (int) reader.get("estadoUmbral2");
                            tempObjUmbral2 = (int) reader.get("tempObjUmbral2");
                            HoraMinimaUmbral2 = (int) reader.get("HoraMinimaUmbral2");
                            HoraMaximaUmbral2 = (int) reader.get("HoraMaximaUmbral2");
                            estCalefactor = (int) reader.get("estCalefactor");
                            ZonaHoraria = (int) reader.get("ZonaHoraria");
                            ID = (int) reader.get("id");
                            EstadoPasChange = (int) reader.get("estPassChang");
                            Hora = (String) reader.get("hora");
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

public double getTempDouble()
{
    return estadoTemperatura;
}

  public String getTemp()
 {
     String res = "" + estadoTemperatura;
     return res;
 }

    public int getEstadoSist() {
        return estadoSist;
    }

    public String getTempObj() {
        String res = "" + tempObj;
        return res;
    }

    public int getEstadoSupUmbrales() {
        return estadoSupUmbrales;
    }

    public int getEstadoUmbral1() {
        return estadoUmbral1;
    }

    public String getTempObjUmbral1() {
        String res = "" + tempObjUmbral1;
        return res;
    }

    public String getHoraMinimaUmbral1() {
    String res = "" + HoraMinimaUmbral1;
        return res;
    }

    public String getHoraMaximaUmbral1() {
        String res = "" + HoraMaximaUmbral1;
        return res;
    }

    public int getEstadoUmbral2() {
        return estadoUmbral2;
    }

    public String getTempObjUmbral2() {
        String res = "" + tempObjUmbral2;
        return res;
    }

    public String getHoraMinimaUmbral2() {
        String res = "" + HoraMinimaUmbral2;
        return res;
    }

    public String getHoraMaximaUmbral2() {
        String res = "" + HoraMaximaUmbral2;
        return res;
    }

    public String getEstCalefactor() {
        String res = "" + estCalefactor;
        return res;
    }

    public String getZonaHoraria() {
        String res = "" + ZonaHoraria;
        return res;
    }

    public String getEstadoPasChange() {
        String res = "" + EstadoPasChange;
        return res;
    }

    public String getHora() {
        return Hora;
    }

    public String isEstConexion() {
        String res = "" + estConexion;
        return res;
    }
}
