package com.example.modelos;

import java.sql.Statement;

public class ProductoCarritoDao {
    int idCliente;
    int idCarrito;
    int idProducto;
    int cantidad;

    //Inserta el registro a la tabla proCarrito con datos que ya tenga el objeto
    public void insertarProductoCarrito(){
        try{
            String query = "INSERT INTO proCarrito(idCliente, idCarrito, idProducto, cantidad)" +
                    " VALUES("+this.idCliente+","+this.idCarrito+", "+this.idProducto+", "+this.cantidad+")";
            //statement es una instruccion para preparar y ejecutar una instruccion en sql
            Statement smt = Conexion.conexion.createStatement();
            smt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Inserta el registro a la tabla proCarrito con datos que le mande de otra clase
    public void insertarProductoCarrito(int idCliente, int idCarrito, int idProducto, int cantidad){
        this.idCliente = idCliente; this.idCarrito = idCarrito;
        this.idProducto = idProducto; this.cantidad = cantidad;

        try{
            String query = "INSERT INTO proCarrito(idCliente, idCarrito, idProducto, cantidad)" +
                    " VALUES("+this.idCliente+","+this.idCarrito+", "+this.idProducto+", "+this.cantidad+")";
            //statement es una instruccion para preparar y ejecutar una instruccion en sql
            Statement smt = Conexion.conexion.createStatement();
            smt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ProductoCarritoDao(int idCliente, int idCarrito, int idProducto, int cantidad) {
        this.idCliente = idCliente;
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public ProductoCarritoDao() {
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

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
