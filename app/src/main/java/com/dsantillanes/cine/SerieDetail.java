package com.dsantillanes.cine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsantillanes.cine.enums.Clasificacion;
import com.dsantillanes.cine.enums.Genero;

import java.io.FileNotFoundException;

public class SerieDetail extends AppCompatActivity {

    String nombre;
    String director;
    String descripcion;
    String actor;
    Integer temporadas;

    TextView txtTemporadas,txtDirector,txtActor,txtGenero,txtClasificacion, txtDescripcion;

    Genero genero;

    Clasificacion clasificacion;

    ImageView cabezera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = getIntent().getStringExtra("nombre");
        director = getIntent().getStringExtra("director");
        actor = getIntent().getStringExtra("actor");
        temporadas = getIntent().getIntExtra("temporadas", 0);
        genero = (Genero) getIntent().getSerializableExtra("genero");
        clasificacion = (Clasificacion) getIntent().getSerializableExtra("clasificacion");
        descripcion = getIntent().getStringExtra("descripcion");
        System.out.println("###################################" + temporadas);

        getSupportActionBar().setTitle(nombre);
        txtDirector = (TextView) findViewById(R.id.director);
        txtActor = (TextView) findViewById(R.id.actor);
        txtTemporadas = (TextView) findViewById(R.id.temporadas);
        txtGenero = (TextView) findViewById(R.id.genero);
        cabezera = (ImageView) findViewById(R.id.cabezera);
        txtClasificacion = (TextView) findViewById(R.id.clasificacion);
        txtDescripcion = (TextView) findViewById(R.id.descripcion);

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this
                    .openFileInput("myImage"));
            cabezera.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        txtTemporadas.setText(String.valueOf(temporadas));
        txtDirector.setText(director);
        txtActor.setText(actor);
        txtGenero.setText(String.valueOf(genero));
        txtClasificacion.setText(String.valueOf(clasificacion));
        txtDescripcion.setText(descripcion);

    }
}
