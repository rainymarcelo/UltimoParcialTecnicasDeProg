package ParcialFinal.dao.exception;

public class LlaveDuplicadaException extends Exception{

    public LlaveDuplicadaException(String llave){
        super(String.format("ya existe un registro con esa llave: %s",llave));
    }
}
