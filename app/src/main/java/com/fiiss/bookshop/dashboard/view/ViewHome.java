package com.fiiss.bookshop.dashboard.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fiiss.bookshop.R;
import com.fiiss.bookshop.dashboard.adapter.HomeRecycler;
import com.fiiss.bookshop.dashboard.model.Book;
import com.fiiss.bookshop.details.DetailsView;
import com.fiiss.bookshop.dialog.DialogBook;
import com.fiiss.bookshop.utilities.Constantes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ViewHome extends AppCompatActivity {

    private int RESULTGALERY = 345;
    private DialogBook dialogDatosCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(Constantes.JSON_BOOKS);
        RecyclerView recycleLibro = findViewById(R.id.recycleLibro);

        // Retrieve data from firebase server
        HomeRecycler homeRecycler = new HomeRecycler(this, mDatabase, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recycleLibro.setLayoutManager(mLayoutManager);
        recycleLibro.setItemAnimator(new DefaultItemAnimator());
        recycleLibro.setAdapter(homeRecycler);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            dialogDatosCodigo = new DialogBook(this, "Book");

            dialogDatosCodigo.setCancelable(false);
            dialogDatosCodigo.show();
            dialogDatosCodigo.getImgLibro().setBackgroundResource(R.drawable.img_add);
            dialogDatosCodigo.getImgLibro().setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), RESULTGALERY);
            });

        });
    }

    public void routerActivity(Book book) {
        Intent intent = new Intent(this, DetailsView.class);
        intent.putExtra("data", book);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode ==  RESULTGALERY) {
            Uri selectedImage = data.getData();
            dialogDatosCodigo.getImgLibro().setImageURI(selectedImage);
        }
    }

}