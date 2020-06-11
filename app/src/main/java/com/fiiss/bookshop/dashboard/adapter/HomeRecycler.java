package com.fiiss.bookshop.dashboard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fiiss.bookshop.R;
import com.fiiss.bookshop.dashboard.model.Book;
import com.fiiss.bookshop.dashboard.view.ViewHome;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeRecycler extends RecyclerView.Adapter<ViewHolderHome> {

    private Context mContext;
    private ViewHome viewHome;
    private DecimalFormat format;

    private List<String> mMenuIds = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();

    public HomeRecycler(final Context context, DatabaseReference databaseReference, ViewHome viewHome) {
        this.mContext = context;
        this.viewHome = viewHome;
        format = new DecimalFormat("#,###.##");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                Book book = dataSnapshot.getValue(Book.class);
                mMenuIds.add(dataSnapshot.getKey());
                bookList.add(book);
                notifyItemInserted(bookList.size());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

                Book book = dataSnapshot.getValue(Book.class);
                String commentKey = dataSnapshot.getKey();

                int commentIndex = mMenuIds.indexOf(commentKey);

                if (commentIndex > -1) {
                    bookList.set(commentIndex, book);
                    notifyItemChanged(commentIndex);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String commentKey = dataSnapshot.getKey();
                int commentIndex = mMenuIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Remove data from the list
                    mMenuIds.remove(commentIndex);
                    bookList.remove(commentIndex);
                    notifyItemRemoved(commentIndex);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Book book = dataSnapshot.getValue(Book.class);
                String commentKey = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        };

        databaseReference.addChildEventListener(childEventListener);

    }

    @NonNull
    @Override
    public ViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_book, parent, false);
        return new ViewHolderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHome holder, @SuppressLint("RecyclerView") final int position) {
        final Book book = bookList.get(position);

        holder.txtTitulo.setText(String.format(viewHome.getString(R.string.p_titulo), book.getTitulo()));
        holder.txtIsbn.setText(String.format(viewHome.getString(R.string.p_isbn), book.getIsbn()));
        holder.txtAutor.setText(String.format(viewHome.getString(R.string.p_autor), book.getAutor()));
        holder.txtDescripcion.setText(String.format(viewHome.getString(R.string.p_descripcion), book.getDescripcion()));
        holder.txtPrecio.setText(String.format(viewHome.getString(R.string.p_precio), format.format(book.getPrecio())));
        holder.txtCalificacion.setText(String.format(viewHome.getString(R.string.p_estrella), book.getCalificacion()));

        Glide.with(viewHome).load(book.getUrlImagen()).into(holder.imgLibro);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

}
