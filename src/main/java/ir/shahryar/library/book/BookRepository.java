package ir.shahryar.library.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    @Query(value = "{\"author\" : ?0}")
    ArrayList<Book> findAllBooksByAuthor(String author);

    Optional<Book> findByNameAndAuthor(String name, String author);

    boolean existsBookByNameAndAuthor(String name, String author);
}
