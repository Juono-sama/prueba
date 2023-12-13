package com.example.modelos;

import com.example.vistas.Producto;

import javax.swing.text.html.ImageView;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {
    int idProducto;
    String nombre;
    float precio;
    //String urlFinalImagen;
    ImageView imagen;
    int idCategoria;

    public ProductosDAO(){}
    public ProductosDAO(int idProducto, String nombre, float precio, ImageView imagen, int idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        //this.urlFinalImagen = urlFinalImagen;
        this.imagen = imagen;
        this.idCategoria = idCategoria;
    }

    public void insertar(){
        try{
            String query = "insert into Producto"+
                    "(nombre, precio, idCategoria) VALUES('"+this.nombre+", "+this.precio+", "+this.idCategoria+"')";
            //statement es una instruccion para preparar y ejecutar una instruccion en sql
            Statement smt = Conexion.conexion.createStatement();
            smt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /*public void actualizar(){
        try {
            String query = "UPDATE Producto SET nombre = '"+this.nombre+"' " +
                    "WHERE idCategoria = "+this.idCategoria;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
    public void eliminar(){
        try{
            String query = "DELETE FROM Producto WHERE idProducto = "+this.idCategoria;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Producto> crearYEnviarListaDeProductosConCategoria(String nombreCat){
        List<Producto> lista = new ArrayList<>();
        //nombreCat = "'"+nombreCat+"'";
        try{
            String query = "SELECT * FROM Producto WHERE idCategoria IN(SELECT idCategoria FROM tblCategorias WHERE nomCategoria = '"+nombreCat+"');";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                Producto producto = new Producto();
                producto.setIdProducto(res.getInt("idProducto"));
                producto.setNombre(res.getString("nombre"));
                producto.setPrecio(res.getFloat("precio"));
                //producto.setUrlFinalImagen(res.getString("imagen"));
                producto.setIdCategoria(res.getInt("idCategoria"));

                lista.add(producto);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    public List<Producto> crearYEnviarListaCompleta(){
        List<Producto> lista = new ArrayList<>();
        try{
            String query = "SELECT * FROM Producto";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                Producto producto = new Producto();
                producto.setIdProducto(res.getInt("idProducto"));
                producto.setNombre(res.getString("nombre"));
                producto.setPrecio(res.getFloat("precio"));
                //producto.setUrlFinalImagen(res.getString("imagen"));
                producto.setIdCategoria(res.getInt("idCategoria"));

                lista.add(producto);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /*public String getUrlImagen() {
        return urlFinalImagen;
    }*/

    /*public void setUrlImagen(String urlFinalImagen) {
        this.urlFinalImagen = urlFinalImagen;
    }*/

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
