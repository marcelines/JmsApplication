package com.meedbrand.pt.jmsapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by João daSilva on 21/10/2015.
 */
public class ListNoteAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Note> notes;


    public ListNoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {

        return notes.size();
    }

    @Override
    public Object getItem(int position) {

        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;



        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.list_notes_, parent, false);


        } else {

            v = convertView;

        }



        TextView tvNome = (TextView)v.findViewById(R.id.tvNome);
        TextView tvEmpresa = (TextView)v.findViewById(R.id.tvEmpresa);
        TextView tvContacto = (TextView)v.findViewById(R.id.tvContacto);
        RatingBar rbRating = (RatingBar)v.findViewById(R.id.ratingBar5);

        Note n = notes.get(position);

        tvNome.setText(n.getNome());
        tvEmpresa.setText(n.getEmpresa());
        tvContacto.setText(n.getContacto());
        rbRating.setRating(n.getRating());


        return v;



    }

}