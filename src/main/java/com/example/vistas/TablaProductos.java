package com.example.vistas;

import com.example.modelos.ProductosDAO;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class TablaProductos {
    public List<Producto> listaProductos;
    public GridPane gridTablaProductos;
    public Button[][] argBtnTabla;
    private TablaActual tablaActual;
    private Carrito carrito;
    private TablaCarrito tablaCarrito;

    private FormularioCliente formularioCliente;
    public TablaProductos(){ //Crea la cuadricula para hacer la tabla de productos
        gridTablaProductos = new GridPane();
    }

    //Crea una cuadricula de botones, dando la impresion de ser una tabla
    public void crearTablaProductos(String nombreCategoria) throws FileNotFoundException {
        /*Crea un objeto DAO que se relaciona con la tabla Productos de la base de datos
        y le decimos que nos dé todos los datos que tenga*/
        ProductosDAO dao = new ProductosDAO();
        listaProductos = dao.crearYEnviarListaDeProductosConCategoria(nombreCategoria);

        //Variables auxiliares
        int numProductos, idAux, idCatAux, ancho=160, alto=120;
        String nombreAux;
        float precioAux;
        ImageView imagenAux;

        //Crea el arreglo de los botones dependiendo que tantos productos hay de la categoria elegida
        numProductos = listaProductos.size();
        argBtnTabla = new Button[numProductos+1][3];

        //Crea la primer columna de botones de la tabla de Productos
        argBtnTabla[0][0] = new Button("Nombre");
        argBtnTabla[0][0].setPrefSize(ancho-60, alto-30);
        argBtnTabla[0][0].setFont(new Font("Arial", 20));
        argBtnTabla[0][1] = new Button("Precio");
        argBtnTabla[0][1].setPrefSize(ancho-60,alto-30);
        argBtnTabla[0][1].setFont(new Font("Arial", 20));
        argBtnTabla[0][2] = new Button("Imagen");
        argBtnTabla[0][2].setPrefSize(ancho-60,alto+60);
        argBtnTabla[0][2].setFont(new Font("Arial", 20));
        //Añade los botones a la primer columna de la cuadricula
        gridTablaProductos.add(argBtnTabla[0][0],0,0);
        gridTablaProductos.add(argBtnTabla[0][1],0,1);
        gridTablaProductos.add(argBtnTabla[0][2],0,2);

        //Crea todos los botones de los productos, solo el de la imagen tiene accion, los demás solo son para mostrar datos
        for(int i=1; i<numProductos+1; i++) {
            idAux = listaProductos.get(i - 1).getIdProducto();
            nombreAux = listaProductos.get(i - 1).getNombre();
            precioAux = listaProductos.get(i - 1).getPrecio();
            idCatAux = listaProductos.get(i - 1).getIdCategoria();

            //Boton del nombre
            argBtnTabla[i][0] = new Button(nombreAux);
            argBtnTabla[i][0].setPrefSize(ancho, alto - 30);
            argBtnTabla[i][0].setFont(new Font("Arial", 16));
            gridTablaProductos.add(argBtnTabla[i][0], i, 0); //Agrega el boton a la cuadricuka

            //Botón del precio
            argBtnTabla[i][1] = new Button("" + precioAux);
            argBtnTabla[i][1].setPrefSize(ancho, alto - 30);
            argBtnTabla[i][1].setFont(new Font("Arial", 16));
            gridTablaProductos.add(argBtnTabla[i][1], i, 1); //Agrega el boton a la cuadricula

            //Botón de la imagen del producto, al presionarla añade el producto al carrito
            argBtnTabla[i][2] = new Button();
            argBtnTabla[i][2].setPrefSize(ancho, alto + 60);
            imagenAux = new ImageView(new Image(new FileInputStream("src/main/java/com/example/img/r/" + nombreAux + ".jpg")));
            imagenAux.setFitHeight(alto + 40);
            imagenAux.setFitWidth(ancho - 20);
            argBtnTabla[i][2].setGraphic(imagenAux);

            //Variables temporales para el boton {
                int finalIdAux = idAux;
                String finalNombreAux = nombreAux;
                float finalPrecioAux = precioAux;
                ImageView finalImagenAux = imagenAux;
                int finalIdCatAux = idCatAux;
            //}
            int finalI = i;
            argBtnTabla[i][2].setOnAction((event) -> {
                try {//Primero cambia la tabla de productos por la de categorias para hacer otra compra
                    tablaActual.cambiarATablaDeCategorias();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                //Agrega el producto a la tabla del carrito
                Producto producto = new Producto(finalIdAux, finalNombreAux, finalPrecioAux, finalImagenAux, finalIdCatAux);
                tablaActual.getTablaCarrito().getCarrito().setProducto(producto);
                try {
                    tablaActual.getTablaCarrito().actualizarTablaCarrito(); //Actualiza la tabla del carrito
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(finalI <=7) { //Esto posiciona el boton de comprar para que no se mueva de donde está
                    double posicionActual;
                    posicionActual = formularioCliente.getvBoxFormulario().getTranslateX();
                    formularioCliente.getvBoxFormulario().setTranslateX(posicionActual - ancho);
                }
            }); //Fin de las acciones del botón de la imagen del producto
            gridTablaProductos.add(argBtnTabla[i][2], i, 2); //Agrega el boton a la cuadricula
        }
    }
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public GridPane getGridTablaProductos() {
        return gridTablaProductos;
    }

    public void setGridTablaProductos(GridPane gridTablaProductos) {
        this.gridTablaProductos = gridTablaProductos;
    }

    public Button[][] getArgBtnTabla() {
        return argBtnTabla;
    }

    public void setArgBtnTabla(Button[][] argBtnTabla) {
        this.argBtnTabla = argBtnTabla;
    }

    public TablaActual getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(TablaActual tablaActual) {
        this.tablaActual = tablaActual;
    }
    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
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
