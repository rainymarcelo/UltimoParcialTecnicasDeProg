package ParcialFinal.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ContenedorPrincipaController {

    @FXML
    private BorderPane contenedorPrincipal;

    public void mnrRegistrarUsuario(){
        try{
            BorderPane registrarUsuario= FXMLLoader
                    .load(getClass().getResource("../view/registro.fxml"));
            this.contenedorPrincipal.setCenter(registrarUsuario);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void mnrConsignar(){
        try{
            BorderPane inicioUsuario=FXMLLoader
                    .load(getClass().getResource("../view/consignar.fxml"));
            this.contenedorPrincipal.setCenter(inicioUsuario);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void mnrRetirar(){
        try{
            BorderPane inicioUsuario=FXMLLoader
                    .load(getClass().getResource("../view/retirar.fxml"));
            this.contenedorPrincipal.setCenter(inicioUsuario);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void mnrTransferencia(){
        try{
            BorderPane inicioUsuario=FXMLLoader
                    .load(getClass().getResource("../view/transferencia.fxml"));
            this.contenedorPrincipal.setCenter(inicioUsuario);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }





}
