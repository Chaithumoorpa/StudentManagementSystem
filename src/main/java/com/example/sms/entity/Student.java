package com.example.sms.entity;

import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = true, unique = true, length=10) // Increase length for UUID
    private String studentId; // Student ID or matriculation number

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = true)
    private String password; // Password field added
    
    @ManyToMany
    @JoinTable(name = "student_course",
               joinColumns = @JoinColumn(name = "student_id"),
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    // Constructors, getters, and setters

    // Constructor
    public Student() {
    	
    	
    }
    
    public Student(String firstName, String lastName, Date dateOfBirth, String gender, String email) {
    	
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.studentId=generateUniqueId();
        this.password = generatePassword(); // Generate password after setting dateOfBirth
        this.gender = gender;
        this.email = email;
    }

    // Method to generate a unique student ID
    public static String generateUniqueId() {
        // Generate UUID
        UUID uuid = UUID.randomUUID();
        
        // Convert UUID to string and remove hyphens
        String uuidString = uuid.toString().replace("-", "").toUpperCase();
        
        // Take the first 10 characters
        return uuidString.substring(0, 10);
    }
    
    // Method to generate a password based on date of birth and first name initials
    public String generatePassword() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String dob = dateFormat.format(this.dateOfBirth);
        String firstNameInitials = this.firstName.substring(0, Math.min(this.firstName.length(), 4)).toLowerCase();
        return dob + firstNameInitials;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
 // Method to add a course to the student's enrolled courses
    public void addCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this); // Bidirectional relationship
    }

    // Method to remove a course from the student's enrolled courses
    public void removeCourse(Course course) {
        courses.remove(course);
        course.getStudents().remove(this); // Bidirectional relationship
    }

    // Getter for courses
    public Set<Course> getCourses() {
        return courses;
    }

    // Optional: toString(), hashCode(), and equals() methods
}
