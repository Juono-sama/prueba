package com.example.vistas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Carta {
    //Inicio de atributos
    private int numCarta=0;
    private String urlImagenCarta;

    public Carta cartaSiguiente;
    ImageView imvCarta;
    //Final de atributos

    public Carta(){}

    //Inserta una imagen a la carta con el url de la imagen
    public void insertarImagen(String urlImagenCarta){
        this.urlImagenCarta = urlImagenCarta;
        try {
            Image imgCarta;
            imgCarta = new Image(new FileInputStream(urlImagenCarta));
            imvCarta = new ImageView(imgCarta);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Setters y getters para usar en Loteria y Tablilla (Generados por IntelliJ ^w^)
    public ImageView getImvCarta() {
        return imvCarta;
    }

    public void setImvCarta(ImageView imvCarta) {
        this.imvCarta = imvCarta;
    }
    public int getNumCarta() {
        return numCarta;
    }

    public void setNumCarta(int numCarta) {
        this.numCarta = numCarta;
    }

    public String getUrlImagenCarta() {
        return urlImagenCarta;
    }

    public void setUrlImagenCarta(String urlImagenCarta) {
        this.urlImagenCarta = urlImagenCarta;
    }

    public Carta getCartaSiguiente() {
        return cartaSiguiente;
    }

    public void setCartaSiguiente(Carta cartaSiguiente) {
        this.cartaSiguiente = cartaSiguiente;
    }
}
