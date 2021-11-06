package ir.shahryar.library.book;

import ir.shahryar.library.Exception.EmptyListException;
import ir.shahryar.library.Exception.UserNotFoundException;
import ir.shahryar.library.data.Response;
import ir.shahryar.library.user.admin.AdminService;
import ir.shahryar.library.user.customer.Customer;
import ir.shahryar.library.user.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String showAllBooks() {
        String result;
        try {
            ArrayList<Book> books = bookService.getAll();
            ArrayList<String> booksName = new ArrayList<>();
            books.forEach(book -> booksName.add('\'' + book.getName() + "' by '" + book.getAuthor() + '\''));
            result = booksName.toString();
        } catch (EmptyListException e) {
            result = bookService.properties.getProperty("EmptyBookList");
        }
        return result;
    }

    @GetMapping("{author}/{name}")
    public String showBook(@PathVariable String name, @PathVariable String author) {
        return bookService.get(name, author).toJson();
    }

    @GetMapping("{author}")
    public String getAllAuthor(@PathVariable String author) {
        ArrayList<Book> books = bookService.getAllAuthorsBook(author);
        ArrayList<String> booksName = new ArrayList<>();
        books.forEach(book -> booksName.add('\'' + book.getName() + "' by '" + book.getAuthor() + '\''));
        return booksName.toString();
    }

    @PostMapping("add")
    public String addBook(@RequestHeader String id, @RequestBody Book book) {
        Response result;
        if (id == null) {
            result = new Response(adminService.properties.getProperty("UserDidNotLoginYet"));
        } else {
            if (adminService.isEmpty()) {
                result = new Response(adminService.properties.getProperty("NoAdminInDatabase"));
            } else {
                if (adminService.isValidId(id)) {
                    String bookValidationResponse = bookService.validateBook(book);
                    if (bookValidationResponse.equals(bookService.properties.getProperty("BookNotFound"))) {
                        bookService.saveNewBook(book);
                        result = new Response(bookService.properties.getProperty("Response.add.book"));
                    } else {
                        result = new Response(bookValidationResponse);
                    }
                } else {
                    result = new Response(adminService.properties.getProperty("UserNotFoundResponse"));
                }
            }
        }
        return result.toJson();
    }

    @PostMapping("rent")
    public String rentBook(@RequestHeader String id, @RequestBody Book book) {
        Response result;
        if (id == null) {
            result = new Response(customerService.properties.getProperty("UserDidNotLoginYet"));
        } else {
            try {
                Customer customer = customerService.getById(id);
                String booksName = book.getName();
                String booksAuthor = book.getAuthor();
                String bookExistenceResponse = bookService.exists(booksName, booksAuthor);
                if (bookExistenceResponse.equals(bookService.properties.getProperty("BookAlreadyExist"))) {
                    book = bookService.get(booksName, booksAuthor);
                    String msg = bookService.rentABook(customer.getNationalId(), book);
                    result = new Response(msg);
                } else {
                    result = new Response(bookExistenceResponse);
                }
            } catch (UserNotFoundException e) {
                result = new Response(bookService.properties.getProperty("UserNotFoundResponse"));
            }
        }
        return result.toJson();
    }

    @PostMapping("giveBack")
    public String giveBackBook(@RequestBody Book book) {
        Response result;
        String booksName = book.getName();
        String booksAuthor = book.getAuthor();
        String bookExistenceResponse = bookService.exists(booksName, booksAuthor);
        if (bookExistenceResponse.equals(bookService.properties.getProperty("BookAlreadyExist"))) {
            book = bookService.get(booksName, booksAuthor);
            String msg = bookService.removeRenter(book);
            result = new Response(msg);
        } else {
            result = new Response(bookExistenceResponse);
        }
        return result.toJson();
    }
}