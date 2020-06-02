package ParcialFinal.bsn;

import ParcialFinal.bsn.exception.ObjetoYaExisteException;
import ParcialFinal.dao.UsuarioDao;
import ParcialFinal.dao.exception.LlaveDuplicadaException;
import ParcialFinal.dao.impl.UsuarioDaoNio;
import ParcialFinal.model.Usuario;
import javafx.fxml.FXML;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class UsuarioBsn {

    private UsuarioDao usuarioDao;

    public UsuarioBsn(){this.usuarioDao=new UsuarioDaoNio(); }

    public void registrarUsuario(Usuario usuario) throws ObjetoYaExisteException{
        try{
            this.usuarioDao.registrarUsuario(usuario);
        }catch (LlaveDuplicadaException lde){
            JOptionPane.showMessageDialog(null,lde);
            throw new ObjetoYaExisteException((String.format("El usuario con cedula %s ya esta registrado",usuario.getCedula())));
        }
    }

    public List<Usuario> buscarUsuario(String cedula){
        return this.usuarioDao.buscarUsuario(cedula);
    }

    public void llevarCuentaUsuario(Usuario usuario){
        usuarioDao.llevarCuentaUsuario(usuario);
    }

    public Boolean presente(String cedula){
        return usuarioDao.presencia(cedula);
    }
}
