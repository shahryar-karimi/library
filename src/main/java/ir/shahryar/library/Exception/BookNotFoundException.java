package ir.shahryar.library.Exception;

import java.util.NoSuchElementException;

public class BookNotFoundException extends NoSuchElementException {
    public BookNotFoundException() {
        super("Book not found");
    }
}
