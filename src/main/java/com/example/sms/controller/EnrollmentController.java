package com.example.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sms.entity.Course;
import com.example.sms.entity.Student;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.CourseService;
import com.example.sms.service.EnrollmentService;
import com.example.sms.service.StudentService;

@Controller
public class EnrollmentController {
	
	
	@Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
	@Autowired
	private final EnrollmentService enrollmentService;
	
	
	
	public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/enroll-student")
    public String showEnrollStudentPage() {
        return "enrollStudent"; // Return the name of the Thymeleaf template for enrolling a student
    }

    @GetMapping("/drop-student")
    public String showDropStudentPage() {
        return "dropStudent"; // Return the name of the Thymeleaf template for dropping a student
    }

    @GetMapping("/display-student-courses/{studentId}")
    public String showStudentCoursesPage(@PathVariable("studentId") String studentId, Model model) {
        // Retrieve the list of courses the student is enrolled in (you need to implement this logic)
         List<Course> courses = courseService.getCoursesByStudentId(studentId);
         model.addAttribute("courses", courses);
        return "studentCourses"; // Return the name of the Thymeleaf template for displaying student courses
    }

    @GetMapping("/display-course-students/{courseId}")
    public String showCourseStudentsPage(@PathVariable("courseId") String courseId,Model model) {
        // Retrieve the list of students enrolled in the course (you need to implement this logic)
         List<Student> students = studentService.getStudentsByCourseId(courseId);
         model.addAttribute("students", students);
        return "courseStudents"; // Return the name of the Thymeleaf template for displaying course students
    }
    @PostMapping("/enroll-student")
    public String enrollStudentInCourse(@RequestParam("studentId") String studentId, @RequestParam("courseId") String courseId) {
        enrollmentService.enrollStudentInCourse(studentId, courseId);
        return "redirect:/dashboard"; // Redirect to a success page
    }
}
