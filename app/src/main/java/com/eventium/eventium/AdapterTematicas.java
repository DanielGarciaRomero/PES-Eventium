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

public class AdapterTematicas extends BaseAdapter
{
    Context context;
    ArrayList<String> categoriaList;
    ArrayList<Bitmap> imageList;

    private static LayoutInflater inflater = null;

    public AdapterTematicas(Context c, ArrayList<String> cl, ArrayList<Bitmap> il) {
        context = c;
        categoriaList = cl;
        imageList = il;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoriaList.size();
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
        rowView = inflater.inflate(R.layout.list_tematicas_row, null);
        holder.tv = (TextView) rowView.findViewById(R.id.lvTematicas_categoriaName);
        holder.iv = (ImageView) rowView.findViewById(R.id.lvTematicas_categoriaImage);
        holder.tv.setText(categoriaList.get(position));
        holder.iv.setImageBitmap(imageList.get(position));
        return rowView;
    }

}
