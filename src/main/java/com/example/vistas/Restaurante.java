package com.example.vistas;

import com.example.componentes.ButtonCell;
import com.example.modelos.CategoriasDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class Restaurante extends Stage {

    private VBox vBox;
    private TableView<CategoriasDAO> tbvCategorias;
    private Button btnAgregar;
    private CategoriasDAO categoriasDAO;
    public Restaurante(){
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

        Scene scene = new Scene(panel);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());       //(3)

        this.setTitle("BootstrapFX");
        this.setScene(scene);
        this.sizeToScene();
        this.show();
    }

    private void crearUI(){
        categoriasDAO = new CategoriasDAO();
        tbvCategorias = new TableView<CategoriasDAO>();
        CrearTable();

        btnAgregar = new Button("Agregar");
        btnAgregar.getStyleClass().setAll("btn","btn-danger");
        btnAgregar.setOnAction((event) -> new CategoriaForm(tbvCategorias, null));
        vBox = new VBox(tbvCategorias, btnAgregar);
    }

    private void CrearTable(){
        TableColumn<CategoriasDAO,Integer> tbcIdCat = new TableColumn<>("ID");
        tbcIdCat.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<CategoriasDAO,String> tbcNomCat = new TableColumn<>("Nombre");
        tbcNomCat.setCellValueFactory(new PropertyValueFactory<>("nomCategoria"));

        TableColumn<CategoriasDAO,String> tbcEditar = new TableColumn<CategoriasDAO,String>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> categoriasDAOStringTableColumn) {
                        return new ButtonCell(1);
                    }
                }
        );

        TableColumn<CategoriasDAO,String> tbcEliminar = new TableColumn<CategoriasDAO,String>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> categoriasDAOStringTableColumn) {
                        return new ButtonCell(2);
                    }
                }
        );



        tbvCategorias.getColumns().addAll(tbcIdCat, tbcNomCat, tbcEditar, tbcEliminar);
        tbvCategorias.setItems(categoriasDAO.listarCategoria());

    }
}
