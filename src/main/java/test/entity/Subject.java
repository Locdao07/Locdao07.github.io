package test.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "subject_code", nullable = false, unique = true)
    private String subjectCode;

    @Column(name = "description")
    private String description;
    
    @Column(name = "credits")
    private Integer credits;

    @Column(name = "fee")
    private double fee;

    @ManyToMany(mappedBy = "subjects")
    private List<Student> students;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations;
    // Getters and setters
    @OneToMany(mappedBy = "subject")
    private List<Payment> payments;
    public Subject() {}

    public Subject(Long id, String subjectName, String subjectCode, String description, Integer credits, double fee) {
        this.id = id;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.description = description;
        this.credits = credits;
        this.fee = fee;
    }	
}
