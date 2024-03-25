package com.example.sms.controller;

import com.example.sms.entity.Student;
import com.example.sms.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/add-student")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student"; // Return the add-student form
    }

    @PostMapping("/add-student")
    public String addStudent(@Valid @ModelAttribute("student") Student student, BindingResult result) {
//        if (result.hasErrors()) {
//            return "add-student"; // Return the form with validation errors
//        }

        // Add logic to set other fields as needed...
        // For example, generate student ID, password, etc.

        // Add logic to save the student
        Student savedStudent = studentService.registerStudent(student);

        if (savedStudent == null) {
            // Handle if the student was not saved successfully (optional)
            return "error-page"; // Redirect to an error page
        }

        return "redirect:/dashboard"; // Redirect to the dashboard after successful addition
    }

    @GetMapping("/remove-student")
    public String showRemoveStudentForm(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "remove-student"; // Return the remove-student form
    }

    // Add logic for removing a student

    @GetMapping("/search-student")
    public String showSearchStudentForm() {
        return "search-student"; // Return the search-student form
    }

    @PostMapping("/search-student")
    public String searchStudent(@RequestParam("searchIdentifier") String searchIdentifier, Model model) {
        List<Student> searchResults = studentService.searchStudentsByIdOrName(searchIdentifier);
        model.addAttribute("searchResults", searchResults);
        return "search-results"; // Return the search-results view
    }

    @GetMapping("/display-students")
    public String displayAllStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "display-students"; // Return the display-students view
    }
}
