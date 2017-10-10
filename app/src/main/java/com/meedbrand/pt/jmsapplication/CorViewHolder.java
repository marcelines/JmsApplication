package com.meedbrand.pt.jmsapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Márcio Pinto on 15/10/2015.
 */
public class CorViewHolder extends RecyclerView.ViewHolder {
    protected ImageView imageView;
    protected TextView textView;
    protected TextView textView2;

    public CorViewHolder(View view) {
        super(view);
        this.imageView = (ImageView) view.findViewById(R.id.ivCor);
        this.textView = (TextView) view.findViewById(R.id.tvRef);
        this.textView2 = (TextView) view.findViewById(R.id.tvCor);
    }


}