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

    //Variables que usaremos para tener registro de numeros y operandos para calcularlos después
    double num1=0, num2=0, numR=0;
    String registroActual="0", operacionFinal="";

    //Muestra la calculadora
    public Calculadora() {
        CrearUI();
        escena = new Scene(vBox, 200, 200);
        escena.getStylesheets()
                .add(getClass()
                        .getResource("/estilos/calculadora.css").toExternalForm());
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    //Generará la expresion tomando en cuenta varias cosas antes de hacerlo.  (Este método fue el diablo encarnado XD)
    private void generarExpresion(String simbolo) {
        String expresionAnterior=txtResultado.getText();
        int l=expresionAnterior.length();
        String ultimoDigito=expresionAnterior.substring(l-1);

        // Borra todo lo que tenga adentro si dice Sintax o Math Error o un 0 a menos que despues del 0 se ponga un signo
        if(expresionAnterior.equals("Sintax Error") || expresionAnterior.equals("Math Error")){
            limpiar();}
        if(expresionAnterior.equals("0")) {
            if(simbolo.equals(".") || simbolo.equals("+") || simbolo.equals("-") || simbolo.equals("x") || simbolo.equals("/")) {}
            else {
                limpiar();
            }
        }

        //En caso de que se ponga un signo después de otro, lo borra para evitar errores. (Y no hacer tan complicado leer los datos XD)
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

        //Si escribe un numero o punto después de un operando, guarda el numero o punto y hace la operacion que hay hasta ahora.
        if(Character.isDigit(simbolo.charAt(0)) || simbolo.equals(".")){
            switch(ultimoDigito){
                case "+":
                    calcular(ultimoDigito);
                    operacionFinal="+";
                    registroActual=""+simbolo;
                    break;
                case "-":
                    switch(expresionAnterior.substring(l-2, l-1)){
                        case "x":
                            num1=num1*(-1);
                            if(operacionFinal.equals("")){
                                calcular("+");
                            }
                            else{calcular("x");
                            }
                            operacionFinal="x";
                            registroActual ="-"+simbolo;
                            break;
                        case "/":
                            num1=num1*(-1);
                            if(operacionFinal.equals("")){
                                calcular("+");
                            }
                            else{calcular("/");
                            }
                            operacionFinal="/";
                            registroActual ="-"+simbolo;
                            break;
                        default:
                            if(operacionFinal.equals("")){
                                calcular("+");
                            }
                            else{calcular("-");
                                }
                            operacionFinal="-";
                            registroActual =""+simbolo;
                            break;
                    }
                    break;
                case "x":
                    if(operacionFinal.equals("")){
                        calcular("+");
                    }
                    else{calcular("x");
                    }
                    operacionFinal="x";
                    registroActual =""+simbolo;
                    break;
                case "/":
                    if(operacionFinal.equals("")){
                        calcular("+");
                    }
                    else{calcular("/");
                    }
                    operacionFinal="/";
                    registroActual =""+simbolo;
                    break;
                default:
                    registroActual=registroActual+simbolo;
                    break;
            }
        }

        //Al revisar todo lo anterior, escribe el simbolo
        txtResultado.appendText(simbolo); //Que sufrimiento de método >n<
    }

    //Solo calcula en base al operando que tenga y a los numeros que ya esten registrados
    private void calcular(String operando){
        num2=Double.parseDouble(registroActual);
        switch(operando){
            case "+":
                numR=num1+num2;
                break;
            case "-":
                numR=num1-num2;
                break;
            case "x":
                numR=num1*num2;
                break;
            case "/":
                numR=num1/num2;
                break;
            default:
                break;
        }
        num1=numR;
    }

    //Crea la interfaz gráfica
    private void CrearUI() {
        int c = 0;
        gridTeclado = new GridPane();
        vBox = new VBox();
        txtResultado = new TextField("0");
        txtResultado.setAlignment(Pos.BASELINE_RIGHT);
        txtResultado.setEditable(false);

        //Asigna los botones
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //Inicializa los botones y les da su tamaño
                arTeclas[i][j] = new Button(arDigitos[c]);
                arTeclas[i][j].setPrefSize(50, 50);
                int finalC1 = c;
                if (i == 3 && j == 3) { //Le da funcion al signo igual para evaluar la expresion
                    arTeclas[i][j].setOnAction( (event) -> validarExpresion() );
                }
                else{ //Le da funcion a todas las demas teclas
                    arTeclas[i][j].setOnAction( (event) -> generarExpresion(arDigitos[finalC1]));
                }
                gridTeclado.add(arTeclas[i][j], i, j); //Añade los simbolos a las teclas
                c++;
            }
        }
        vBox = new VBox(txtResultado, gridTeclado); //Muestra el teclado
    }

    //Valida la expresion escrita, se activa al presionar la tecla =  (Esto CASI llega a ser el diablo encarnado XD)
    private void validarExpresion() {
        String expresion = txtResultado.getText();
        int longitud = expresion.length();

        //Sintax error en caso de que el primer simbolo sea un signo
        char primerSimbolo = expresion.charAt(0);
        switch(primerSimbolo) {
            case '+','-','x','/':
                txtResultado.setText("Sintax Error");
                break;
            default:
                break;

        }

        //Sintax error en caso de que el ultimo simbolo es un signo.
        char ultimoSimbolo = expresion.charAt(longitud-1);
        switch(ultimoSimbolo) {
            case '+','-','x','/':
                txtResultado.setText("Sintax Error");
                break;
            default:
                break;

        }

        //Muestra el resultado
        if(numR!=0){
            calcular(operacionFinal);
            txtResultado.setText(""+numR);
            operacionFinal=""; num2=0; registroActual="0";
            if(txtResultado.getText().equals("Infinity")){
                txtResultado.setText("Math Error");
            }
        }
        else{ txtResultado.setText("Math Error"); }
    }

    //Borra lo que haya en la expresion y sus registros. Se usa en caso de teclear algo despues de "Sintax o Math Error" o que la expresion sea un 0
    private void limpiar(){
        String expresion = txtResultado.getText();
        switch(expresion){
            case "Sintax Error", "0", "Math Error":
                txtResultado.setText("");
                break;
            default:
                break;
        }
        operacionFinal=""; numR=0; num1=0; num2=0; registroActual="0";
    }
}