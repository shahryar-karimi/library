package ir.shahryar.library.book;

import ir.shahryar.library.Exception.BookNotFoundException;
import ir.shahryar.library.Exception.EmptyListException;
import ir.shahryar.library.util.props.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    public MyProperties properties;

    public void saveNewBook(Book book) {
        bookRepository.save(book);
    }

    public ArrayList<Book> getAll() throws EmptyListException {
        ArrayList<Book> all = (ArrayList<Book>) bookRepository.findAll();
        if (all.isEmpty()) throw new EmptyListException();
        return all;
    }

    public ArrayList<Book> getAllAuthorsBook(String author) throws EmptyListException {
        ArrayList<Book> authorsBooks = bookRepository.findAllBooksByAuthor(author);
        if (authorsBooks.isEmpty()) throw new EmptyListException();
        return authorsBooks;
    }

    public String rentABook(String renterNationalId, Book book) {
        book.setRenterNationalId(renterNationalId);
        return "book rented to " + renterNationalId;
    }

    public String removeRenter(Book book) {
        book.setRenterNationalId(null);
        return "Book '" + book.getName() + "' is free";
    }

    public Book get(String name, String author) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findByNameAndAuthor(name, author);
        if (optionalBook.isEmpty()) throw new BookNotFoundException();
        return optionalBook.get();
    }

    public String exists(String name, String author) {
        String result;
        if (name == null)
            result = properties.getProperty("NotEnoughInfo.booksName");
        else if (author == null)
            result = properties.getProperty("NotEnoughInfo.booksAuthor");
        else if (bookRepository.existsBookByNameAndAuthor(name, author))
            result = properties.getProperty("BookAlreadyExist");
        else result = properties.getProperty("BookNotFound");
        return result;
    }

    public String validateBook(Book book) {
        String result = exists(book.getName(), book.getAuthor());
        if (result.equals(properties.getProperty("BookNotFound")) &&
                book.getBody() == null) result = properties.getProperty("NotEnoughInfo.booksBody");
        return result;
    }
}
