package com.example.modelos;

import java.sql.ResultSet;
import java.sql.Statement;

public class CarritoDao {
    int idCliente;
    int idCarrito;
    public CarritoDao(int idCliente, int idCarrito) {
        this.idCliente = idCliente;
        this.idCarrito = idCarrito;
    }
    public CarritoDao() {
    }

    public void insertarCarrito(int idCliente, int idCarrito){
        this.idCliente = idCliente; this.idCarrito = idCarrito;
        try{
            String query = "INSERT INTO Carrito(idCliente, idCarrito)" +
                    " VALUES("+idCliente+","+idCarrito+")";
            //statement es una instruccion para preparar y ejecutar una instruccion en sql
            Statement smt = Conexion.conexion.createStatement();

            smt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getIdUltimoCarrito(){
        int idUltimoCarrito=0;
        try{
            String query = "select idCarrito from Carrito order by idCarrito desc Limit 1;";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            res.next();

            if(!res.wasNull()){
                idUltimoCarrito = res.getInt("idCarrito");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return idUltimoCarrito;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }
}
