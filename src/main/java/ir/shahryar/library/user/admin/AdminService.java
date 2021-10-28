package ir.shahryar.library.user.admin;

import ir.shahryar.library.Exception.EmptyListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminProperties adminProperties;

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
            result = adminProperties.properties.getProperty("NoAdminInDatabaseResponse");
        } else {
            String username = admin.getUsername();
            String password = admin.getPassword();
            if (username == null) {
                result = adminProperties.properties.getProperty("NotEnoughInfo.usernameResponse");
            } else if (password == null) {
                result = adminProperties.properties.getProperty("NotEnoughInfo.passwordResponse");
            } else if (!isValidUsername(username)) {
                result = adminProperties.properties.getProperty("InvalidUsernameResponse");
            } else if (!isValidPassword(password)) {
                result = adminProperties.properties.getProperty("InvalidPasswordResponse");
            } else {
                result = "ok";
            }
        }
        return result;
    }
}