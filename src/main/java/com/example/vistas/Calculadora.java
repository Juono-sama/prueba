package com.example.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calculadora extends Stage {
    private Scene escena;
    private VBox vBox;
    private GridPane gridTeclado;
    private TextField txtResultado;
    private Button[][] arTeclas = new Button[4][4]; //Un arreglo o "filder" de botones de 4x4
    /*private char[] arDigitos = {
            '7','8','9','/',
            '4','5','6','x',
            '1','2','3','-',
            '0','.','+','='};*/
    private String[] arDigitos = {
            "7", "4", "1", "0",
            "8", "5", "2", ".",
            "9", "6", "3", "+",
            "/", "x", "-", "="};


    int num1=0, num2=0, numR=0;



    public Calculadora() {
        CrearUI();
        //escena = new Scene(new Button("X"), 200, 200);
        escena = new Scene(vBox, 200, 200);
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    private void generarExpresion(String simbolo) {
        String expresionAnterior=txtResultado.getText();
        int l=expresionAnterior.length();
        String ultimoDigito=expresionAnterior.substring(l-1);

        if(expresionAnterior.equals("Sintax Error")){
            limpiar();}
        if(expresionAnterior.equals("0")) {
            if(simbolo.equals(".") || simbolo.equals("+") || simbolo.equals("-") || simbolo.equals("x") || simbolo.equals("/")) {
            } else {
                limpiar();
            }
        }

        String nuevoTexto="";
        if(simbolo.equals("+") || simbolo.equals("x")||simbolo.equals("/")){
            if(ultimoDigito.equals("+") || ultimoDigito.equals("-") || ultimoDigito.equals("x") || ultimoDigito.equals("/")){
                nuevoTexto=expresionAnterior.substring(0,l-1);
                txtResultado.setText(nuevoTexto);
                if(nuevoTexto.substring(l-2).equals("x")){
                    nuevoTexto=expresionAnterior.substring(0,l-2);
                    txtResultado.setText(nuevoTexto);
                }
            }
        }
        if(simbolo.equals("-")){
            if(ultimoDigito.equals("+") || ultimoDigito.equals("-")){
                nuevoTexto=expresionAnterior.substring(0,l-1);
                txtResultado.setText(nuevoTexto);
            }
        }



        txtResultado.appendText(simbolo);
    }

    private void CrearUI() {
        int c = 0;
        gridTeclado = new GridPane();
        vBox = new VBox();
        txtResultado = new TextField("0");
        txtResultado.setAlignment(Pos.BASELINE_RIGHT);
        txtResultado.setEditable(false);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                arTeclas[i][j] = new Button(arDigitos[c]);
                arTeclas[i][j].setPrefSize(50, 50);
                int finalC1 = c;
                if (i == 3 && j == 3) {
                arTeclas[i][j].setOnAction( (event)->validarExpresion() );
                }
                else{
                    arTeclas[i][j].setOnAction(
                            (event) -> generarExpresion(arDigitos[finalC1]));}
                if(c==7){}
                gridTeclado.add(arTeclas[i][j], i, j);
                c++;
            }
        }

        vBox = new VBox(txtResultado, gridTeclado);
    }

    private void igual(){
        boolean validado=true;
        validado = validarExpresion();
        //Si si está validad, calcule
        if(validado){
            calcular();
        }

    }

    private boolean validarExpresion() {
        boolean validacion=true;
        String expresion = txtResultado.getText();
        int longitud = expresion.length();

        char primerSimbolo = expresion.charAt(0);
        switch(primerSimbolo) {
            case '+','-','x','/':
                txtResultado.setText("Sintax Error");
                validacion = false;
                break;
            default:
                break;

        }

        char ultimoSimbolo = expresion.charAt(longitud-1);
        switch(ultimoSimbolo) {
            case '+','-','x','/':
                txtResultado.setText("Sintax Error");
                validacion= false;
                break;
            default:
                break;

        }
        return validacion;
    }

    private void calcular(){

    }

    private void limpiar(){
        String expresion = txtResultado.getText();
        switch(expresion){
            case "Sintax Error", "0":
                txtResultado.setText("");
                break;
            default:
                break;
        }
    }

}
