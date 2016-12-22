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

public class AdapterComments extends BaseAdapter
{
    Context context;
    ArrayList<String> usernameList;
    ArrayList<String> commentList;
    ArrayList<Bitmap> imageList;

    private static LayoutInflater inflater = null;

    public AdapterComments(Context c, ArrayList<String> ul, ArrayList<String> cl, ArrayList<Bitmap> il) {
        context = c;
        usernameList = ul;
        commentList = cl;
        imageList = il;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return usernameList.size();
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
        TextView tv, tv2;
        ImageView iv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_comments_row, null);
        holder.tv = (TextView) rowView.findViewById(R.id.lvComments_userName);
        holder.tv2 = (TextView) rowView.findViewById(R.id.lvComments_Comment);
        holder.iv = (ImageView) rowView.findViewById(R.id.lvComments_userImage);
        holder.tv.setText(usernameList.get(position));
        holder.tv2.setText(commentList.get(position));
        holder.iv.setImageBitmap(imageList.get(position));
        return rowView;
    }

}
