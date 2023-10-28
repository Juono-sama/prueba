package com.example.componentes;

import com.example.modelos.CategoriasDAO;
import com.example.vistas.CategoriaForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCell extends TableCell<CategoriasDAO,String> {
    private Button btnCelda;
    int opc;
    private TableView<CategoriasDAO> tbvCategorias;
    private CategoriasDAO objCat;
    public ButtonCell(int opc){
        this.opc = opc;
        String txtBtn = this.opc ==1 ? "Editar" : "Eliminar";
        btnCelda = new Button(txtBtn);
        btnCelda.setOnAction(event -> accionBoton());
    }

    private void accionBoton(){
        tbvCategorias = ButtonCell.this.getTableView();
        objCat = tbvCategorias.getItems().get(ButtonCell.this.getIndex());
        if(this.opc == 1){
            new CategoriaForm(tbvCategorias, objCat);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Topicos Avanzados de Programacion");
            alert.setHeaderText("Confirmación del sistema");
            alert.setContentText("¿Deseas eliminar la categoria?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                objCat.eliminar();
                tbvCategorias.setItems(objCat.listarCategoria());
                tbvCategorias.refresh();
            }
            else{}
        }

    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if ( !empty ){
            this.setGraphic(btnCelda);
        }
    }
}
