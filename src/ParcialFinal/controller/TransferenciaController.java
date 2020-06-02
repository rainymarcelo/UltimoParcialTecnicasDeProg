package ParcialFinal.controller;

import ParcialFinal.bsn.UsuarioBsn;
import ParcialFinal.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.security.PrivateKey;

public class TransferenciaController {

    @FXML
    private BorderPane transferencia;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCedulaReceptor;

    @FXML
    private TextField txtValor;

    UsuarioBsn usuarioBsn=new UsuarioBsn();

    public void transferir(){
        String cedula=txtCedula.getText();
        String cedulaReceptor=txtCedulaReceptor.getText();
        int valor=Integer.parseInt(txtValor.getText());

        if (!usuarioBsn.presente(cedula) || !usuarioBsn.presente(cedulaReceptor)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Consiganacion");
            alert.setHeaderText("Consigancion");
            alert.setContentText("alguna o las 2 cedulas ingresadas no esta en el sistema");
            alert.showAndWait();
            return;
        }else{
            String nombre="";
            String apellidos="";
            int saldo=0;

            for (Usuario usuario:usuarioBsn.buscarUsuario(cedula)){
                if (usuario.getCedula().equals(cedula)){
                    nombre=usuario.getNombre();
                    apellidos=usuario.getApellidos();
                    saldo=usuario.getSaldo();
                }
            }

            String nombreReceptor="";
            String apellidosReceptor="";
            int saldoReceptor=0;

            for (Usuario usuario:usuarioBsn.buscarUsuario(cedulaReceptor)){
                if (usuario.getCedula().equals(cedulaReceptor)){
                    nombreReceptor=usuario.getNombre();
                    apellidosReceptor=usuario.getApellidos();
                    saldoReceptor=usuario.getSaldo();
                }
            }

            saldo-=valor;
            saldoReceptor+=valor;
            if (saldo<0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Retiro");
                alert.setHeaderText("Retiro");
                alert.setContentText("La cantidad a retirar es mayor al saldo actual , el cual es de: "+(saldo+valor));
                alert.showAndWait();
                txtValor.clear();
            }
            else {
                Usuario usuario=new Usuario(cedula,nombre,apellidos,saldo);
                usuarioBsn.llevarCuentaUsuario(usuario);

                Usuario usuarioReceptor=new Usuario(cedulaReceptor,nombreReceptor,apellidosReceptor,saldoReceptor);
                usuarioBsn.llevarCuentaUsuario(usuarioReceptor);

                try {
                    BorderPane consignar = FXMLLoader
                            .load(getClass().getResource("../view/contenedor-principal.fxml"));
                    this.transferencia.setCenter(consignar);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public void mnuOperaciones(){
        try {
            BorderPane consignar = FXMLLoader
                    .load(getClass().getResource("../view/contenedor-principal.fxml"));
            this.transferencia.setCenter(consignar);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

