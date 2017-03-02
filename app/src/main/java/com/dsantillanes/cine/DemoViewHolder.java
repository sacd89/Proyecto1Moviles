package com.dsantillanes.cine;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SONU on 27/03/16.
 */
public class DemoViewHolder extends RecyclerView.ViewHolder {


    public TextView titulo;
    public TextView clasificacion;
    public ImageView portada;

    public DemoViewHolder(View view) {
        super(view);


        this.titulo = (TextView) view.findViewById(R.id.person_name);
        this.portada = (ImageView) view.findViewById(R.id.person_photo);
        this.clasificacion = (TextView) view.findViewById(R.id.clasificacion);

    }
}