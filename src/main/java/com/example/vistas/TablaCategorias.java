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

    //Crea una cuadricula de botones, dando la impresion de ser una tabla
    public void crearTablaCategorias() throws FileNotFoundException {
        /*Crea un objeto DAO que se relaciona con la tabla tblCategorias de la base de datos
        y le decimos que nos dé todos los datos que tenga*/
        CategoriasDAO dao = new CategoriasDAO();
        listaCategorias = dao.crearYEnviarLista();

        //Variables para generar la cuadricula que muestra la tabla de categorias
        int numCategorias, ancho=180, alto=180;
        String categoriaAux, productoAux;
        ImageView imageViewProductoAux;
        numCategorias = listaCategorias.size();
        argBtnTabla = new Button[numCategorias+1][2]; //Crea los botones que irán en la cuadrícula, osea en la tabla de Categorias

        //Crea la columna inicial de botones, ya que se me hizo fácil hacer cuadriculas con botones
        argBtnTabla[0][0] = new Button("Nombre de la \ncategoria de \nproductos");
        argBtnTabla[0][0].setPrefSize(ancho,alto-90);
        argBtnTabla[0][0].setFont(new Font("Arial", 20));
        argBtnTabla[0][1] = new Button("Imagen del \ntipo de \nproductos");
        argBtnTabla[0][1].setPrefSize(ancho,alto+90);
        argBtnTabla[0][1].setFont(new Font("Arial", 20));
        //Agrega los botones a la cuadricula,
        gridTablaCategorias.add(argBtnTabla[0][0],0,0);
        gridTablaCategorias.add(argBtnTabla[0][1],0,1);

        //Crea cada botones para cada categoria para dar forma a la tabla.
        for(int i = 1; i < numCategorias+1; i++) {
            categoriaAux = listaCategorias.get(i-1).getCategoria(); //Variables auxiliares
            productoAux = listaCategorias.get(i-1).getProducto();

            //Crea un botón con el nombre de la categoria, al presionarlo no hace nada
            argBtnTabla[i][0] = new Button(categoriaAux);
            argBtnTabla[i][0].setPrefSize(ancho,alto-90);
            argBtnTabla[i][0].setFont(new Font("Arial", 16));
            gridTablaCategorias.add(argBtnTabla[i][0], i, 0); //Añade el boton a la cuadricula

            //Crea un botón con una imágen representantiva de la categoria, al presionarlo cambia a la tabla de esa categoria
            argBtnTabla[i][1] = new Button();
            argBtnTabla[i][1].setPrefSize(ancho,alto+90);
            imageViewProductoAux = new ImageView(new Image(new FileInputStream("src/main/java/com/example/img/r/"+productoAux+".jpg")));
            imageViewProductoAux.setFitHeight(alto+60);
            imageViewProductoAux.setFitWidth(ancho-20);
            argBtnTabla[i][1].setGraphic(imageViewProductoAux);

            //Le da la accion de cambiar a la tabla de productos de la categoria que muestre la imagen
            String finalCategoriaAux = categoriaAux;
            argBtnTabla[i][1].setOnAction((event) -> {
                try {
                    tablaActual.cambiarATablaDeProductos(finalCategoriaAux); //Método que cambia la tabla
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            gridTablaCategorias.add(argBtnTabla[i][1], i, 1); //Añade el botón a la cuadricula
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
