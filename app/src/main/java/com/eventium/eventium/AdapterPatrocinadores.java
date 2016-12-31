package com.eventium.eventium;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterPatrocinadores extends BaseAdapter
{
    Context context;
    ArrayList<String> patrocinadoresList;
    ArrayList<Bitmap> imageList;

    private static LayoutInflater inflater = null;

    public AdapterPatrocinadores(Context c, ArrayList<String> pl, ArrayList<Bitmap> il) {
        context = c;
        patrocinadoresList = pl;
        imageList = il;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return patrocinadoresList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder
    {
        TextView tv;
        ImageView iv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_patrocinadores_row, null);
        holder.tv = (TextView) rowView.findViewById(R.id.usernameSponsor);
        holder.iv = (ImageView) rowView.findViewById(R.id.imageSponsor);
        holder.tv.setText(patrocinadoresList.get(position));
        holder.iv.setImageBitmap(imageList.get(position));
        return rowView;
    }

}