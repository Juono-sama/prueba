package com.example.vistas;

import com.example.modelos.CategoriasDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CategoriaForm extends Stage {
    private Scene escena;
    private HBox hBox;
    private TextField txtNameCat;
    private Button btnGuardar;
    private CategoriasDAO objCatDao;
    TableView<CategoriasDAO> tbvCategorias;

    public CategoriaForm(TableView<CategoriasDAO> tbvCat, CategoriasDAO objCatDAO) {
        this.tbvCategorias = tbvCat;
        objCatDao = objCatDAO == null ? new CategoriasDAO() : objCatDAO;
        CrearUI();
        escena = new Scene(hBox);
        this.setTitle("Gestion de Categorias");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNameCat = new TextField();
        txtNameCat.setText(objCatDao.getNomCategoria());
        txtNameCat.setPromptText("Nombre de la categoría");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarCategoria());
        hBox = new HBox();
        hBox = new HBox(txtNameCat, btnGuardar);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
    }

    private void guardarCategoria() {
        objCatDao.setNomCategoria(txtNameCat.getText());
        if(objCatDao.getIdCategoria() > 0){
            objCatDao.actualizar();
        }
        else{
            objCatDao.insertar();
        }
        tbvCategorias.setItems(objCatDao.listarCategoria());
        tbvCategorias.refresh();

        this.close();
    }
}