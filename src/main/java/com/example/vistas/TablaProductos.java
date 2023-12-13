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

    public void crearTablaProductos(String nombreCategoria) throws FileNotFoundException {
        gridTablaProductos = new GridPane();
        ProductosDAO dao = new ProductosDAO();

        listaProductos = dao.crearYEnviarListaDeProductosConCategoria(nombreCategoria);

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
        argBtnTabla[0][2] = new Button("Imagen");
        argBtnTabla[0][2].setPrefSize(ancho-60,alto+60);
        argBtnTabla[0][2].setFont(new Font("Arial", 20));
        gridTablaProductos.add(argBtnTabla[0][0],0,0);
        gridTablaProductos.add(argBtnTabla[0][1],0,1);
        gridTablaProductos.add(argBtnTabla[0][2],0,2);

        for(int i=1; i<numProductos+1; i++) {
            idAux = listaProductos.get(i - 1).getIdProducto();
            nombreAux = listaProductos.get(i - 1).getNombre();
            precioAux = listaProductos.get(i - 1).getPrecio();
            idCatAux = listaProductos.get(i - 1).getIdCategoria();

            argBtnTabla[i][0] = new Button(nombreAux);
            argBtnTabla[i][0].setPrefSize(ancho, alto - 30);
            argBtnTabla[i][0].setFont(new Font("Arial", 16));
            gridTablaProductos.add(argBtnTabla[i][0], i, 0);

            argBtnTabla[i][1] = new Button("" + precioAux);
            argBtnTabla[i][1].setPrefSize(ancho, alto - 30);
            argBtnTabla[i][1].setFont(new Font("Arial", 16));
            gridTablaProductos.add(argBtnTabla[i][1], i, 1);

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
                try {
                    tablaActual.cambiarATablaDeCategorias();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                //Aqui van operaciones para ir mostrando el carrito abajo
                Producto producto = new Producto(finalIdAux, finalNombreAux, finalPrecioAux, finalImagenAux, finalIdCatAux);
                tablaActual.getTablaCarrito().getCarrito().setProducto(producto);
                try {
                    tablaActual.getTablaCarrito().actualizarTablaCarrito();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(finalI <=7) {
                    double posicionActual;
                    posicionActual = formularioCliente.getvBoxFormulario().getTranslateX();
                    formularioCliente.getvBoxFormulario().setTranslateX(posicionActual - ancho);
                }
            });
            gridTablaProductos.add(argBtnTabla[i][2], i, 2);
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
