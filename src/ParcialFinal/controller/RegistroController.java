package ParcialFinal.controller;

import ParcialFinal.bsn.UsuarioBsn;
import ParcialFinal.bsn.exception.ObjetoYaExisteException;
import ParcialFinal.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RegistroController {

    @FXML
    private BorderPane registro;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtSaldo;

    private UsuarioBsn usuarioBsn=new UsuarioBsn();

    @FXML
    public void initialize(){

        txtCedula.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCedula.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        txtNombre.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txtNombre.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        }));

        txtApellidos.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txtApellidos.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        }));

        txtSaldo.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtSaldo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));
    }

    public void btnRegistrar() throws ObjetoYaExisteException {
        String cedula=txtCedula.getText();
        String nombre=txtNombre.getText();
        String apellidos=txtApellidos.getText();
        String saldoString=txtSaldo.getText();

        boolean esValido=validarCampos(cedula,nombre,apellidos,saldoString);

        if (!esValido){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registro de usuario");
            alert.setHeaderText("Registro de usuario");
            alert.setContentText("Diligencie todos los campos");
            alert.showAndWait();
            return;
        }

        int saldo=Integer.parseInt(saldoString);

        if (saldo<=0 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registro de usuario");
            alert.setHeaderText("Registro de usuario");
            alert.setContentText("El saldo es invalido, por favor de un valor mayor a 0");
            alert.showAndWait();
        }
        else {
            Usuario usuario=new Usuario(cedula,nombre,apellidos,saldo);
            usuarioBsn.registrarUsuario(usuario);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro de usuario");
            alert.setHeaderText("Registro de usuario");
            alert.setContentText("Registro exitoso");
            alert.showAndWait();

            try{
                BorderPane principal= FXMLLoader
                        .load(getClass().getResource("../view/contenedor-principal.fxml"));
                this.registro.setCenter(principal);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

    }

    private Boolean validarCampos(String... campos) {
        for (int i = 0; i < campos.length; i++) {
            if (campos[i] == null || "".equals(campos[i])) {
                return false;
            }
        }
        return true;
    }

    private void limpiarCampos(){
        txtSaldo.clear();
        txtCedula.clear();
        txtNombre.clear();
        txtApellidos.clear();
    }

    public void mnuPrincipal(){
        try{
            BorderPane principal= FXMLLoader
                    .load(getClass().getResource("../view/contenedor-principal.fxml"));
            this.registro.setCenter(principal);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
