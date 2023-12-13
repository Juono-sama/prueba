package com.example.vistas;

public class Categoria{
    private String categoria;
    private String producto;
    public Categoria() {
    }
    public Categoria(String categoria, String producto){
        this.categoria = categoria;
        this.producto = producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

}
