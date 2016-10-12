package com.eventium.eventium;

/**
 * Created by daniel on 12/10/16.
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterImagenesListView extends BaseAdapter
{
    Context context;
    ArrayList<String> eventList;
    ArrayList<Uri> imageList;

    private static LayoutInflater inflater = null;

    public AdapterImagenesListView(Context c, ArrayList<String> el, ArrayList<Uri> il) {
        eventList = el;
        context = c;
        imageList = il;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return eventList.size();
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
        rowView = inflater.inflate(R.layout.imagelist, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textViewOnList);
        holder.iv = (ImageView) rowView.findViewById(R.id.imageViewOnList);
        holder.tv.setText(eventList.get(position));
        holder.iv.setImageURI(imageList.get(position));
        return rowView;
    }
}
