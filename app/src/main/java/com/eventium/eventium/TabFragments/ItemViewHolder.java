package com.eventium.eventium.TabFragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.eventium.eventium.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView imagenE;
    public TextView titulo;
    public TextView ciudad;
    public TextView fechas;
    public TextView horas;
    public TextView precio;

    public ImageView imagenU;
    public TextView username;

    public ItemViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        imagenE = (ImageView) itemView.findViewById(R.id.imagenEvento);
        titulo = (TextView) itemView.findViewById(R.id.titulo);
        ciudad = (TextView) itemView.findViewById(R.id.ciudad);
        fechas = (TextView) itemView.findViewById(R.id.fechas);
        horas = (TextView) itemView.findViewById(R.id.horas);
        precio = (TextView) itemView.findViewById(R.id.precio);

        imagenU = (ImageView) itemView.findViewById(R.id.imagenUser);
        username = (TextView) itemView.findViewById(R.id.username);
    }

    public void bind(EventModel eventModel) {
        imagenE.setImageBitmap(eventModel.getImagen());
        titulo.setText(eventModel.getTitulo());
        ciudad.setText(eventModel.getCiudad());
        fechas.setText(eventModel.getFechas());
        horas.setText(eventModel.getHoras());
        precio.setText(eventModel.getPrecio());
    }

    public void bind(UserModel userModel) {
        imagenU.setImageBitmap(userModel.getImagen());
        username.setText(userModel.getUsername());
    }

}
