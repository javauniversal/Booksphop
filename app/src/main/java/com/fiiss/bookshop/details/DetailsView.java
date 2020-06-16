package com.fiiss.bookshop.details;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fiiss.bookshop.R;
import com.fiiss.bookshop.dashboard.model.Book;

import java.text.DecimalFormat;

public class DetailsView extends AppCompatActivity {

    public ImageView imgLibro;
    public TextView txtTitulo;
    public TextView txtCalificacion;
    public TextView txtIsbn;
    public TextView txtAutor;
    public TextView txtDescripcion;
    public TextView txtPrecio;
    private DecimalFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        imgLibro = findViewById(R.id.imgLibro);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtCalificacion = findViewById(R.id.txtCalificacion);
        txtIsbn = findViewById(R.id.txtIsbn);
        txtAutor = findViewById(R.id.txtAutor);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtPrecio = findViewById(R.id.txtPrecio);

        format = new DecimalFormat("#,###.##");

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Book book = (Book) bundle.getSerializable("data");

        assert book != null;
        setDateBook(book);
    }

    private void setDateBook(Book book) {

        Glide.with(this).load(book.getUrlImagen()).into(imgLibro);

        txtTitulo.setText(String.format(getString(R.string.p_titulo), book.getTitulo()));
        txtIsbn.setText(String.format(getString(R.string.p_isbn), book.getIsbn()));
        txtAutor.setText(String.format(getString(R.string.p_autor), book.getAutor()));
        txtDescripcion.setText(String.format(getString(R.string.p_descripcion), book.getDescripcion()));
        txtPrecio.setText(String.format(getString(R.string.p_precio), format.format(book.getPrecio())));
        txtCalificacion.setText(String.format(getString(R.string.p_estrella), book.getCalificacion()));

    }

}