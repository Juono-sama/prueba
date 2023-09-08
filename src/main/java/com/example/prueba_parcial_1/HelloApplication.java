package com.example.prueba_parcial_1;

import com.example.vistas.Calculadora;
import com.example.vistas.Loteria;
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
    private MenuItem mitCalculadora,mitLoteria, mitSalir;
    private void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction((event) -> new Calculadora());

        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction((event)-> new Loteria());

        menuParcial1 = new Menu("Parcial 1");
        menuParcial1.getItems().addAll(mitCalculadora,mitLoteria);

        menuParcial2 = new Menu("Parcial 2");

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
        CrearUI();
        borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        escena = new Scene(borderPane, 200, 300);
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}