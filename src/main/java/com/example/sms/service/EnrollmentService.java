package com.example.sms.service;

import com.example.sms.entity.Course;
import com.example.sms.entity.Student;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
	@Autowired
    private final CourseRepository courseRepository;
	@Autowired
    private final StudentRepository studentRepository;

    @Autowired
    public EnrollmentService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public void enrollStudentInCourse(String studentId, String courseId) {
        Student student = studentRepository.findByStudentId(studentId);
        Course course = courseRepository.findByCourseId(courseId);

        if (student != null && course != null) {
            student.getCourses().add(course);
            studentRepository.save(student);
        } else {
            // Handle case where student or course is not found
        }
    }

    public void dropStudentFromCourse(String studentId, String courseId) {
        Student student = studentRepository.findByStudentId(studentId);

        if (student != null) {
            student.getCourses().removeIf(course -> course.getCourseId().equals(courseId));
            studentRepository.save(student);
        } else {
            // Handle case where student is not found
        }
    }
}

