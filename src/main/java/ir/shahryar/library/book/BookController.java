package ir.shahryar.library.book;

import ir.shahryar.library.Exception.BookNotFoundException;
import ir.shahryar.library.Exception.UserNotFoundException;
import ir.shahryar.library.data.Response;
import ir.shahryar.library.user.admin.AdminService;
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

    @GetMapping("{name}/{author}")
    public String showBook(@PathVariable String name, @PathVariable String author) {
        return bookService.get(name, author).toJson();
    }

    @GetMapping("{author}/getAll")
    public String getAllAuthor(@PathVariable String author) {
        return bookService.getAllAuthorsBook(author).toJson();
    }

    @PostMapping("add")
    public String addBook(@RequestBody BookWrapper bookWrapper) {
        String result = bookService.isValidBookWrapper(bookWrapper).getMessage();
        if (result.equals("Ok")) {
            Book book = bookWrapper.getBook();
            if (adminService.isEmpty()) {
                result = new Response("Request not allowed\nAdmin not found").toJson();
            } else {
                if (bookService.findByName(book.getName()) != null) {
                    result = new Response("This book already exist").toJson();
                } else {
                    result = bookService.save(book).toJson();
                }
            }
        }
        return result;
    }

    @PostMapping("rent")
    public String rentBook(@RequestBody BookWrapper bookWrapper) {
        String result = bookService.isValidBookWrapper(bookWrapper).getMessage();
        if (result.equals("Ok")) {
            Book book = bookWrapper.getBook();
            try {
                book = bookService.get(book.getName(), bookWrapper.getBook().getAuthor());
                if (book.getRenterNationalId() == null) {
                    String nationalId = bookWrapper.getCustomerNationalId();
                    if (nationalId != null) {
                        if (customerService.exists(nationalId)) {
                            book.setRenterNationalId(nationalId);
                            result = new Response(book + " is rented to " + nationalId).toJson();
                        } else {
                            result = new Response("National id not found").toJson();
                        }
                    } else {
                        result = new Response("Input national id").toJson();
                    }
                } else {
                    result = new Response("book has rented to: " + book.getRenterNationalId()).toJson();
                }
            } catch (BookNotFoundException | UserNotFoundException e) {
                result = new Response(e.getMessage()).toJson();
            }
        }
        return result;
    }
}