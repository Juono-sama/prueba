package com.example.vistas;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;

public class TablaActual {
    public TablaProductos tablaProductos;
    public TablaCategorias tablaCategorias;
    public TablaCarrito tablaCarrito;
    private FormularioCliente formularioCliente;
    public GridPane gridTablaActual;
    public VBox vBox;
    public Label labelTablaActual, labelAnuncio;

    public TablaActual(){
        gridTablaActual = new GridPane();
        labelTablaActual = new Label();
        labelAnuncio = new Label(); labelAnuncio.setFont(new Font("Arial", 14));
        vBox = new VBox(labelAnuncio, labelTablaActual);
    }

    public void generarTablaCategoriasInicial() throws FileNotFoundException {
        tablaCategorias.crearTablaCategorias();
        setGridTablaActual(tablaCategorias.getGridTablaCategorias());
        labelAnuncio.setText("Categorias:");
        actualizarLabel();
    }

    public void cambiarATablaDeProductos(String nombreDeCategoria) throws FileNotFoundException {
        tablaProductos.crearTablaProductos(nombreDeCategoria);
        tablaProductos.setCarrito(tablaCarrito.getCarrito());
        setGridTablaActual(tablaProductos.getGridTablaProductos());
        labelAnuncio.setText("Productos:");
        actualizarLabel();
    }

    public void cambiarATablaDeCategorias() throws FileNotFoundException {
        tablaCategorias.crearTablaCategorias();
        setGridTablaActual(tablaCategorias.getGridTablaCategorias());
        labelAnuncio.setText("Categorias:");
        actualizarLabel();
    }

    public void actualizarLabel(){
        labelTablaActual.setGraphic(getGridTablaActual());
    }

    public TablaProductos getTablaProductos() {
        return tablaProductos;
    }

    public void setTablaProductos(TablaProductos tablaProductos) {
        this.tablaProductos = tablaProductos;
    }

    public TablaCategorias getTablaCategorias() {
        return tablaCategorias;
    }

    public void setTablaCategorias(TablaCategorias tablaCategorias) {
        this.tablaCategorias = tablaCategorias;
    }

    public GridPane getGridTablaActual() {
        return gridTablaActual;
    }

    public void setGridTablaActual(GridPane gridTablaActual) {
        this.gridTablaActual = gridTablaActual;
    }

    public Label getLabelTablaActual() {
        return labelTablaActual;
    }

    public void setLabelTablaActual(Label labelTablaActual) {
        this.labelTablaActual = labelTablaActual;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public TablaCarrito getTablaCarrito() {
        return tablaCarrito;
    }

    public void setTablaCarrito(TablaCarrito tablaCarrito) {
        this.tablaCarrito = tablaCarrito;
    }

    public FormularioCliente getFormularioCliente() {
        return formularioCliente;
    }

    public void setFormularioCliente(FormularioCliente formularioCliente) {
        this.formularioCliente = formularioCliente;
    }
}
