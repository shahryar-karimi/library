package ir.shahryar.library.book;

import ir.shahryar.library.util.MyList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByName(String name);

    @Query(value = "{\"author\" : ?0}")
    MyList<Book> findAllBooksByAuthor(String author);

    Optional<Book> findByNameAndAuthor(String name, String author);

    boolean existsBookByNameAndAuthor(String name, String author);
}
