package model;


//Clase para el manejode la excepción personalizada RepositoryException

public class RepositoryException extends Exception{
    public RepositoryException(){};
    public RepositoryException(Throwable cause){
        super(cause);
    
    }  
    public RepositoryException(String message){
        super(message);
    } 

    public RepositoryException(String message, Throwable cause){
        super(message, cause);
    }
}
