package ir.shahryar.library.Exception;

import javax.management.InstanceAlreadyExistsException;

public class UserAlreadyExistException extends InstanceAlreadyExistsException {
    public UserAlreadyExistException() {
        super("User already exist");
    }
}
