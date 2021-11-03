package ir.shahryar.library.user.admin;

import ir.shahryar.library.Exception.EmptyListException;
import ir.shahryar.library.util.props.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    public MyProperties properties;

    public boolean isValidUsername(String username) throws EmptyListException {
        Admin admin = get();
        return admin.getUsername().equals(username);
    }

    public boolean isValidPassword(String password) throws EmptyListException {
        Admin admin = get();
        return admin.getPassword().equals(password);
    }

    public boolean isEmpty() {
        return adminRepository.findAll().isEmpty();
    }

    private void changeEmail(Admin admin, String newEmail) {
        admin.setEmail(newEmail);
    }

    private void changeFirstName(Admin admin, String newFirstName) {
        admin.setFirstName(newFirstName);
    }

    private void changeLastName(Admin admin, String newLastName) {
        admin.setLastName(newLastName);
    }

    private void changeSalary(Admin admin, int newSalary) {
        admin.setSalary(newSalary);
    }

    public String changeInfo(Admin oldAdmin, Admin admin) {
        StringBuilder resultMessages = new StringBuilder();
        if (admin.getEmail() != null) {
            String newEmail = admin.getEmail();
            String oldEmail = oldAdmin.getEmail();
            changeEmail(oldAdmin, newEmail);
            resultMessages.append("Email changed from '").append(oldEmail).append("' to '").append(newEmail).append("'\n");
        }
        if (admin.getFirstName() != null) {
            String newFirstName = admin.getFirstName();
            String oldFirstName = oldAdmin.getFirstName();
            changeFirstName(oldAdmin, newFirstName);
            resultMessages.append("First name changed from '").append(oldFirstName).append("' to '").append(newFirstName).append("'\n");
        }
        if (admin.getLastName() != null) {
            String newLastName = admin.getLastName();
            String oldLastName = oldAdmin.getLastName();
            changeLastName(oldAdmin, newLastName);
            resultMessages.append("Last name changed from '").append(oldLastName).append("' to '").append(newLastName).append("'\n");
        }
        if (admin.getSalary() != -1) {
            int newSalary = admin.getSalary();
            int oldSalary = oldAdmin.getSalary();
            changeSalary(oldAdmin, newSalary);
            resultMessages.append("Salary changed from '").append(oldSalary).append("' to '").append(newSalary).append("'\n");
        }
        if (resultMessages.length() == 0) {
            resultMessages.append("There is nothing to change\n");
        }
        return resultMessages.substring(0, resultMessages.length() - 1);
    }

    public Admin get() throws EmptyListException {
        List<Admin> list = adminRepository.findAll();
        if (list.isEmpty()) throw new EmptyListException();
        return list.get(0);
    }

    public String giveToken() {
        Admin admin = get();
        return admin.getId();
    }

    public String validateAdmin(Admin admin) {
        String result;
        if (isEmpty()) {
            result = properties.getProperty("NoAdminInDatabase");
        } else {
            String username = admin.getUsername();
            String password = admin.getPassword();
            if (username == null) {
                result = properties.getProperty("NotEnoughInfo.username");
            } else if (password == null) {
                result = properties.getProperty("NotEnoughInfo.password");
            } else if (!isValidUsername(username)) {
                result = properties.getProperty("InvalidUsername");
            } else if (!isValidPassword(password)) {
                result = properties.getProperty("InvalidPassword");
            } else {
                result = "ok";
            }
        }
        return result;
    }

    public boolean isValidId(String id) {
        return get().getId().equals(id);
    }

    public String changeUsername(AdminWrapper adminWrapper) {
        Admin admin = get();
        admin.setUsername(adminWrapper.getNewUsername());
        return "Username changed";
    }

    public String changePassword(AdminWrapper adminWrapper) {
        String result;
        String adminValidationResponse = validateAdmin(adminWrapper);
        if (adminValidationResponse.equals("ok")) {
            Admin admin = get();
            admin.setPassword(adminWrapper.getNewPassword());
            result = "Password changed";
        } else {
            result = adminValidationResponse;
        }
        return result;
    }
}
