package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriasDAO {
    private int idCategoria;
    private String nomCategoria;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }

    public void insertar(){
        try{
            String query = "insert into tblCategorias"+
                    "(nomCategoria) VALUES('"+this.nomCategoria+"')";
            //statement es una instruccion para preparar y ejecutar una instruccion en sql
            Statement smt = Conexion.conexion.createStatement();
            smt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void actualizar(){
        try {
            String query = "UPDATE tblCategorias SET nomCategoria = '"+this.nomCategoria+"' " +
                    "WHERE idCategoria = "+this.idCategoria;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void eliminar(){
        try{
            String query = "DELETE FROM tblCategorias WHERE idCategoria = "+this.idCategoria;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<CategoriasDAO> listarCategoria(){
        //Array le tienes que decir cuántos puede almacenar, ArryaList puede crecer indefinidamente sin decirle cuántos elementos puede tener como máximo.
        ObservableList<CategoriasDAO> listCat = FXCollections.observableArrayList();
        CategoriasDAO objC;
        try{
            String query = "SELECT * FROM tblCategorias";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                objC=new CategoriasDAO();
                objC.idCategoria = res.getInt("idCategoria");
                objC.nomCategoria = res.getString("nomCategoria");
                listCat.add(objC);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return listCat; //Lo va a mandar a un TableView para que pueda mostrar los datos de los objetos CategoriasDAO en una tabla visible.
    }
}