package com.example.vistas;

import com.example.componentes.ButtonCell;
import com.example.modelos.CategoriasDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import java.io.FileNotFoundException;

public class Restaurante extends Stage {
    private TableView<CategoriasDAO> tbvCategorias;
    private Button btnAgregar;
    //Parte que yo hice
    Scene escena;
    private VBox vBox, vTablaCarritoCliente, vCarrito, vCliente, vTabla;
    private HBox hPrincipal,  hCarritoYCliente;
    private CategoriasDAO categoriasDAO;
    private Label lblTablaActual, lblTablaCarrito, lblAnuncioCarrito;
    private TablaCategorias tablaCategorias;
    private TablaProductos tablaProductos;
    private TablaActual tablaActual;
    private TablaCarrito tablaCarrito;
    private FormularioCliente formularioCliente;
    private Carrito carrito;
    //public List<Categorias> categoriasList;

    public Restaurante() throws FileNotFoundException {
        crearUI();
        Panel panel = new Panel("This is the title");
        panel.getStyleClass().add("panel-primary");                            //(2)
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
        //Button button = new Button("Hello BootstrapFX");
        //button.getStyleClass().setAll("btn","btn-danger");                     //(2)
        //content.setCenter(button);
        content.setCenter(vBox);
        panel.setBody(content);

        /*Scene scene = new Scene(panel);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());       //(3)

        this.setTitle("BootstrapFX");
        this.setScene(scene);
        this.sizeToScene();
        this.show();*/

        //Parte que yo hice
        escena = new Scene (hPrincipal, 1500, 790);
        this.setTitle("Restaurante");
        this.setScene(escena);
        this.show();

    }

    private void crearUI() throws FileNotFoundException {
        categoriasDAO = new CategoriasDAO();
        tbvCategorias = new TableView<CategoriasDAO>();
        //CrearTable();

        btnAgregar = new Button("Agregar");
        btnAgregar.getStyleClass().setAll("btn","btn-danger");
        btnAgregar.setOnAction((event) -> new CategoriaForm(tbvCategorias, null));
        vBox = new VBox(tbvCategorias, btnAgregar);

        //Parte que yo hice
        crearTablas();
        unirTablasYLabels();

        vTabla = tablaActual.getvBox();
        vTabla.setSpacing(10);

        vCarrito = new VBox(lblAnuncioCarrito, lblTablaCarrito);
        vCliente = formularioCliente.getvBoxFormulario(); //Aqui pondré el formulario del cliente, el boton de comprar y reiniciar compra

        hCarritoYCliente = new HBox(vCarrito, vCliente);
        hCarritoYCliente.setSpacing(10);

        vTablaCarritoCliente = new VBox(vTabla, hCarritoYCliente);
        vTablaCarritoCliente.setSpacing(10);

        hPrincipal = new HBox(vTablaCarritoCliente);
        hPrincipal.setPadding(new Insets(10));
    }

    public void crearTablas() {
        //Crea las tablas que usaré (la actual, la de productos y la de categorias)
        tablaActual = new TablaActual();
        tablaProductos = new TablaProductos();
        tablaCategorias = new TablaCategorias();
        tablaCarrito = new TablaCarrito();
        formularioCliente = new FormularioCliente();
    }

    public void unirTablasYLabels() throws FileNotFoundException {
        //Une la tabla actual con las de productos y categoria
        tablaActual.setTablaProductos(tablaProductos);
        tablaActual.setTablaCategorias(tablaCategorias);
        tablaActual.setTablaCarrito(tablaCarrito);
        tablaActual.setFormularioCliente(formularioCliente);
        //tablaActual.setFormularioCliente(formularioCliente);

        tablaProductos.setTablaActual(tablaActual);
        tablaProductos.setFormularioCliente(formularioCliente);
        tablaCategorias.setTablaActual(tablaActual);
        tablaCarrito.setFormularioCliente(formularioCliente);

        formularioCliente.setTablaCarrito(tablaCarrito);
        formularioCliente.setTablaActual(tablaActual);


        //Le dice al lblTablaActual que dependerá de lo que pase en en labelTablaActual del objeto tablaActual, así lo que haga en esa clase cambiaré aquí
        lblTablaActual = tablaActual.getLabelTablaActual();
        tablaActual.generarTablaCategoriasInicial();
        tablaActual.cambiarATablaDeCategorias();

        //Le dice al lblTablaCarrito que dependerá de lo que pase en en labelTablaCarrito del objeto tablaCarrito, así lo que haga en esa clase cambiaré aquí
        lblTablaCarrito = tablaCarrito.getLabelTablaCarrito();
        tablaCarrito.generarTablaCarritoInicial();
        lblAnuncioCarrito = new Label("Carrito:"); lblAnuncioCarrito.setFont(new Font("Arial", 16));


    }

    private void CrearTable() throws FileNotFoundException {
        TableColumn<CategoriasDAO,Integer> tbcIdCat = new TableColumn<>("ID");
        tbcIdCat.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<CategoriasDAO,String> tbcNomCat = new TableColumn<>("Nombre");
        tbcNomCat.setCellValueFactory(new PropertyValueFactory<>("nomCategoria"));
        //tbcNomCat.setCellValueFactory();
        //tbcNomCat.setGraphic(new ImageView(new Image(new FileInputStream(new PropertyValueFactory<>("nomCategoria")))));


        TableColumn<CategoriasDAO,String> tbcEditar = new TableColumn<CategoriasDAO,String>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> categoriasDAOStringTableColumn) {
                        try {
                            return new ButtonCell(1);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        TableColumn<CategoriasDAO,String> tbcEliminar = new TableColumn<CategoriasDAO,String>("Eliminar");
        //tbcEliminar.setCellFactory
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> categoriasDAOStringTableColumn) {
                        try {
                            //Label
                            return new ButtonCell(2);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );



        tbvCategorias.getColumns().addAll(tbcIdCat, tbcNomCat, tbcEditar, tbcEliminar);
        tbvCategorias.setItems(categoriasDAO.listarCategoria());

    }
}
