package com.gutierrez.agenda_semana10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<amigo> datosAmigosArrayList;
    LayoutInflater layoutInflater;
    amigo misAmigos;

    public AdaptadorImagenes(Context context, ArrayList<amigo> datosAmigosArrayList) {
        this.context = context;
        this.datosAmigosArrayList = datosAmigosArrayList;
    }

    @Override
    public int getCount() {
        return datosAmigosArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return datosAmigosArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(datosAmigosArrayList.get(i).getIdAmigo());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View Visor = layoutInflater.inflate(R.layout.listview_imagenes,viewGroup,false);
        TextView temp = Visor.findViewById(R.id.lblAmigo);

        try {
            misAmigos = datosAmigosArrayList.get(i);
            temp.setText(misAmigos.getName());

            temp = Visor.findViewById(R.id.lblNumero);
            temp.setText("Numero: " + misAmigos.getNumber());


            temp = Visor.findViewById(R.id.lblCorreo);
            temp.setText("Correo: " + misAmigos.getEmail());


        }catch (Exception e){
            mensajeToast(e.getMessage());
        }

        return Visor;
    }

    private void mensajeToast(String msg){
        Toast.makeText(context.getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
}
