package com.fiiss.bookshop.dashboard.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import dmax.dialog.SpotsDialog;


public class ViewHome extends AppCompatActivity {

    private int RESULTGALERY = 345;
    private DialogBook dialogDatosCodigoBlobal;
    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    private Uri selectedImageURI = null;
    private SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
    protected AlertDialog dialogCuston;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Constantes.JSON_BOOKS);
        storageReference = FirebaseStorage.getInstance().getReference().child(Constantes.JSON_FOTOS);

        RecyclerView recycleLibro = findViewById(R.id.recycleLibro);

        // Retrieve data from firebase server
        HomeRecycler homeRecycler = new HomeRecycler(this, mDatabase, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recycleLibro.setLayoutManager(mLayoutManager);
        recycleLibro.setItemAnimator(new DefaultItemAnimator());
        recycleLibro.setAdapter(homeRecycler);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            dialogDatosCodigoBlobal = new DialogBook(this, getString(R.string.app_name));

            dialogDatosCodigoBlobal.setCancelable(false);
            dialogDatosCodigoBlobal.show();
            dialogDatosCodigoBlobal.getImgLibro().setBackgroundResource(R.drawable.img_add);
            dialogDatosCodigoBlobal.getImgLibro().setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_imagen)), RESULTGALERY);
            });
            dialogDatosCodigoBlobal.getBtnBuardar().setOnClickListener(v -> validateElemnt(dialogDatosCodigoBlobal));
            dialogDatosCodigoBlobal.getBtnCancelar().setOnClickListener(v -> dialogDatosCodigoBlobal.dismiss());
        });

        dialogCuston = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.customDialog)
                .setMessage(R.string.app_name)
                .build();
    }

    public void routerActivity(Book book) {
        Intent intent = new Intent(this, DetailsView.class);
        intent.putExtra("data", book);
        startActivity(intent);
    }

    public void deleteBook(Book book) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_dialog));
        builder.setMessage(getString(R.string.content_message));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Query applesQuery = mDatabase.orderByChild("isbn").equalTo(book.getIsbn());
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Log", "onCancelled", databaseError.toException());
                    }
                });
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode ==  RESULTGALERY) {
            selectedImageURI = data.getData();
            dialogDatosCodigoBlobal.getImgLibro().setImageURI(selectedImageURI);
        }
    }


    private void validateElemnt(DialogBook dialogDatosCodigo) {

        if (selectedImageURI == null) {
            Toast.makeText(this, getString(R.string.file_imagen_require), Toast.LENGTH_LONG).show();
        } else if (validateText(dialogDatosCodigo.getEditAddTitulo())) {
            dialogDatosCodigo.getEditAddTitulo().setFocusable(true);
            dialogDatosCodigo.getEditAddTitulo().setFocusableInTouchMode(true);
            dialogDatosCodigo.getEditAddTitulo().requestFocus();
            dialogDatosCodigo.getEditAddTitulo().setError(getString(R.string.file_require));
        } else if (validateText(dialogDatosCodigo.getEditAddAutor())) {
            dialogDatosCodigo.getEditAddAutor().setFocusable(true);
            dialogDatosCodigo.getEditAddAutor().setFocusableInTouchMode(true);
            dialogDatosCodigo.getEditAddAutor().requestFocus();
            dialogDatosCodigo.getEditAddAutor().setError(getString(R.string.file_require));
        } else if (validateText(dialogDatosCodigo.getEditAddCalif())) {
            dialogDatosCodigo.getEditAddCalif().setFocusable(true);
            dialogDatosCodigo.getEditAddCalif().setFocusableInTouchMode(true);
            dialogDatosCodigo.getEditAddCalif().requestFocus();
            dialogDatosCodigo.getEditAddCalif().setError(getString(R.string.file_require));
        } else if (validateText(dialogDatosCodigo.getEditAddISBN())) {
            dialogDatosCodigo.getEditAddISBN().setFocusable(true);
            dialogDatosCodigo.getEditAddISBN().setFocusableInTouchMode(true);
            dialogDatosCodigo.getEditAddISBN().requestFocus();
            dialogDatosCodigo.getEditAddISBN().setError(getString(R.string.file_require));
        } else {
            validateISBN(dialogDatosCodigo);
        }

    }

    private void validateISBN(DialogBook dialogDatosCodigo) {
        mDatabase.orderByChild("isbn").equalTo(dialogDatosCodigo.getEditAddISBN().getText().toString().trim()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Book book = dataSnapshot.getValue(Book.class);
                        if (book == null) {
                            if (validateText(dialogDatosCodigo.getEditAddDescrip())) {
                                dialogDatosCodigo.getEditAddDescrip().setFocusable(true);
                                dialogDatosCodigo.getEditAddDescrip().setFocusableInTouchMode(true);
                                dialogDatosCodigo.getEditAddDescrip().requestFocus();
                                dialogDatosCodigo.getEditAddDescrip().setError(getString(R.string.file_require));
                            } else if (validateText(dialogDatosCodigo.getEditAddPreci())) {
                                dialogDatosCodigo.getEditAddPreci().setFocusable(true);
                                dialogDatosCodigo.getEditAddPreci().setFocusableInTouchMode(true);
                                dialogDatosCodigo.getEditAddPreci().requestFocus();
                                dialogDatosCodigo.getEditAddPreci().setError(getString(R.string.file_require));
                            } else {
                                // Continue
                                dialogCuston.show();
                                Date fecha = new Date();
                                String dateActuality = df_input.format(fecha);
                                StorageReference riversRef = storageReference.child(dateActuality+".jpg");
                                riversRef.putFile(selectedImageURI)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            // Get a URL to the uploaded content
                                            Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                                            task.addOnSuccessListener(uri -> {
                                                String photoLink = uri.toString();
                                                Book book1 = new Book();
                                                book1.setTitulo(dialogDatosCodigo.getEditAddTitulo().getText().toString());
                                                book1.setAutor(dialogDatosCodigo.getEditAddAutor().getText().toString());
                                                book1.setCalificacion(dialogDatosCodigo.getEditAddCalif().getText().toString());
                                                book1.setIsbn(dialogDatosCodigo.getEditAddISBN().getText().toString());
                                                book1.setDescripcion(dialogDatosCodigo.getEditAddDescrip().getText().toString());
                                                book1.setPrecio(Double.parseDouble(dialogDatosCodigo.getEditAddPreci().getText().toString()));
                                                book1.setUrlImagen(photoLink);
                                                pushInfoFirebase(book1);
                                            });
                                        })
                                        .addOnFailureListener(exception -> {
                                            dialogCuston.dismiss();
                                            Toast.makeText(ViewHome.this, getString(R.string.file_error_imagen), Toast.LENGTH_LONG).show();
                                        });
                            }
                        } else {
                            dialogDatosCodigo.getEditAddISBN().setFocusable(true);
                            dialogDatosCodigo.getEditAddISBN().setFocusableInTouchMode(true);
                            dialogDatosCodigo.getEditAddISBN().requestFocus();
                            dialogDatosCodigo.getEditAddISBN().setError(getString(R.string.duplicate));
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        dialogCuston.dismiss();
                    }
                }
        );
    }

    public boolean validateText(EditText editText) {
        return Objects.requireNonNull(editText.getText()).toString().trim().isEmpty();
    }

    private void pushInfoFirebase(Book book) {
        mDatabase.push().setValue(book, (error, ref) -> {
            selectedImageURI = null;
            Toast.makeText(ViewHome.this, getString(R.string.file_add_ok), Toast.LENGTH_LONG).show();
            dialogDatosCodigoBlobal.dismiss();
            dialogCuston.dismiss();
        });
    }

}