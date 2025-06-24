package borisov.producer.exception;

public class UserEmailAlreadyExistException extends RuntimeException{
    public UserEmailAlreadyExistException() {
        super("Email уже используется");
    }
}
