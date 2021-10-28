package ir.shahryar.library.book;

import ir.shahryar.library.Exception.BookNotFoundException;
import ir.shahryar.library.Exception.EmptyListException;
import ir.shahryar.library.data.Response;
import ir.shahryar.library.util.MyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findByName(String name) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findByName(name);
        if (optionalBook.isEmpty()) throw new BookNotFoundException();
        return optionalBook.get();
    }

    public MyList<Book> getAll() throws EmptyListException {
        MyList<Book> all = (MyList<Book>) bookRepository.findAll();
        if (all.isEmpty()) throw new EmptyListException();
        return all;
    }

    public MyList<Book> getAllAuthorsBook(String author) throws EmptyListException {
        MyList<Book> authorsBooks = bookRepository.findAllBooksByAuthor(author);
        if (authorsBooks.isEmpty()) throw new EmptyListException();
        return authorsBooks;
    }

    public String rentABook(String renterNationalId, Book book) throws BookNotFoundException {
        book = get(book.getName(), book.getAuthor());
        book.setRenterNationalId(renterNationalId);
        return "book rented to " + renterNationalId;
    }

    public String removeRenter(Book book) {
        book.setRenterNationalId(null);
        return new Response("Book '" + book.getName() + "' is free").toJson();
    }

    public Book get(String name, String author) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findByNameAndAuthor(name, author);
        if (optionalBook.isEmpty()) throw new BookNotFoundException();
        return optionalBook.get();
    }

    public boolean exists(String name, String author) {
        return bookRepository.existsBookByNameAndAuthor(name, author);
    }

    public Response isValidBookWrapper(BookWrapper bookWrapper) {
        Response result;
        if (bookWrapper.getBook() == null) result = new Response("Input book");
        else {
            Book book = bookWrapper.getBook();
            if (book.getName() == null) result = new Response("Input books name");
            else if (book.getAuthor() == null) result = new Response("Input books author");
            else {
                if (exists(book.getName(), book.getAuthor())) {
                    result = new Response("Ok");
                } else {
                    result = new Response("Book not found");
                }
            }
        }
        return result;
    }
}
