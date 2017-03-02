package com.dsantillanes.cine;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.dsantillanes.cine.MainActivity.*;
import java.util.ArrayList;


/**
 * Created by SONU on 22/03/16.
 */
public class Toolbar_ActionMode_Callback_Pictures implements ActionMode.Callback {

    private Context context;
    private AdapterPictures recyclerView_adapter;
    private ArrayList<Pelicula> message_models;
    private Integer position;


    public Toolbar_ActionMode_Callback_Pictures(Context context, AdapterPictures recyclerView_adapter, ArrayList<Pelicula> message_models, Integer position) {
        this.context = context;
        this.recyclerView_adapter = recyclerView_adapter;
        this.message_models = message_models;
        this.position = position;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.context, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            menu.findItem(R.id.item_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Fragment recyclerFragment;
        switch (item.getItemId()) {
            case R.id.item_delete:

                //Check if current action mode is from ListView Fragment or RecyclerView Fragment
                    //If current fragment is recycler view fragment
                    recyclerFragment = new MainActivity().getFragment(0);//Get recycler view fragment
                    if (recyclerFragment != null)
                        //If recycler fragment not null
                        ((PeliculasFragment) recyclerFragment).deleteRows(position);//delete selected rows
                break;
        }
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
            recyclerView_adapter.removeSelection();  // remove selection
            Fragment recyclerFragment = new MainActivity().getFragment(0);//Get recycler fragment
            if (recyclerFragment != null)
                ((PeliculasFragment) recyclerFragment).setNullToActionMode();//Set action mode null

    }
}
