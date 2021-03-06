package ir.shahryar.library.user.customer;

import ir.shahryar.library.Exception.AlreadyExistPack;
import ir.shahryar.library.Exception.UserAlreadyExistException;
import ir.shahryar.library.Exception.UserNotFoundException;
import ir.shahryar.library.data.Response;
import ir.shahryar.library.packs.Pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("signUp")
    public String signUp(@RequestBody Customer customer) {
        String result;
        if (customer == null) {
            result = new Response(customerService.properties.getProperty("NullCustomer")).toJson();
        } else {
            try {
                customer.init(0);
                customer = customerService.saveNewCustomer(customer);
                result = new Response(customer + customerService.properties.getProperty("SignedUp")).toJson();
            } catch (UserAlreadyExistException e) {
                result = new Response(e.getMessage()).toJson();
            }
        }
        return result;
    }

    @PostMapping("login")
    public String Login(@RequestBody Customer customer) {
        Response result;
        String customerValidationResponse = customerService.validateCustomer(customer);
        if (customerValidationResponse.equals("ok")) {
            customer = customerService.getByNationalId(customer.getNationalId());
            String token = customerService.giveToken(customer);
            result = new Response(token);
        } else {
            result = new Response(customerValidationResponse);
        }
        return result.toJson();
    }

    @PostMapping("buy")
    public String buyPack(@RequestHeader String id, @RequestParam String pack) {
        String result;
        if (id == null) {
            result = new Response(customerService.properties.getProperty("UserDidNotLoginYet")).toJson();
        } else {
            try {
                Customer customer = customerService.getById(id);
                result = new Response(customerService.buyPack(customer, Pack.valueOf(pack))).toJson();
            } catch (UserNotFoundException e) {
                result = new Response(customerService.properties.getProperty("UserNotFoundResponse")).toJson();
            } catch (AlreadyExistPack e) {
                result = new Response(e.getMessage()).toJson();
            }
        }
        return result;
    }

    @PostMapping("charge")
    public String chargeWallet(@RequestHeader String id, @RequestParam long amount) {
        String result;
        if (id == null) {
            result = new Response(customerService.properties.getProperty("UserDidNotLoginYet")).toJson();
        } else {
            try {
                Customer customer = customerService.getById(id);
                customerService.chargeWallet(customer, amount);
                result = new Response(customerService.properties.getProperty("ChargedWallet")).toJson();
            } catch (UserNotFoundException e) {
                result = new Response(customerService.properties.getProperty("UserNotFoundResponse")).toJson();
            }
        }
        return result;
    }

    @GetMapping("dashboard")
    public String showCustomer(@RequestHeader String id) {
        String result;
        if (id == null) {
            result = new Response(customerService.properties.getProperty("UserDidNotLoginYet")).toJson();
        } else {
            try {
                Customer customer = customerService.getById(id);
                result = customer.toJson();
            } catch (UserNotFoundException e) {
                result = new Response(customerService.properties.getProperty("UserNotFoundResponse")).toJson();
            }
        }
        return result;
    }
}
