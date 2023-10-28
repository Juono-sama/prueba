package com.example.prueba_parcial_1;

import com.example.componentes.Hilo;
import com.example.modelos.Conexion;
import com.example.vistas.Calculadora;
import com.example.vistas.Loteria;
import com.example.vistas.PistaAtletismo;
import com.example.vistas.Restaurante;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;




//Usaremos MenuBar, Menu y MenuItem


public class HelloApplication extends Application {
    private Scene escena;
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Menu menuParcial1, menuParcial2, menuSalir;
    private MenuItem mitCalculadora,mitLoteria, mitSalir, mitRestaurante, mitPista;

    private void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction((event) -> new Calculadora());

        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction((event)-> new Loteria());

        menuParcial1 = new Menu("Parcial 1");
        menuParcial1.getItems().addAll(mitCalculadora,mitLoteria);

        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction((event)-> new Restaurante());

        mitPista = new MenuItem("Pista Atletismo");
        mitPista.setOnAction((actionEvent -> new PistaAtletismo()));

        menuParcial2 = new Menu("Parcial 2");
        menuParcial2.getItems().addAll(mitRestaurante, mitPista);

        menuSalir = new Menu("Mas opciones");
        mitSalir = new MenuItem("Salir");
        mitSalir.setOnAction((event) -> salir());
        menuSalir.getItems().add(mitSalir);

        menuBar = new MenuBar(menuParcial1,menuParcial2, menuSalir);
    }

    private void salir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensaje del sistema");
        alert.setHeaderText("¿Confirmar cerrar sistema?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {

        /*new Hilo("Yuno").start();
        new Hilo("Rodrigo").start();
        new Hilo("Martina").start();
        new Hilo("Daniela").start();
        new Hilo("Guadalupe").start();
        new Hilo("German").start();*/


        connectToDB();
        CrearUI();
        borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        escena = new Scene(borderPane, 200, 300);
        escena.getStylesheets()
                .add(getClass()
                .getResource("/estilos/estilos.css").toExternalForm());
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
    }

    public void connectToDB(){
        Conexion.createConnection();
        System.out.println("Conexión establecida");
    }


    public static void main(String[] args) {
        launch();
    }
}