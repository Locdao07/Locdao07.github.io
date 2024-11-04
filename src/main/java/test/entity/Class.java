//package test.entity;
//
//
//import java.util.List;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//public class Class {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String className;
//
//    @ManyToOne
//    @JoinColumn(name = "course_id")
//    private Course course;
//
//    @OneToMany(mappedBy = "aClass", cascade = CascadeType.ALL)
//    private List<Student> students;
//
//    // Getters and setters
//}
//
