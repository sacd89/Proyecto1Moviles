package com.dsantillanes.cine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsantillanes.cine.enums.Clasificacion;
import com.dsantillanes.cine.enums.Genero;

import static com.dsantillanes.cine.MainActivity.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class SeriesFragment extends Fragment {

    private static View view;
    private static RecyclerView recyclerView;
    private static AdapterSeries adapter;
    private ActionMode mActionMode;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_series, container, false);
        populateRecyclerView();
        implementRecyclerViewClickListeners();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_serie, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    //Populate ListView with dummy data
    private void populateRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recicladorSeries);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AdapterSeries(getActivity(), listaSeries);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Implement item click and long click over recycler view
    private void implementRecyclerViewClickListeners() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
                view.setSelected(true);
                Intent i = new Intent(getContext(), SerieDetail.class);
                i.putExtra("nombre", listaSeries.get(position).getNombre());
                i.putExtra("director", listaSeries.get(position).getDirectorPpal());
                i.putExtra("actor", listaSeries.get(position).getActores());
                i.putExtra("genero", listaSeries.get(position).getGenero());
                i.putExtra("clasificacion", listaSeries.get(position).getClasificacion());
                i.putExtra("descripcion", listaSeries.get(position).getDescripcion());
                i.putExtra("temporadas", listaSeries.get(position).getTemporadas());

                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+listaSeries.get(position).getTemporadas());

                Bitmap b = StringToBitMap(listaSeries.get(position).getFoto()); // your bitmap
                createImageFromBitmap(b);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
                view.setSelected(true);
            }
        }));
    }

    //List item select method
    private void onListItemSelect(int position) {
        adapter.toggleSelection(position);//Toggle the selection



        boolean hasCheckedItems = adapter.getSelectedCount() == 1;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null) {
            // there are some selected items, start the actionMode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new Toolbar_ActionMode_Callback_Series(getActivity(), adapter, listaSeries, position));
        }else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null) {
            //set action mode title on item selection

            mActionMode.setTitle(String.valueOf(adapter
                    .getSelectedCount()) + " Seleccionada");
            System.out.println("selectedID=============== = " + adapter.getSelectedIds());

        }


    }
    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null)
            mActionMode = null;
    }

    //Delete selected rows
    public void deleteRows(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.preguntaEliminar);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.eliminarAlert, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SparseBooleanArray selected = adapter
                        .getSelectedIds();//Get selected ids
                adapter.idItemSeleccionado = new ArrayList<>();
                adapter.idItemSeleccionado.remove(selected);
                cine.deleteSerie(listaSeries.get(position).getNombre(), listaSeries);
                mActionMode.finish();//Finish action mode after use
            }
        });
        builder.setNegativeButton(R.string.cancelarAlert, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mActionMode.finish();//Finish action mode after use
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void updateRows() {
        SparseBooleanArray selected = adapter
                .getSelectedIds();//Get selected ids
        adapter.idItemSeleccionado = new ArrayList<>();
        mActionMode.finish();//Finish action mode after use

    }

    public static AdapterSeries getAdapter() {
        return adapter;
    }

    public static void setAdapter(AdapterSeries adapter) {
        SeriesFragment.adapter = adapter;
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static void setRecyclerView(RecyclerView recyclerView) {
        SeriesFragment.recyclerView = recyclerView;
    }

    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            final int DEFAULT_OFFSET_IMG = 22;
            byte[] decodedString = Base64.decode(encodedString.substring(DEFAULT_OFFSET_IMG), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
