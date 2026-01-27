//package com.computerseekho.api;
//
//import com.computerseekho.api.controller.StudentController;
//import com.computerseekho.api.entity.Student;
//import com.computerseekho.api.service.StudentService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.extension.ExtendWith;
//
//@ExtendWith(MockitoExtension.class)
//public class StudentControllerTest {
//
//    @Mock
//    private StudentService studentService; // MOCKED SERVICE
//
//    @InjectMocks
//    private StudentController studentController; // REAL CONTROLLER
//
//    @Test
//    public void testCreateStudent() {
//
//        Student student = new Student();
//        student.setStudentId(1);
//        student.setStudentName("Sachin");
//        student.setStudentGender("Male");
//        student.setStudentAddress("Pune");
//        student.setStudentMobile(9876543210L);
//        student.setStudentQualification("B.Tech");
//        student.setStudentUsername("sachin123");
//        student.setStudentPassword("pass123");
//
//
//        // Mock Behavior
//        when(studentService.saveStudent(student)).thenReturn(student);
//
//        // Call Controller Method
//        Student result = studentController.createStudent(student);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals("Sachin", result.getStudentName());
//        assertEquals(1, result.getStudentId());
//    }
//}