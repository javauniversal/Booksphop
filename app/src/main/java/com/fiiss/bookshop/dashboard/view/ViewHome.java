package com.fiiss.bookshop.dashboard.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fiiss.bookshop.R;
import com.fiiss.bookshop.dashboard.adapter.HomeRecycler;
import com.fiiss.bookshop.utilities.Constantes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewHome extends AppCompatActivity {

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}