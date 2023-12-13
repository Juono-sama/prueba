package com.example.vistas;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Collections;
import java.util.List;

public class Tablilla {
    //Inicio de los atributos
    private GridPane gridTablilla;
    private Carta cartaMostrada;
    private Tablilla tablillaAnterior, tablillaSiguiente;
    public Tablilla(){}
    private final Button[][] argBtnTablilla = new Button[4][4];
    int contadorIgual=0;
    //Fin de atributos

    public void crearTablilla(Carta[] carta, List<Integer> numAleatorio) { //Crea la tablilla con cartas en orden aleatorio
        gridTablilla = new GridPane();
        Collections.shuffle(numAleatorio);

        Carta[] cartaAuxiliar = new Carta[16];
        //Carta cartaAuxiliar; //Aqui le asigna cartas a los botones del gridPane de la tablilla
        int posImagen=1, posCartaALlenar=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //Crea una carta auxiliar para usar después
                cartaAuxiliar[posCartaALlenar] = new Carta();
                String url = new String(carta[numAleatorio.get(posImagen-1)].getUrlImagenCarta());
                cartaAuxiliar[posCartaALlenar].insertarImagen(url);
                cartaAuxiliar[posCartaALlenar].getImvCarta().setFitHeight(140);
                cartaAuxiliar[posCartaALlenar].getImvCarta().setFitWidth(100);

                // Le asigna la carta creada para poner la en el boton
                argBtnTablilla[i][j] = new Button();
                argBtnTablilla[i][j].setPrefSize(90, 130);
                argBtnTablilla[i][j].setGraphic(cartaAuxiliar[posCartaALlenar].getImvCarta());
                Carta finalCartaAuxiliar = cartaAuxiliar[posCartaALlenar];
                int finalJ = j;
                int finalI = i;

                //Le pone la accion de comparar con la carta que se esté mostrando en caso de presionarla
                argBtnTablilla[i][j].setOnAction((event) -> comparar(finalCartaAuxiliar, cartaMostrada, argBtnTablilla[finalI][finalJ]));
                gridTablilla.add(argBtnTablilla[i][j],i,j);
                posImagen++; posCartaALlenar++;
            }
        }
    }

    //Guarda la carta que se esté mostrando, es decir, actualiza la carta con la que los botones deben comparar
    public void actuCarta(Carta cartaMostrada){ //Actualiza la carta mostrada cada que cambie
        this.cartaMostrada = cartaMostrada;
    }

    //Compara la carta del boton con la carta que se muestre
    public void comparar(Carta cartaDelBoton, Carta cartaMostrada, Button boton){
        if(cartaDelBoton.getUrlImagenCarta().equals(cartaMostrada.getUrlImagenCarta())){
                boton.setDisable(true);
                contadorIgual++;
                System.out.println("Llevas "+contadorIgual+" cartas iguales");
        }
    }

    //Habilita los botones para cuando se reinicie el juego
    public void habilitarBotones(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                argBtnTablilla[i][j].setDisable(false);
            }
        }
    }

    //Setters y getters para usar en la lotería (obviamente generados por intelliJ >w<)
    public GridPane getGridTablilla() {
        return gridTablilla;
    }

    public void setGridTablilla(GridPane grdTablilla) {
        this.gridTablilla = grdTablilla;
    }

    public Tablilla getTablillaAnterior() {
        return tablillaAnterior;
    }

    public void setTablillaAnterior(Tablilla tablillaAnterior) {
        this.tablillaAnterior = tablillaAnterior;
    }

    public Tablilla getTablillaSiguiente() {
        return tablillaSiguiente;
    }

    public void setTablillaSiguiente(Tablilla tablillaSiguiente) {
        this.tablillaSiguiente = tablillaSiguiente;
    }

    public int getContadorIgual() {
        return contadorIgual;
    }

    public void setContadorIgual(int contadorIgual) {
        this.contadorIgual = contadorIgual;
    }

}
