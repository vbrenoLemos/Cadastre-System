package Exceptions;

public class ValuesException extends Exception{
    public ValuesException(){
        super("The values passed are invalid");
    }
    public ValuesException(String message){
        super(message);
    }
}
