package com.example.modelos;

import com.example.vistas.Categoria;

import java.sql.ResultSet;
import java.sql.Statement;

public class ClientesDAO {
    private int idCliente;
    private String nombre;

    public void insertarCliente(){// inserta un cliente en la base de datos
        try{
            String query = "INSERT INTO Cliente(nombre) VALUES('"+this.nombre+"')"; //instruccion que quiero ejecutar en sql
            //statement es una instruccion para preparar y ejecutar una instruccion en sql
            Statement smt = Conexion.conexion.createStatement();
            smt.executeUpdate(query); //ejecuta la instruccion en sql
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getIdUltimoCliente(){ //Obtiene el ultimo id del cliente que se haya insertado en la base de datos
        int idUltimoCliente=0;
        try{
            String query = "select idCliente from Cliente order by idCliente desc Limit 1;";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            res.next();

            if(!res.wasNull()) {
                idUltimoCliente = res.getInt("idCliente");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return idUltimoCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
