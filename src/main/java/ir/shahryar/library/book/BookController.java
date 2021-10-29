package ir.shahryar.library.book;

import ir.shahryar.library.Exception.UserNotFoundException;
import ir.shahryar.library.data.Response;
import ir.shahryar.library.user.admin.AdminService;
import ir.shahryar.library.user.customer.Customer;
import ir.shahryar.library.user.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return bookService.getAll().toJson();
    }

    @GetMapping("{author}/{name}")
    public String showBook(@PathVariable String name, @PathVariable String author) {
        return bookService.get(name, author).toJson();
    }

    @GetMapping("{author}")
    public String getAllAuthor(@PathVariable String author) {
        return bookService.getAllAuthorsBook(author).toJson();
    }

    @PostMapping("add")
    public String addBook(@RequestHeader String id, @RequestBody Book book) {
        Response result;
        if (id == null) {
            result = new Response(adminService.adminProperties.properties.getProperty("UserDidNotLoginYetResponse"));
        } else {
            if (adminService.isEmpty()) {
                result = new Response(adminService.adminProperties.properties.getProperty("NoAdminInDatabaseResponse"));
            } else {
                if (adminService.isValidId(id)) {
                    String bookValidationResponse = bookService.validateBook(book);
                    if (bookValidationResponse.equals("Book not found")) {
                        bookService.saveNewBook(book);
                        result = new Response("This book added successfully!");
                    } else {
                        result = new Response(bookValidationResponse);
                    }
                } else {
                    result = new Response(adminService.adminProperties.properties.getProperty("UserNotFoundResponse"));
                }
            }
        }
        return result.toJson();
    }

    @PostMapping("rent")
    public String rentBook(@RequestHeader String id, @RequestBody Book book) {
        Response result;
        if (id == null) {
            result = new Response(customerService.customerProperties.properties.getProperty("UserDidNotLoginYetResponse"));
        } else {
            try {
                Customer customer = customerService.getById(id);
                String booksName = book.getName();
                String booksAuthor = book.getAuthor();
                String bookExistenceResponse = bookService.exists(booksName, booksAuthor);
                if (bookExistenceResponse.equals("Book already exist")) {
                    book = bookService.get(booksName, booksAuthor);
                    String msg = bookService.rentABook(customer.getNationalId(), book);
                    result = new Response(msg);
                } else {
                    result = new Response(bookExistenceResponse);
                }
            } catch (UserNotFoundException e) {
                result = new Response(customerService.customerProperties.properties.getProperty("UserNotFoundResponse"));
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
        if (bookExistenceResponse.equals("Book already exist")) {
            book = bookService.get(booksName, booksAuthor);
            String msg = bookService.removeRenter(book);
            result = new Response(msg);
        } else {
            result = new Response(bookExistenceResponse);
        }
        return result.toJson();
    }
}