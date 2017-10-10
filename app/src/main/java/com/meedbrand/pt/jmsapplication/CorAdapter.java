package com.meedbrand.pt.jmsapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Márcio Pinto on 15/10/2015.
 */
public class CorAdapter extends RecyclerView.Adapter<CorAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Cor> cores;

    public CorAdapter(Context context, ArrayList<Cor> cores) {
        this.context = context;
        this.cores = cores;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cores_, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        Cor c = cores.get(position);

        viewHolder.txtViewRef.setText(c.getRef());
        if (Global.idioma == "PT"){
            viewHolder.txtViewCor.setText(c.getCor());
        } else if (Global.idioma == "EN"){
            viewHolder.txtViewCor.setText(c.getCorEn());
        } else if (Global.idioma == "FR"){
            viewHolder.txtViewCor.setText(c.getCorFr());
        } else if (Global.idioma =="IT"){
            viewHolder.txtViewCor.setText(c.getCorIt());
        }

        Bitmap thumb = c.getThumb();
        viewHolder.imgViewCor.setImageBitmap(thumb);
        //viewHolder.imgViewIcon.setImageResource(itemsData[position].getImageUrl());


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView txtViewRef;
        public TextView txtViewCor;
        public ImageView imgViewCor;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewRef = (TextView) itemLayoutView.findViewById(R.id.tvRef);
            txtViewCor = (TextView) itemLayoutView.findViewById(R.id.tvCor);
            imgViewCor = (ImageView) itemLayoutView.findViewById(R.id.ivCor);

        }
    }



    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cores.size();
    }
}
