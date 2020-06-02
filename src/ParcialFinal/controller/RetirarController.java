package ParcialFinal.controller;

import ParcialFinal.bsn.UsuarioBsn;
import ParcialFinal.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RetirarController {

    @FXML
    private BorderPane retirar;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtValor;

    UsuarioBsn usuarioBsn=new UsuarioBsn();


    public void mnuOperaciones(){
        try {
            BorderPane consignar = FXMLLoader
                    .load(getClass().getResource("../view/contenedor-principal.fxml"));
            this.retirar.setCenter(consignar);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public void retirar(){
        String cedula=txtCedula.getText();
        int valor=Integer.parseInt(txtValor.getText());

        if (!usuarioBsn.presente(cedula)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Consiganacion");
            alert.setHeaderText("Consigancion");
            alert.setContentText("La cedula ingresada no esta en el sistema");
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

            saldo-=valor;
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
                try {
                    BorderPane consignar = FXMLLoader
                            .load(getClass().getResource("../view/contenedor-principal.fxml"));
                    this.retirar.setCenter(consignar);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public void initialize(){
        txtCedula.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCedula.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        txtValor.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtValor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));
    }
}
