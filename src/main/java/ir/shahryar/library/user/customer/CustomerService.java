package ir.shahryar.library.user.customer;

import ir.shahryar.library.Exception.AlreadyExistPack;
import ir.shahryar.library.Exception.UserAlreadyExistException;
import ir.shahryar.library.Exception.UserNotFoundException;
import ir.shahryar.library.packs.Pack;
import ir.shahryar.library.wallet.Wallet;
import ir.shahryar.library.wallet.transaction.TransActionType;
import ir.shahryar.library.wallet.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    public CustomerProperties customerProperties;

    public Customer getById(String id) throws UserNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) throw new UserNotFoundException();
        return optionalCustomer.get();
    }

    public Customer getByNationalId(String nationalId) throws UserNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByNationalId(nationalId);
        if (optionalCustomer.isEmpty()) throw new UserNotFoundException();
        return optionalCustomer.get();
    }

    public Customer saveNewCustomer(Customer customer) throws UserAlreadyExistException {
        if (exists(customer.getNationalId())) throw new UserAlreadyExistException();
        return customerRepository.save(customer);
    }

    public boolean exists(String nationalId) {
        return customerRepository.existsCustomerByNationalId(nationalId);
    }

    public String giveToken(Customer customer) {
        return customer.getId();
    }

    public String buyPack(Customer customer, Pack pack) throws AlreadyExistPack {
        String result;
        if (customer.getPack().getRemainDays() < 0) {
            Wallet wallet = customer.getWallet();
            if (hasEnoughMoney(wallet, pack.getPrice())) {
                Transaction transaction = new Transaction(pack.getPrice(), TransActionType.WITHDRAW);
                wallet.addTransAction(transaction);
                result = "This pack successfully was bought";
            } else {
                result = "You don't have enough money";
            }
        } else {
            throw new AlreadyExistPack();
        }
        return result;
    }

    public boolean hasEnoughMoney(Wallet wallet, double money) {
        return wallet.getHolding() >= money;
    }

    public void chargeWallet(Customer customer, long amount) {
        Wallet wallet = customer.getWallet();
        Transaction transaction = new Transaction(amount, TransActionType.DEPOSIT);
        wallet.addTransAction(transaction);
    }

    public boolean isValidPassword(Customer customer, String password) {
        return customer.getPassword().equals(password);
    }

    public String validateCustomer(Customer customer) {
        String result;
        String nationalId = customer.getNationalId();
        String password = customer.getPassword();
        if (nationalId == null) {
            result = customerProperties.properties.getProperty("NotEnoughInfo.nationalId");
        } else if (password == null){
            result = customerProperties.properties.getProperty("NotEnoughInfo.passwordResponse");
        } else if (!exists(nationalId)) {
            result = customerProperties.properties.getProperty("InvalidNationalIdResponse");
        } else {
            customer = getByNationalId(nationalId);
            if (!isValidPassword(customer, nationalId)) {
                result = customerProperties.properties.getProperty("InvalidPasswordResponse");
            } else {
                result = "ok";
            }
        }
        return result;
    }
}
