package ir.shahryar.library.user.customer;

import ir.shahryar.library.user.admin.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByNationalId(String nationalId);

    boolean existsCustomerByNationalId(String nationalId);

    Optional<Customer> findByPassword(String password);
}
