package com.example.vistas;

import com.example.modelos.CategoriasDAO;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class TablaCategorias {
    public List<Categoria> listaCategorias;
    public GridPane gridTablaCategorias;
    private Button[][] argBtnTabla;
    private TablaProductos tablaProductos;
    private TablaActual tablaActual;
    private Restaurante restaurante;

    public TablaCategorias(){
        gridTablaCategorias = new GridPane();
    }

    public void crearTablaCategorias() throws FileNotFoundException {
        CategoriasDAO dao = new CategoriasDAO();
        listaCategorias = dao.crearYEnviarLista();


        int idCategoriaAux, numCategorias, ancho=180, alto=180;
        String categoriaAux, productoAux;
        ImageView imageViewProductoAux;

        numCategorias = listaCategorias.size();
        argBtnTabla = new Button[numCategorias+1][2];

        argBtnTabla[0][0] = new Button("Nombre de la \ncategoria de \nproductos");
        argBtnTabla[0][0].setPrefSize(ancho,alto-90);
        argBtnTabla[0][0].setFont(new Font("Arial", 20));
        argBtnTabla[0][1] = new Button("Imagen del \ntipo de \nproductos");
        argBtnTabla[0][1].setPrefSize(ancho,alto+90);
        argBtnTabla[0][1].setFont(new Font("Arial", 20));
        gridTablaCategorias.add(argBtnTabla[0][0],0,0);
        gridTablaCategorias.add(argBtnTabla[0][1],0,1);

        for(int i = 1; i < numCategorias+1; i++) {
            categoriaAux = listaCategorias.get(i-1).getCategoria();
            productoAux = listaCategorias.get(i-1).getProducto();
            //productoAux = productoAux+".jpg";

            argBtnTabla[i][0] = new Button(categoriaAux);
            argBtnTabla[i][0].setPrefSize(ancho,alto-90);
            argBtnTabla[i][0].setFont(new Font("Arial", 16));
            gridTablaCategorias.add(argBtnTabla[i][0], i, 0);

            argBtnTabla[i][1] = new Button();
            argBtnTabla[i][1].setPrefSize(ancho,alto+90);
            imageViewProductoAux = new ImageView(new Image(new FileInputStream("src/main/java/com/example/img/r/"+productoAux+".jpg")));
            imageViewProductoAux.setFitHeight(alto+60);
            imageViewProductoAux.setFitWidth(ancho-20);
            argBtnTabla[i][1].setGraphic(imageViewProductoAux);
            String finalCategoriaAux = categoriaAux;
            argBtnTabla[i][1].setOnAction((event) -> {
                try {
                    tablaActual.cambiarATablaDeProductos(finalCategoriaAux);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            gridTablaCategorias.add(argBtnTabla[i][1], i, 1);
        }
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }
    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
    public GridPane getGridTablaCategorias() {
        return gridTablaCategorias;
    }
    public void setGridTablaCategorias(GridPane gridTablaCategorias) {
        this.gridTablaCategorias = gridTablaCategorias;
    }
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public TablaProductos getTablaProductos() {
        return tablaProductos;
    }

    public void setTablaProductos(TablaProductos tablaProductos) {
        this.tablaProductos = tablaProductos;
    }

    public TablaActual getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(TablaActual tablaActual) {
        this.tablaActual = tablaActual;
    }
}
