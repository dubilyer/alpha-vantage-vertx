package exceptions;

public class ExposerException extends RuntimeException{
    String message;

    public ExposerException(String message) {
        super(message);
    }
}
