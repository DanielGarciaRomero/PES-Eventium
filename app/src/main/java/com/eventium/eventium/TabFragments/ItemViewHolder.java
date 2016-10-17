package com.eventium.eventium.TabFragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.eventium.eventium.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView imagen;
    public TextView titulo;
    public TextView ciudad;
    public TextView fechas;
    public TextView horas;
    public TextView precio;

    public ItemViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        imagen = (ImageView) itemView.findViewById(R.id.imagenEvento);
        titulo = (TextView) itemView.findViewById(R.id.titulo);
        ciudad = (TextView) itemView.findViewById(R.id.ciudad);
        fechas = (TextView) itemView.findViewById(R.id.fechas);
        horas = (TextView) itemView.findViewById(R.id.horas);
        precio = (TextView) itemView.findViewById(R.id.precio);
    }

    public void bind(EventModel eventModel) {
        imagen.setImageURI(eventModel.getImagen());
        titulo.setText(eventModel.getTitulo());
        ciudad.setText(eventModel.getCiudad());
        fechas.setText(eventModel.getFechas());
        horas.setText(eventModel.getHoras());
        precio.setText(eventModel.getPrecio());
    }

}
