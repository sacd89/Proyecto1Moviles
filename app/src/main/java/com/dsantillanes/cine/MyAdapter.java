package com.dsantillanes.cine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.dsantillanes.cine.MainActivity.StringToBitMap;

/**
 * Created by dsantillanes on 28/02/17.
 */

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Pelicula> peliculas;


    public MyAdapter(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.picture_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {

        String foto = peliculas.get(i).getFoto();
        final int DEFAULT_OFFSET_IMG = 22;
        byte[] decodedString = Base64.decode(foto.substring(DEFAULT_OFFSET_IMG), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + peliculas.get(i).getNombre());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + peliculas.get(i).getDirectorPpal());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + peliculas.get(i).getActorPpal());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + peliculas.get(i).getGenero());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + peliculas.get(i).getFoto());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + peliculas.get(i).getDuracion());


        viewHolder.txtNombrePelicula.setText(String.valueOf(peliculas.get(i).getNombre()));
        viewHolder.ivFotoPelicula.setImageBitmap(decodedByte);

    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNombrePelicula;
        private ImageView ivFotoPelicula;
        public ViewHolder(View view) {
            super(view);

            txtNombrePelicula = (TextView)view.findViewById(R.id.person_name);
            ivFotoPelicula = (ImageView)view.findViewById(R.id.person_photo);
        }


    }

}
