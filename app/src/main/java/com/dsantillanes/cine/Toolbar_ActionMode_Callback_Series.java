package com.dsantillanes.cine;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


/**
 * Created by SONU on 22/03/16.
 */
public class Toolbar_ActionMode_Callback_Series implements ActionMode.Callback {

    private Context context;
    private AdapterSeries recyclerView_adapter;
    private ArrayList<Serie> message_models;
    private Integer position;


    public Toolbar_ActionMode_Callback_Series(Context context, AdapterSeries recyclerView_adapter, ArrayList<Serie> message_models, Integer position) {
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
                    recyclerFragment = new MainActivity().getFragment(1);//Get recycler view fragment
                    if (recyclerFragment != null)
                        //If recycler fragment not null
                        ((SeriesFragment) recyclerFragment).deleteRows(position);//delete selected rows
                break;
        }
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
            recyclerView_adapter.removeSelection();  // remove selection
            Fragment recyclerFragment = new MainActivity().getFragment(1);//Get recycler fragment
            if (recyclerFragment != null)
                ((SeriesFragment) recyclerFragment).setNullToActionMode();//Set action mode null

    }
}
