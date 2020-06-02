package ParcialFinal.dao.impl;

import ParcialFinal.dao.UsuarioDao;
import ParcialFinal.dao.exception.LlaveDuplicadaException;
import ParcialFinal.model.Usuario;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;

public class UsuarioDaoNio implements UsuarioDao {

    private final static String NOMBRE_ARCHIVO = "usuarios";
    private final static Path ARCHIVO = Paths.get(NOMBRE_ARCHIVO);
    private final static String FIELD_SEPARATOR = ",";
    private final static String RECORD_SEPARATOR = System.lineSeparator();

    public UsuarioDaoNio() {
        if (!Files.exists(ARCHIVO)) {
            try {
                Files.createFile(ARCHIVO);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


    @Override
    public void registrarUsuario(Usuario usuario) throws LlaveDuplicadaException {
        Optional<Usuario> usuarioOptional = this.consultarUsuarioPorId(usuario.getCedula());
        if (usuarioOptional.isPresent()) {
            throw new LlaveDuplicadaException(usuario.getCedula());
        }
        String usuarioString = parseUsuario2String(usuario);
        byte[] datosRegistro = usuarioString.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Boolean presencia(String cedula) {
        try (Stream<String> stream = Files.lines(ARCHIVO)) {
            Optional<String> usuarioString = stream
                    .filter(usuario -> cedula.equals(usuario.split(",")[0]))
                    .findFirst();
            if (usuarioString.isPresent()) {
                return true;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }


    @Override
    public void llevarCuentaUsuario(Usuario usuario) {
        String usuarioString = parseUsuario2String(usuario);
        byte[] datosRegistro = usuarioString.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(datosRegistro);
        try (FileChannel fileChannel = FileChannel.open(ARCHIVO, APPEND)) {
            fileChannel.write(byteBuffer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private String parseUsuario2String(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append(usuario.getCedula()).append(FIELD_SEPARATOR)
                .append(usuario.getNombre()).append(FIELD_SEPARATOR)
                .append(usuario.getApellidos()).append(FIELD_SEPARATOR)
                .append(usuario.getSaldo()).append(RECORD_SEPARATOR);
        return sb.toString();
    }

    @Override
    public Optional<Usuario> consultarUsuarioPorId(String id) {
        try (Stream<String> stream = Files.lines(ARCHIVO)) {
            Optional<String> usuarioString = stream
                    .filter(usuario -> id.equals(usuario.split(",")[0]))
                    .findFirst();
            if (usuarioString.isPresent()) {
                return Optional.of(parseUsuario2Object(usuarioString.get()));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> buscarUsuario(String id) {
        List<Usuario> usuarios = new ArrayList<>();
        try (Stream<String> stream = Files.lines(ARCHIVO)) {
            stream.forEach(usuarioString -> usuarios.add(parseUsuario2Object(usuarioString)));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return usuarios;
    }


    private Usuario parseUsuario2Object(String usuariosString) {
        String[] datosUsuario = usuariosString.split(FIELD_SEPARATOR);
        //todo:validar que el tamaño del arreglo sea de 4 elementos
        Usuario usuario = new Usuario(datosUsuario[0],
                datosUsuario[1],
                datosUsuario[2],
                Integer.parseInt(datosUsuario[3]));
        return usuario;
    }
}
