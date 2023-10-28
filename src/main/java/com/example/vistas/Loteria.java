package com.example.vistas;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Loteria extends Stage {
    //Inicio de los atributos
    private Scene escena;
    private HBox hPrincipal,hBtnSelec, hHud, hMazo;
    private VBox vTablilla, vMazo;
    private ImageView imvCarta, imvDorso; //Si no uso imvDorso, me confundo con las que sí son cartas XDXDXD
    private Button btnAnterior, btnSiguiente, btnInicio;
    private Label lblCronometro, lblImagenCarta, lblTablilla;
    private Timer tiempo;
    private TimerTask tiempoTarea;
    private Carta[] cartas;
    private Carta cartaActual, cartaMostrada;
    private Tablilla[] tablillas;
    private Tablilla tablillaActual;
    int contadorIgual=0;
    String cronometro;
    int m=0,s=0, sTotal=0;
    //Fin de los atributos de la loteria

    public Loteria (){
        CrearUI();
        escena = new Scene(hPrincipal,1024, 720);
        this.setTitle("Loteria");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        crearMazo();
        barajearCartasDelMazo();
        crearTablillas();
        crearHudDerecho();
        crearHudIzquierdo();

        //Inserta el GridPane de la tablilla en un label para poder cambiar la tablilla con los botones
        lblTablilla = new Label();
        lblTablilla.setGraphic(tablillaActual.getGridTablilla());
        vTablilla =new VBox(lblTablilla, hBtnSelec); //Se añade a una vbox para ponerlo en pantalla
        vTablilla.setSpacing(20);

        // El HBox principal donde se pone todo
        hPrincipal = new HBox(vTablilla, vMazo);
        hPrincipal.setPadding(new Insets(20));
    }

    public void crearMazo(){ //Crea las cartas y les pone el numero que las identifica y su url
        int numCarta=0;
        cartas = new Carta[54];
        while(numCarta < 54){
            cartas[numCarta] = new Carta();
            cartas[numCarta].setNumCarta(numCarta);
            if(numCarta == 0){ //Si la carta es 0, crea el dorso
                cartas[numCarta].setUrlImagenCarta("src/main/java/com/example/img/0 LM (dorso).jpg");
                try {
                    Image imgDorso;
                    imgDorso = new Image(new FileInputStream(cartas[0].getUrlImagenCarta()));
                    imvCarta = new ImageView(imgDorso);
                    imvDorso = imvCarta;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                cartas[0].setImvCarta(imvCarta);
                cartas[0].getImvCarta().setFitHeight(600);
                cartas[0].getImvCarta().setFitWidth(500);
            }
            else { //Si la carta no es 0, crea las cartas que no son el dorso
                cartas[numCarta].setUrlImagenCarta("src/main/java/com/example/img/"+numCarta+" LM.jpg");
                try {
                    Image imgCarta;
                    imgCarta = new Image(new FileInputStream(cartas[numCarta].getUrlImagenCarta()));
                    imvCarta = new ImageView(imgCarta);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                cartas[numCarta].setImvCarta(imvCarta);
                cartas[numCarta].getImvCarta().setFitHeight(600);
                cartas[numCarta].getImvCarta().setFitWidth(500);
            }
            numCarta++;
        }
    }

    void barajearCartasDelMazo(){ //Crea un arreglo de numeros, los cambia de lugar aletoriamente y se usan para desordenar las cartas para usarla en el mazo
        List<Integer> numAleatorio = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53);
        Collections.shuffle(numAleatorio);                                                            //p52

        //Les indica a las cartas cuál sigue después de ellas en el mazo
        int i=0;
        Carta cartaActual;
        cartaActual = cartas[0];
        while(i<53){
            cartaActual.setCartaSiguiente(cartas[numAleatorio.get(i)]);
            cartaActual = cartaActual.getCartaSiguiente();
            i++;
        }
        cartaActual.setCartaSiguiente(cartas[0]);
        //La carta siguiente de la última carta va a apuntar al dorso de las carta, indicando que ya no hay más cartas
    }

    private void crearTablillas(){ //Crea las tablilas poniendoles cartas y las tablillas que les siguen y precenden
        tablillas = new Tablilla[4];
        int i=0;

        while(i<4){
            tablillas[i] = new Tablilla();
            tablillas[i].crearTablilla(cartas); //Le dá a las tablillas las cartas para que se las pongan (la tablilla agarra cartas aleatorias dentro de su método)
            i++;
        }
        i=0;

        while(i<4){ //Les dice a las tablillas cual va antes y despues de ellas
            if(i == 0){ //Esto servirá para los botones
                tablillas[0].setTablillaAnterior(tablillas[3]);
                tablillas[0].setTablillaSiguiente(tablillas[1]);
            }
            else if (i == 3) {
                tablillas[3].setTablillaSiguiente(tablillas[0]);
                tablillas[3].setTablillaAnterior(tablillas[2]);
            }
            else{
                tablillas[i].setTablillaSiguiente(tablillas[i+1]);
                tablillas[i].setTablillaAnterior(tablillas[i-1]);
            }
            i++;
        }
        tablillaActual = tablillas[0]; //Asigna la primer tablilla que sale como la primera que se creó
    }

    public void crearHudDerecho(){ //Crea la parte derecha de la pantalla
        //Crea un label en el que irá el mazo. Se usa un label ya que es fácil cambiar lo que se muestra dentro de él
        lblImagenCarta = new Label(); lblImagenCarta.setPrefHeight(600); lblImagenCarta.setPrefWidth(500);
        cartaActual = cartas[0];
        lblImagenCarta.setGraphic(cartaActual.getImvCarta());

        //Pone el label en un HBox
        hMazo = new HBox();
        hMazo.getChildren().addAll(lblImagenCarta);

        //Crea el boton de inicio y le da su funcionalidad
        btnInicio = new Button("Iniciar"); btnInicio.setFont(new Font("Serif", 20));
        btnInicio.setPrefSize(250, 130);
        btnInicio.setOnAction((event) -> crononemtroFuncionando());

        //Crea un label con el cronómetro y le añade cosas para que se vea bonito >w<
        lblCronometro = new Label(); lblCronometro.setText("00:00"); lblCronometro.setPrefSize(200, 120);
        lblCronometro.setAlignment(Pos.CENTER); lblCronometro.setFont(new Font("Serif",24));

        //Pone todo lo creado en un HBox para que se vea, porque sino no sirve uwu
        hHud = new HBox();
        hHud.getChildren().addAll(btnInicio, lblCronometro);
        vMazo = new VBox(hMazo,hHud);
    }

    public void crearHudIzquierdo(){ //Crea todo lo que va en la parte de la izquierda (Menos la tablilla, ese es un tema completamente aparte)
        //Crea el boton anterior para cambiar a la tablilla anterior antes de jugar
        btnAnterior = new Button("<"); btnAnterior.setFont(new Font("Serif", 20));
        btnAnterior.setPrefSize(200,100);
        btnAnterior.setOnAction((event) -> cambiarATablillaAnterior());

        btnSiguiente = new Button(">"); btnSiguiente.setFont(new Font("Serif", 20));
        btnSiguiente.setPrefSize(200,100);
        btnSiguiente.setOnAction((event) -> cambiarATablillaSiguiente());

        //Agrega los botones a un HBox para que se muestren, sino no se muestran
        hBtnSelec = new HBox(btnAnterior,btnSiguiente);
    }

    void cambiarATablillaAnterior(){ //Le dice a las tablillas cuales son la siguiente
        tablillaActual = tablillaActual.getTablillaAnterior();
        lblTablilla.setGraphic(tablillaActual.getGridTablilla());
    }
    void cambiarATablillaSiguiente(){ //Le dice a las tablillas cuales son la anterior
        tablillaActual = tablillaActual.getTablillaSiguiente();
        lblTablilla.setGraphic(tablillaActual.getGridTablilla());
    }

    private void actualizarCarta(){ //actualiza lblImagenCarta bien fácil e intuitivo como me gusta UwU
        //Crea una carta, le pone los parámetros de las cartas del mazo aleatorio y la muestra en la parte de la izquierda
        cartaMostrada = new Carta();
        cartaMostrada.insertarImagen(cartaActual.getCartaSiguiente().getUrlImagenCarta());
        cartaMostrada.getImvCarta().setFitHeight(600);
        cartaMostrada.getImvCarta().setFitWidth(500);
        lblImagenCarta.setGraphic(cartaMostrada.getImvCarta());

        //Le dice a la tablilla qué carta se está mostrando para compararla con sus botones en caso de que sus botones sean presionados
        tablillaActual.actuCarta(cartaMostrada);

        cartaActual = cartaActual.getCartaSiguiente(); //Prepara para la siguiente carta

        contadorIgual = tablillaActual.getContadorIgual(); //Actualiza las cartas que el jugador seleccionó bien en la tablilla
        if(cartaActual == cartas[0]){ //Si se acaban las cartas y no terminó la tablilla, pierde JKAJSJKALJDASKLDJSADJS
            reiniciarTodo();
            JOptionPane.showMessageDialog(null,"Ya pasaron todas las cartas, \nNi modo, perdiste XD", "The Looser XDXD", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void crononemtroFuncionando(){ //El metodo principal, que se ejecuta cuando el botón "Iniciar" se presiona YeeeeeY
        //Antes que nada, cambia el botono "Iniciar" por "Reiniciar" y le aplica lo que su nombre dice (Y deshabilita la tablilla para no hacer trampa >w<)
        btnInicio.setText("Reiniciar");
        btnInicio.setOnAction((event) -> reiniciarTodo());
        btnAnterior.setDisable(true);
        btnAnterior.setDisable(true);
        actualizarCarta(); //Esto cambia la carta en el segundo 0, sino, no da la impresion de estar jugando XD
        if(s>0){ //Si vuelve a presionar el boton iniciar reinicia el tiempo:
            tiempoTarea.cancel();
            tiempo=null;
            tiempoTarea=null;
            s=0; m=0;
            escribirTiempo();
        }
        //Al presionar el boton iniciar, inicia el cronometro y el cambio de las cartas (Para eso es el label)
        tiempo = new Timer();
        tiempoTarea = new TimerTask() { //El TimerTask por asi decirlo, crea el hilo principal del programa (o eso parece)
                @Override
                public void run() {
                    actualizarTiempoYCarta();
                }
            };
        tiempo.schedule(tiempoTarea, 1000, 1000); //Cada cuanto se ejecuta el programa

    }
    private void actualizarTiempoYCarta(){ //Crea lo de adentro del hilo principal para actualizar el tiempo y las cartas del mazo
        s++; sTotal++;
        if(s==60) {
            m++;
            s=0;
        }
        Platform.runLater(() -> escribirTiempo());

        if(s%5 == 0){
                Platform.runLater(() -> actualizarCarta());
        }
    }
    private void escribirTiempo(){ //Actualiza el tiempo (Para eso puse un label, es facil actualizarlo uwu)
        cronometro = (m<=9?"0":"") +m+ ":"+ (s<=9?"0":"") +s;
        lblCronometro.setText(cronometro);

        if(contadorIgual == 16){ //Comprueba cada segundo si ganaste, sino ps no hace nada
            contadorIgual = 0; reiniciarTodo();
            JOptionPane.showMessageDialog(null,"Le diste a 16, \nGanaste ^w^", "The Winner uwu", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void reiniciarTodo(){ //Pone por los valores predeterminados para cuando le de en reiniciar
        s=0; m=0;
        escribirTiempo();

        tiempoTarea.cancel();
        tiempo.cancel();

        cartaMostrada = cartas[0];
        lblImagenCarta.setGraphic(cartaMostrada.getImvCarta());
        barajearCartasDelMazo();

        btnInicio.setText("Iniciar");
        btnInicio.setOnAction((event) -> crononemtroFuncionando());

        tablillaActual.habilitarBotones();
        contadorIgual=0;

        btnAnterior.setDisable(false);
        btnAnterior.setDisable(false);
    }
}