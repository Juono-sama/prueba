package com.example.vistas;

import com.example.componentes.Hilo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PistaAtletismo extends Stage {
    private ProgressBar[] pgbCoorredores = new ProgressBar[6];
    private Hilo[] thrCorredores = new Hilo[6];
    private VBox vBox;
    private Button btnIniciar;
    private Scene escena;
    private String[] strCorredores = {"Martina", "German", "Yuno", "Rodrigio", "Rubensin", "Vanessa"};
    public PistaAtletismo(){
        CrearUI();
        escena = new Scene(vBox);
        this.setTitle("Pista de atletismo");
        this.setScene(escena);
    }

    private void CrearUI() {
        vBox = new VBox();
        for (int i = 0; i <pgbCoorredores.length; i++) {
            pgbCoorredores[i] = new ProgressBar(0);
            thrCorredores[i] = new Hilo(strCorredores[i], pgbCoorredores[i]);
            vBox.getChildren().add(pgbCoorredores[i]);
        }
        btnIniciar = new Button("Iniciar Carrera ^w^");
        btnIniciar.setOnAction(event -> {
            for (int i = 0; i < pgbCoorredores.length; i++) {
                thrCorredores[i].start();
            }
        });
        vBox.getChildren().add(btnIniciar);
        this.show();
    }

}
