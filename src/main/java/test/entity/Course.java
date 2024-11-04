//package test.entity;
//
//import java.util.List;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//public class Course {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String courseCode;
//    private String courseName;
//    private String description;
//
//    @OneToMany(mappedBy = "course")
//    private List<Class> classes;
//
//    // Getters and setters
//}
//
