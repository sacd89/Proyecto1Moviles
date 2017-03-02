package com.dsantillanes.cine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dsantillanes.cine.enums.Clasificacion;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.dsantillanes.cine.MainActivity.*;

/**
 * Created by dsantillanes on 28/02/17.
 */

public class AdapterPictures extends RecyclerView.Adapter<DemoViewHolder> {

    private static Context context;
    private SparseBooleanArray mSelectedItemsIds;
    private static int selectedPos = 0;
    public List<Pelicula> idItemSeleccionado;


    public AdapterPictures(Context context,
                             ArrayList<Pelicula> arrayList) {
        this.context = context;
        listaPeliculas = arrayList;
        mSelectedItemsIds = new SparseBooleanArray();

    }


    @Override
    public int getItemCount() {
        return (null != listaPeliculas ? listaPeliculas.size() : 0);

    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder,
                                 int i) {

        Bitmap b = StringToBitMap(listaPeliculas.get(i).getFoto()); // your bitmap
        createImageFromBitmap(b);
        Clasificacion clasificacion = listaPeliculas.get(i).getClasificacion();
        holder.titulo.setText(String.valueOf(listaPeliculas.get(i).getNombre()));
        holder.clasificacion.setText("Clasificación " + clasificacion);
        holder.portada.setImageBitmap(b);
        holder.itemView
                .setBackgroundColor(mSelectedItemsIds.get(i) ? 0x9934B5E4
                        : Color.TRANSPARENT);


    }


    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    @Override
    public DemoViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.picture_card, viewGroup, false);
        return new DemoViewHolder(mainGroup);

    }

    /***
     * Methods required for do selections, remove selections, etc.
     */

    //Toggle selection methods
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
        idItemSeleccionado = new ArrayList<>();
        //Igualacion de id del item seleccionado
        System.out.println("arrayList = " + listaPeliculas.get(position));
        idItemSeleccionado.add(listaPeliculas.get(position));
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }


    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }



}
