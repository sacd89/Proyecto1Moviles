package com.dsantillanes.cine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dsantillanes.cine.enums.Clasificacion;
import com.dsantillanes.cine.enums.Genero;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.dsantillanes.cine.MainActivity.cine;
import static com.dsantillanes.cine.MainActivity.listaPeliculas;
import static com.dsantillanes.cine.MainActivity.listaSeries;

public class CreateSerie extends AppCompatActivity {

    Button btnAddImage;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
    Spinner spinnerGenero, spinnerClasificacion;
    String titulo, director, actores, descripcion, foto, txtGenero, txtClasificacion;
    TextView txtTitulo, txtDirector, txtActores, txtTemporadas, txtDescripcion;
    Genero genero;
    Clasificacion clasificacion;
    Integer temporadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_serie);
        initToolbar();

        imageView = (ImageView) findViewById(R.id.imgView);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtDirector = (TextView) findViewById(R.id.txtDirector);
        txtActores = (TextView) findViewById(R.id.txtActores);
        txtTemporadas = (TextView) findViewById(R.id.txtTemporadas);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        btnAddImage = (Button) findViewById(R.id.buttonLoadPicture);
        btnAddImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        spinnerGenero = (Spinner) findViewById(R.id.spinnerGenero);
        spinnerGenero.setAdapter(new ArrayAdapter<Genero>(this, android.R.layout.simple_spinner_item, Genero.values()));
        spinnerClasificacion = (Spinner) findViewById(R.id.spinnerClasificacion);
        spinnerClasificacion.setAdapter(new ArrayAdapter<Clasificacion>(this, android.R.layout.simple_spinner_item, Clasificacion.values()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_serie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.createSerie:
                call();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Agregar Serie");
    }

    public void call(){
        titulo = String.valueOf(txtTitulo.getText());
        descripcion = String.valueOf(txtDescripcion.getText());
        director = String.valueOf(txtDirector.getText());
        actores = String.valueOf(txtActores.getText());
        temporadas = Integer.valueOf(String.valueOf(txtTemporadas.getText()));
        txtGenero = spinnerGenero.getSelectedItem().toString();
        genero = Genero.valueOf(txtGenero);
        txtClasificacion = spinnerClasificacion.getSelectedItem().toString();
        clasificacion = Clasificacion.valueOf(txtClasificacion);
        listaSeries = cine.addSerie(titulo,director,actores,genero,temporadas,foto,descripcion,clasificacion,listaSeries);
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            decodeUri(data.getData());
        }


    }

    public void decodeUri(Uri uri) {
        ParcelFileDescriptor parcelFD = null;
        try {
            parcelFD = getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor imageSource = parcelFD.getFileDescriptor();

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(imageSource, null, o);

            // the new size we want to scale to
            final int REQUIRED_SIZE = 1024;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);

            imageView.setImageBitmap(bitmap);
            BitMapToString(bitmap);
            foto = "data:image/jpeg;base64,"+BitMapToString(bitmap);
        } catch (FileNotFoundException e) {
            // handle errors
        } catch (IOException e) {
            // handle errors
        } finally {
            if (parcelFD != null)
                try {
                    parcelFD.close();
                } catch (IOException e) {
                    // ignored
                }
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
