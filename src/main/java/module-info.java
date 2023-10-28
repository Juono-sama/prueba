module com.example.prueba_parcial_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;


    opens com.example.prueba_parcial_1 to javafx.fxml;
    exports com.example.prueba_parcial_1;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    opens com.example.modelos;
    //requires mariadb.java.client;
}