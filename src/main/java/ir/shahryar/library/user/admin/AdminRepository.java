package ir.shahryar.library.user.admin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

    Optional<Admin> findByUsername(String username);

    boolean existsAdminByUsername(String username);

    Optional<Admin> findByPassword(String password);
}
