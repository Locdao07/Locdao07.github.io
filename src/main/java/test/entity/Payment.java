package test.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne 
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @Column(name = "total_fee", nullable = false)
    private Double totalFee;

//    @Column(name = "payment_method", nullable = false)
//    private String paymentMethod;  // Add this field

    // Getters and Setters
}

