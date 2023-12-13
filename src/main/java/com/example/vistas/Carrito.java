package com.example.vistas;


import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    public List<Producto> listaProductos;
    public Carrito(){
        listaProductos = new ArrayList<>();
    }

    public void insertarProductoAlCarrito(int idProducto, String nombre, float precio, ImageView imagen, int idCategoria){
        Producto productoAInsertar = new Producto();
        productoAInsertar.setIdProducto(idProducto);
        productoAInsertar.setNombre(nombre);
        productoAInsertar.setPrecio(precio);
        productoAInsertar.setImagen(imagen);
        productoAInsertar.setIdCategoria(idCategoria);

        listaProductos.add(productoAInsertar);
    }


    public void setProducto(Producto producto){
        listaProductos.add(producto);
    }
    public Producto getProductoConIndice(int indice){
        return listaProductos.get(indice);
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }




}
