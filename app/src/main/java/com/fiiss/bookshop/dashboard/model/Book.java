package com.fiiss.bookshop.dashboard.model;

public class Book {

    private String uid;
    private String titulo;
    private String isbn;
    private String autor;
    private String descripcion;
    private Double precio;
    private String calificacion;
    private String urlImagen;

    public Book() { }

    public Book(String uid, String titulo, String isbn, String autor, String descripcion, Double precio, String calificacion, String urlImagen) {
        this.uid = uid;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calificacion = calificacion;
        this.urlImagen = urlImagen;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
