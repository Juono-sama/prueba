package com.example.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Loteria extends Stage {
    private Scene escena;
    private HBox hPrincipal,hBtnSelec;
    private VBox vTablilla, vMazo;
    private ImageView imvMazo;
    private Button[][] argBtnTablilla = new Button[4][4];
    private GridPane grdTablilla;
    private Button btnAnterior, btnSiguiente, btnInicio;
    public Loteria (){
        CrearUI();
        escena = new Scene(hPrincipal,1024, 600);
        this.setTitle("Loteria");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        CrearTablilla();
        crearMazo();

        btnAnterior = new Button("<");
        btnAnterior.setPrefSize(200,100);
        btnSiguiente = new Button(">");
        btnSiguiente.setPrefSize(200,100);
        hBtnSelec = new HBox(btnAnterior,btnSiguiente);
        vTablilla =new VBox(grdTablilla, hBtnSelec);
        vTablilla.setSpacing(20);

        /*vMazo = new VBox(); con crearMazo ya instanciamos lo anterior
        hPrincipal = new HBox(vTablilla, vMazo);
        hPrincipal.setPadding(new Insets(20)); */
    }

    public void crearMazo(){
        Image imgDorso = new Image("src/main/java/com/example/img/c.jpg");
        imvMazo = new ImageView(imgDorso);
        btnInicio = new Button("Iniciar");
        vMazo = new VBox(imvMazo,btnInicio);
    }

    private void CrearTablilla() {
        String[] arImagenes = {"c"};
        grdTablilla = new GridPane();
        int pos=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                /*Image imgCartP = new Image( //ruta absoluta
                        new File("C:\\Users\\elar4\\Pictures\\captura.jpg").toURI().toString());

                ImageView imv = new ImageView(imgCartP);
                argBtnTablilla[i][j] = new Button();
                argBtnTablilla[i][j].setPrefSize(90, 130);
                grdTablilla.add(argBtnTablilla[i][j],i,j);*/

                ImageView imv;
                try {
                    InputStream stream = new FileInputStream("src/main/java/com/example/img/c.jpg");
                    Image imgCartaP = new Image(stream);
                    imv = new ImageView(imgCartaP);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                imv.setFitHeight(140);
                imv.setFitWidth(100);
                argBtnTablilla[i][j] = new Button();
                argBtnTablilla[i][j].setPrefSize(90, 130);
                argBtnTablilla[i][j].setGraphic(imv);
                grdTablilla.add(argBtnTablilla[i][j],i,j);
            }
        }
    }
}

