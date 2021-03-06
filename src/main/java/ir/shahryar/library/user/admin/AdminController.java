package ir.shahryar.library.user.admin;

import ir.shahryar.library.Exception.UserNotFoundException;
import ir.shahryar.library.data.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("login")
    public String login(@RequestBody Admin admin) {
        Response result;
        String adminValidationResponse = adminService.validateAdmin(admin);
        if (adminValidationResponse.equals("ok")) {
            String token = adminService.giveToken();
            result = new Response(token);
        } else {
            result = new Response(adminValidationResponse);
        }
        return result.toJson();
    }

    @PostMapping("change/Info")
    public String changeInfo(@RequestBody Admin admin) {
        Response result;
        String adminValidationResponse = adminService.validateAdmin(admin);
        if (adminValidationResponse.equals("ok")) {
            Admin oldAdmin = adminService.get();
            result = new Response(adminService.changeInfo(oldAdmin, admin));
        } else {
            result = new Response(adminValidationResponse);
        }
        return result.toJson();
    }

    @PostMapping("change/password")
    public String changePassword(AdminWrapper adminWrapper) {
        Response result;
        String adminValidationResponse = adminService.validateAdmin(adminWrapper);
        if (adminValidationResponse.equals("ok")) {
            String msg = adminService.changePassword(adminWrapper);
            result = new Response(msg);
        } else {
            result = new Response(adminValidationResponse);
        }
        return result.toJson();
    }

    @PostMapping("change/username")
    public String changeUsername(AdminWrapper adminWrapper) {
        Response result;
        String adminValidationResponse = adminService.validateAdmin(adminWrapper);
        if (adminValidationResponse.equals("ok")) {
            String msg = adminService.changeUsername(adminWrapper);
            result = new Response(msg);
        } else {
            result = new Response(adminValidationResponse);
        }
        return result.toJson();
    }

    @GetMapping("")
    public String showInfo() {
        String result;
        try {
            Admin admin = adminService.get();
            result = admin.toJson();
        } catch (UserNotFoundException e) {
            result = new Response(e.getMessage()).toJson();
        }
        return result;
    }
}
