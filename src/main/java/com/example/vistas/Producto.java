package com.example.vistas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Producto {
    int idProducto;
    String nombre;
    float precio;
    //String urlFinalImagen;
    ImageView imagen;
    int idCategoria;

    public Producto(){}
    public Producto(int idProducto, String nombre, float precio, ImageView imagen, int idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        //this.urlFinalImagen = urlFinalImagen;
        this.imagen = imagen;
        this.idCategoria = idCategoria;
    }

    private void generarImageView() throws FileNotFoundException {
        imagen = new ImageView(new Image(new FileInputStream("src/main/java/com/example/img/"+nombre+".jpg")));
    }
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /*public String getUrlFinalImagen() {
        return urlFinalImagen;
    }*/

    /*public void setUrlFinalImagen(String urlFinalImagen){
        this.urlFinalImagen = urlFinalImagen;
        try {
            generarImageView();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
