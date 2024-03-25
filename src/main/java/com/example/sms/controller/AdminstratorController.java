
package com.example.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sms.entity.Administrator;
import com.example.sms.service.AdministratorService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminstratorController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        // Add an empty administrator object to the model
        model.addAttribute("administrator", new Administrator());
        return "registration"; // Assuming your registration form template is named "registration.html"
    }
    
    @PostMapping("/registration")
    public String registerAdmin(Administrator administrator) {
        // Check if username already exists
        Administrator existingUsername = administratorService.findByUsername(administrator.getUsername());
        if (existingUsername != null) {
            // Username already exists, return to registration form with error message
            return "redirect:/registration?error=usernameExists";
        }

        // Check if email already exists
        Administrator existingEmail = administratorService.findByEmail(administrator.getEmail());
        if (existingEmail != null) {
            // Email already exists, return to registration form with error message
            return "redirect:/registration?error=emailExists";
        }

        // Register admin
        Administrator registeredAdmin = administratorService.registerAdministrator(administrator);
        // Redirect to a success page or homepage
        return "redirect:/registration?success=true";
    }
    
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Assuming your login form template is named "login.html"
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        // Authenticate admin
        boolean authenticated = administratorService.authenticate(username, password);
        if (authenticated) {
            // Redirect to admin dashboard after successful login
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Return to login page with error message
        }
    }
    
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Redirect to login page
        return "redirect:/login";
    }
    
    
}




