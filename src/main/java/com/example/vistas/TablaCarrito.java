package com.example.vistas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class TablaCarrito {
    public List<Producto> listaProductos;
    public GridPane gridTablaCarrito;
    public Button[][] argBtnTabla;
    public FormularioCliente formularioCliente;
    private Label labelTablaCarrito;
    public Carrito carrito;

    public TablaCarrito(){
        gridTablaCarrito = new GridPane();
        labelTablaCarrito = new Label();
        carrito = new Carrito();
    }

    public void reiniciarTablaCarrito() throws FileNotFoundException {
        if (listaProductos != null) {
            carrito.getListaProductos().clear();
            gridTablaCarrito = new GridPane();
            actualizarTablaCarrito();
            formularioCliente.getvBoxFormulario().setTranslateX(1140);
        }
    }

    public void actualizarTablaCarrito() throws FileNotFoundException {
        listaProductos = carrito.getListaProductos();

        int numProductos, idAux, idCatAux, ancho=160, alto=120;
        String nombreAux;
        float precioAux;
        ImageView imagenAux;

        numProductos = listaProductos.size();
        argBtnTabla = new Button[numProductos+1][3];

        argBtnTabla[0][0] = new Button("Nombre");
        argBtnTabla[0][0].setPrefSize(ancho-60, alto-30);
        argBtnTabla[0][0].setFont(new Font("Arial", 20));
        argBtnTabla[0][1] = new Button("Precio");
        argBtnTabla[0][1].setPrefSize(ancho-60,alto-30);
        argBtnTabla[0][1].setFont(new Font("Arial", 20));
        argBtnTabla[0][2] = new Button("Imagen \n(Seleccione \npara \neliminar \ndel carrito)");
        argBtnTabla[0][2].setPrefSize(ancho-60,alto+60);
        argBtnTabla[0][2].setFont(new Font("Arial", 14));
        gridTablaCarrito.add(argBtnTabla[0][0],0,0);
        gridTablaCarrito.add(argBtnTabla[0][1],0,1);
        gridTablaCarrito.add(argBtnTabla[0][2],0,2);

        for(int i=1; i<numProductos+1; i++) {
            idAux = listaProductos.get(i - 1).getIdProducto();
            nombreAux = listaProductos.get(i - 1).getNombre();
            precioAux = listaProductos.get(i - 1).getPrecio();
            idCatAux = listaProductos.get(i - 1).getIdCategoria();

            argBtnTabla[i][0] = new Button(nombreAux);
            argBtnTabla[i][0].setPrefSize(ancho, alto - 30);
            argBtnTabla[i][0].setFont(new Font("Arial", 16));
            gridTablaCarrito.add(argBtnTabla[i][0], i, 0);

            argBtnTabla[i][1] = new Button("" + precioAux);
            argBtnTabla[i][1].setPrefSize(ancho, alto - 30);
            argBtnTabla[i][1].setFont(new Font("Arial", 16));
            gridTablaCarrito.add(argBtnTabla[i][1], i, 1);

            argBtnTabla[i][2] = new Button();
            argBtnTabla[i][2].setPrefSize(ancho, alto + 60);
            imagenAux = new ImageView(new Image(new FileInputStream("src/main/java/com/example/img/r/" + nombreAux + ".jpg")));
            imagenAux.setFitHeight(alto + 40);
            imagenAux.setFitWidth(ancho - 20);
            argBtnTabla[i][2].setGraphic(imagenAux);

            //Variables temporales para el boton {
            /*int finalIdAux = idAux;
            String finalNombreAux = nombreAux;
            float finalPrecioAux = precioAux;
            ImageView finalImagenAux = imagenAux;
            int finalIdCatAux = idCatAux;*/
            //}
            int finalI = i;
            argBtnTabla[i][2].setOnAction((event) -> {
                gridTablaCarrito = new GridPane();
                carrito.getListaProductos().remove(finalI -1);
                try {
                    actualizarTablaCarrito();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(finalI <=7){
                    double posicionActual;
                    posicionActual = formularioCliente.getvBoxFormulario().getTranslateX();
                    formularioCliente.getvBoxFormulario().setTranslateX(posicionActual+ancho);
                }
            });
            gridTablaCarrito.add(argBtnTabla[i][2], i, 2);
        }

        actualizarLabel();
    }

    public void generarTablaCarritoInicial(){
        argBtnTabla = new Button[1][3];
        int ancho=160, alto=120;

        argBtnTabla[0][0] = new Button("Nombre");
        argBtnTabla[0][0].setPrefSize(ancho-60, alto-30);
        argBtnTabla[0][0].setFont(new Font("Arial", 20));
        argBtnTabla[0][1] = new Button("Precio");
        argBtnTabla[0][1].setPrefSize(ancho-60,alto-30);
        argBtnTabla[0][1].setFont(new Font("Arial", 20));
        argBtnTabla[0][2] = new Button("Imagen");
        argBtnTabla[0][2].setPrefSize(ancho-60,alto+60);
        argBtnTabla[0][2].setFont(new Font("Arial", 20));
        gridTablaCarrito.add(argBtnTabla[0][0],0,0);
        gridTablaCarrito.add(argBtnTabla[0][1],0,1);
        gridTablaCarrito.add(argBtnTabla[0][2],0,2);

        actualizarLabel();
    }



    public void actualizarLabel(){
        labelTablaCarrito.setGraphic(getGridTablaCarrito());
    }

    public List<Producto> getListaProducto() {
        return listaProductos;
    }

    public void setListaProducto(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public GridPane getGridTablaCarrito() {
        return gridTablaCarrito;
    }

    public void setGridTablaCarrito(GridPane gridTablaCarrito) {
        this.gridTablaCarrito = gridTablaCarrito;
    }

    public Button[][] getArgBtnTabla() {
        return argBtnTabla;
    }

    public void setArgBtnTabla(Button[][] argBtnTabla) {
        this.argBtnTabla = argBtnTabla;
    }

    public Label getLabelTablaCarrito() {
        return labelTablaCarrito;
    }

    public void setLabelTablaCarrito(Label labelTablaCarrito) {
        this.labelTablaCarrito = labelTablaCarrito;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
    public FormularioCliente getFormularioCliente() {
        return formularioCliente;
    }

    public void setFormularioCliente(FormularioCliente formularioCliente) {
        this.formularioCliente = formularioCliente;
    }

}
