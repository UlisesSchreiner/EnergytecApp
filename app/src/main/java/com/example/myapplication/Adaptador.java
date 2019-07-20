package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context contexto;

    private int Dimension_arreglo;
    ArrayList<String> Nombres = new ArrayList<>();
    ArrayList<Integer> Imagenes = new ArrayList<>();


    public Adaptador(Context context, ArrayList<ObjetoHTTP>listObjetos)
    {
        this.contexto = context;

        int dimension =  listObjetos.size();
        Dimension_arreglo = dimension;

        for (int x = 0; x < dimension; x++)
        {
                String nombre = listObjetos.get(x).NOMBRE;

                if (nombre == null){
                    Nombres.add("sispositivo desconocido");
                }else {
                    Nombres.add(nombre);
                }


            switch (listObjetos.get(x).TIPO)
            {
                case 1: Imagenes.add(R.drawable.termotanque);
                break;
                case 2: Imagenes.add(R.drawable.tempandhummidity);
                break;
                default: Imagenes.add(R.drawable.error);
                break;
            }

        }


        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista, null);

        TextView titulo = (TextView) vista.findViewById(R.id.Temperatura);
        ImageView imagen = (ImageView) vista.findViewById(R.id.lvImagen);

        titulo.setText(Nombres.get(position));              // set title
        imagen.setImageResource(Imagenes.get(position));    // set images


        return vista;
    }

    @Override
    public int getCount() {
        return Dimension_arreglo;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
