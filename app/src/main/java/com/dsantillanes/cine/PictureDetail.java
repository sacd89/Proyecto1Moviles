package com.dsantillanes.cine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class PictureDetail extends AppCompatActivity {

    String nombre,director,actor,duracion,genero,foto;

    TextView txtNombre,txtDirector,txtActor,txtDuracion,txtGenero;

    ImageView cabezera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = getIntent().getStringExtra("nombre");
        director = getIntent().getStringExtra("director");
        actor = getIntent().getStringExtra("actor");
        duracion = getIntent().getStringExtra("duracion");
        genero = getIntent().getStringExtra("genero");

        getSupportActionBar().setTitle(nombre);
        txtDirector = (TextView) findViewById(R.id.director);
        txtActor = (TextView) findViewById(R.id.actor);
        txtDuracion = (TextView) findViewById(R.id.duracion);
        txtGenero = (TextView) findViewById(R.id.genero);
        cabezera = (ImageView) findViewById(R.id.cabezera);

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this
                    .openFileInput("myImage"));
            cabezera.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        txtDuracion.setText(duracion);
        txtDirector.setText(director);
        txtActor.setText(actor);
        txtGenero.setText(genero);


    }

}
