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

    public FormularioCliente() { //Crea un cuadro de texto para poner el nombre del cliente y el botón de comprar
        //Crea el cuadro de texto del nombde del cliente
        campoParaNombre = new TextField();
        campoParaNombre.setPrefSize(225, 125);
        campoParaNombre.setFont(new Font("Arial", 21));
        campoParaNombre.setPromptText("Nombre del cliente");
        campoParaNombre.setEditable(true);

        //Crea el botón de comprar
        botonComprar = new Button("Comprar");
        botonComprar.setPrefSize(225, 150);
        botonComprar.setFont(new Font("Arial", 34));
        botonComprar.setOnAction((event) -> {
            try {
                presionarBotonComprar(); //Accion de comprar
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        //Labels para contener al boton y al cuadro de texto
        labelBotonComprar = new Label();
        labelBotonComprar.setGraphic(botonComprar);

        labelCampoNombre = new Label();
        labelCampoNombre.setGraphic(campoParaNombre);

        //vBox para contener a los labels anteriores
        vBoxFormulario = new VBox(labelCampoNombre, labelBotonComprar);
        vBoxFormulario.setTranslateX(1140); vBoxFormulario.setTranslateY(50);
        vBoxFormulario.setSpacing(10);
    }

    //Al presionar el botón de comprar, lee el nombre e inserta los datos en las tablas Carrito y proCarrito de la base de datos
    public void presionarBotonComprar() throws FileNotFoundException {
        //Pregunta para confirmar la compra
        if (JOptionPane.showConfirmDialog(null, "¿Esta seguro de que quiere comprar lo siguiente?",
                "¿Comprar?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            //Acciones al confirmar la compra
            leerEInsertarNombreEscrito(); //inserta los datos de la tabla Cliente de la base de datos
            insertarCarritoEnBD(); //inserta los datos de la tabla Carrito de la base de datos
            insertarProductosDeCarritoEnBD(); //inserta los datos de la tablaproCarrrito de la base de datos

            reiniciarClienteYCarrito(); //Reinicia todo para dejar que otro cliente compre.
        }
    }

    //Lee la caja de texto y inserta los datos en la base de datos con el objeto DAO de la tabla Cliente
    private void leerEInsertarNombreEscrito(){
        nombreCliente = campoParaNombre.getText();
        clientesDAO = new ClientesDAO();
        clientesDAO.setNombre(nombreCliente);
        clientesDAO.insertarCliente();
    }

    //Recupera el ultimo cliente y carrito insertados para crear un nuevo registro de la tabla Carrrito con el objeto DAO de la tabla Carrito
    private void insertarCarritoEnBD(){
        carritoDao = new CarritoDao();
        int idCliente = clientesDAO.getIdUltimoCliente();
        int idCarrito = carritoDao.getIdUltimoCarrito()+1;
        carritoDao.insertarCarrito(idCliente, idCarrito);
    }

    //Recupera el cliente y carrito antes insertados y crea registros de los productos comprados usando el objeto Dao de la tabla proCarrito
    private void insertarProductosDeCarritoEnBD() { //
        //Crea una lista con los productos del carrito y los id de los productos que vaya revisando
        List<Producto> productosAComprar = tablaCarrito.getListaProducto();
        List<Integer> idsRevisados = new ArrayList<>();

        //Recupera el ultimo cliente y carrito insertados.
        int idCliente = clientesDAO.getIdUltimoCliente();
        int idCarrito = carritoDao.getIdUltimoCarrito();

        //Variables auxiliares para insertar datos de la tabla de proCarrito
        int idProductoAux, cantidadAux1 = 0;
        float precioCompra=0, precioTotal=0;
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

                //Va sumando el precio de las compras
                precioCompra = productosAComprar.get(i).getPrecio() * cantidadAux1;
                precioTotal = precioTotal + precioCompra;

                if(i==0){
                    productosComprados = "Usted ha comprado los siguientes productos:";
                }
                productosComprados = productosComprados+"\n"+cantidadAux1+" "+productosAComprar.get(i).getNombre()+"  $"+precioCompra;
            }//rehace las variables para que podamos continuar.
            i++;
            j = i;
        }

        //Crea el cuadro de diálogo al terminar la compra
        productosComprados = productosComprados+"\n\nTotal a pagar: $"+precioTotal;
        JOptionPane.showMessageDialog(null, productosComprados, "Productos comprados",JOptionPane.INFORMATION_MESSAGE);
    }

    //Reinicia las tablas mostradas para hacer otra compra
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
