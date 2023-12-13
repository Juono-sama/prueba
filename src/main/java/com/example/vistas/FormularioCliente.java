package com.example.vistas;

import com.example.modelos.CarritoDao;
import com.example.modelos.ClientesDAO;
import com.example.modelos.ProductoCarritoDao;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FormularioCliente {
    private Cliente cliente;
    public TablaCarrito tablaCarrito;
    public TablaActual tablaActual;
    public ClientesDAO clientesDAO;
    public CarritoDao carritoDao;
    private Label labelBotonComprar, labelCampoNombre;
    private GridPane gridFormulario;
    private Button botonComprar;
    private TextField campoParaNombre;
    private VBox vBoxFormulario;
    private String nombreCliente, productosComprados = "Aún no ha comprado nada.";

    public FormularioCliente() {
        campoParaNombre = new TextField();
        campoParaNombre.setPrefSize(225, 125);
        campoParaNombre.setFont(new Font("Arial", 21));
        campoParaNombre.setPromptText("Nombre del cliente");
        campoParaNombre.setEditable(true);

        botonComprar = new Button("Comprar");
        botonComprar.setPrefSize(225, 150);
        botonComprar.setFont(new Font("Arial", 34));
        botonComprar.setOnAction((event) -> {
            try {
                presionarBotonComprar();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        labelBotonComprar = new Label();
        labelBotonComprar.setGraphic(botonComprar);

        labelCampoNombre = new Label();
        labelCampoNombre.setGraphic(campoParaNombre);

        vBoxFormulario = new VBox(labelCampoNombre, labelBotonComprar);
        vBoxFormulario.setTranslateX(1140); vBoxFormulario.setTranslateY(50);
        vBoxFormulario.setSpacing(10);
    }

    public void presionarBotonComprar() throws FileNotFoundException {
        if (JOptionPane.showConfirmDialog(null, "¿Esta seguro de que quiere comprar lo siguiente?", "¿Comprar?",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

            leerEInsertarNombreEscrito();
            insertarCarritoEnBD();
            insertarProductosDeCarritoEnBD();

            reiniciarClienteYCarrito();
        }
    }

    private void insertarProductosDeCarritoEnBD() {
        List<Producto> productosAComprar = tablaCarrito.getListaProducto();
        List<Integer> idsRevisados = new ArrayList<>();

        int idCliente = clientesDAO.getIdUltimoCliente();
        int idCarrito = carritoDao.getIdUltimoCarrito();

        int idProductoAux, cantidadAux1 = 0;
        int tamanoProductosAComprar = productosAComprar.size();
        int i = 0, j = 0;

        //Revisa los productos para saber la cantidad de estos en el carrito
        while (i < tamanoProductosAComprar) {
            if (idsRevisados.contains(productosAComprar.get(i).idProducto)) {
                //Si ya revisamos ese id, no hace nada
            } else {
                //Si aun no revisa ese id, revisa cuanto de ese producto compraste
                idProductoAux = productosAComprar.get(i).getIdProducto();
                cantidadAux1=0;
                while (j < tamanoProductosAComprar) {
                    if (idProductoAux == productosAComprar.get(j).getIdProducto()) {
                        //Cuando el idAux coincida con uno de los productos a comprar, aumenta la cantidad de ese producto comprado
                        cantidadAux1++;
                    }
                    j++;
                }
                idsRevisados.add(idProductoAux); //Marca el id del producto actual como revisado

                //Inserta el producto con su cantidad en la tabla proCarrito
                ProductoCarritoDao productoCarritoDao = new ProductoCarritoDao();
                productoCarritoDao.setIdCliente(idCliente);
                productoCarritoDao.setIdCarrito(idCarrito);
                productoCarritoDao.setIdProducto(idProductoAux);
                productoCarritoDao.setCantidad(cantidadAux1);
                productoCarritoDao.insertarProductoCarrito();

                if(i==0){
                    productosComprados = "Usted ha comprado los siguientes productos:";
                }
                productosComprados = productosComprados+"\n"+cantidadAux1+" "+productosAComprar.get(i).getNombre();
            }//rehace las variables para que podamos continuar.
            i++;
            j = i;
        }

        JOptionPane.showMessageDialog(null, productosComprados, "Productos comprados",JOptionPane.INFORMATION_MESSAGE);
    }

    private void insertarCarritoEnBD(){
        carritoDao = new CarritoDao();
        int idCliente = clientesDAO.getIdUltimoCliente();
        int idCarrito = carritoDao.getIdUltimoCarrito()+1;
        carritoDao.insertarCarrito(idCliente, idCarrito);
    }

    private void leerEInsertarNombreEscrito(){
        nombreCliente = campoParaNombre.getText();
        clientesDAO = new ClientesDAO();
        clientesDAO.setNombre(nombreCliente);
        clientesDAO.insertarCliente();
    }

    public void reiniciarClienteYCarrito() throws FileNotFoundException {
        cliente = new Cliente();
        cliente.setNombre(campoParaNombre.getText());
        tablaCarrito.reiniciarTablaCarrito();
        campoParaNombre.clear();
        tablaActual.cambiarATablaDeCategorias();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public GridPane getGridFormulario() {
        return gridFormulario;
    }

    public void setGridFormulario(GridPane gridFormulario) {
        this.gridFormulario = gridFormulario;
    }

    public VBox getvBoxFormulario() {
        return vBoxFormulario;
    }

    public void setvBoxFormulario(VBox vBoxFormulario) {
        this.vBoxFormulario = vBoxFormulario;
    }

    public TablaCarrito getTablaCarrito() {
        return tablaCarrito;
    }

    public void setTablaCarrito(TablaCarrito tablaCarrito) {
        this.tablaCarrito = tablaCarrito;
    }

    public TablaActual getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(TablaActual tablaActual) {
        this.tablaActual = tablaActual;
    }

    public CarritoDao getCarritoDao() {
        return carritoDao;
    }

    public void setCarritoDao(CarritoDao carritoDao) {
        this.carritoDao = carritoDao;
    }

}
