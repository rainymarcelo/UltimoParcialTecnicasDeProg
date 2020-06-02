package ParcialFinal.dao;

import ParcialFinal.dao.exception.LlaveDuplicadaException;
import ParcialFinal.model.Usuario;
import java.util.*;

import java.util.Optional;

public interface UsuarioDao {
    void registrarUsuario(Usuario usuario) throws LlaveDuplicadaException;

    void llevarCuentaUsuario(Usuario usuario);

    Optional<Usuario> consultarUsuarioPorId(String id);

    List<Usuario> buscarUsuario(String id);

    Boolean presencia(String cedula);

}
