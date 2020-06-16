package com.fiiss.bookshop.dashboard.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fiiss.bookshop.R;

public class ViewHolderHome extends RecyclerView.ViewHolder {

    public ImageView imgLibro;
    public ImageView delete;
    public TextView txtTitulo;
    public TextView txtCalificacion;
    public TextView txtIsbn;
    public TextView txtAutor;
    public TextView txtDescripcion;
    public TextView txtPrecio;
    public LinearLayout contentCardView;

    public ViewHolderHome(View itemView) {
        super(itemView);
        imgLibro = itemView.findViewById(R.id.imgLibro);
        txtTitulo = itemView.findViewById(R.id.txtTitulo);
        txtCalificacion = itemView.findViewById(R.id.txtCalificacion);
        txtIsbn = itemView.findViewById(R.id.txtIsbn);
        txtAutor = itemView.findViewById(R.id.txtAutor);
        txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
        txtPrecio = itemView.findViewById(R.id.txtPrecio);
        delete = itemView.findViewById(R.id.delete);

        contentCardView = itemView.findViewById(R.id.contentCardView);
    }

}
