package ir.shahryar.library.Exception;

import java.io.IOException;

public class TimeAlreadySetException extends IOException {
    public TimeAlreadySetException() {
        super("Time already set exception");
    }
}
