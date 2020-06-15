package com.fiiss.bookshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiiss.bookshop.R;


public class DialogBook extends Dialog {

    private String title;

    private ImageView imgLibro;
    private EditText editAddTitulo;
    private EditText editAddAutor;
    private EditText editAddCalif;
    private EditText editAddISBN;
    private EditText editAddDescrip;
    private EditText editAddPreci;

    private Button btnCancelar;
    private Button btnBuardar;

    public DialogBook(Context context, String title) {
        super(context, R.style.DialogTheme);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_book);

        TextView txttitle = findViewById(R.id.title);
        txttitle.setText(title);

        imgLibro = findViewById(R.id.imgLibro);
        editAddTitulo = findViewById(R.id.editAddTitulo);
        editAddAutor = findViewById(R.id.editAddAutor);
        editAddCalif = findViewById(R.id.editAddCalif);
        editAddISBN = findViewById(R.id.editAddISBN);
        editAddDescrip = findViewById(R.id.editAddDescrip);
        editAddPreci = findViewById(R.id.editAddPreci);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnBuardar = findViewById(R.id.btnBuardar);

    }

    public ImageView getImgLibro() {
        return imgLibro;
    }

    public void setImgLibro(ImageView imgLibro) {
        this.imgLibro = imgLibro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EditText getEditAddTitulo() {
        return editAddTitulo;
    }

    public void setEditAddTitulo(EditText editAddTitulo) {
        this.editAddTitulo = editAddTitulo;
    }

    public EditText getEditAddAutor() {
        return editAddAutor;
    }

    public void setEditAddAutor(EditText editAddAutor) {
        this.editAddAutor = editAddAutor;
    }

    public EditText getEditAddCalif() {
        return editAddCalif;
    }

    public void setEditAddCalif(EditText editAddCalif) {
        this.editAddCalif = editAddCalif;
    }

    public EditText getEditAddISBN() {
        return editAddISBN;
    }

    public void setEditAddISBN(EditText editAddISBN) {
        this.editAddISBN = editAddISBN;
    }

    public EditText getEditAddDescrip() {
        return editAddDescrip;
    }

    public void setEditAddDescrip(EditText editAddDescrip) {
        this.editAddDescrip = editAddDescrip;
    }

    public EditText getEditAddPreci() {
        return editAddPreci;
    }

    public void setEditAddPreci(EditText editAddPreci) {
        this.editAddPreci = editAddPreci;
    }

    public Button getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(Button btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public Button getBtnBuardar() {
        return btnBuardar;
    }

    public void setBtnBuardar(Button btnBuardar) {
        this.btnBuardar = btnBuardar;
    }
}
