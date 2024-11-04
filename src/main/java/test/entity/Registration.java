package test.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Registrations")
@Data
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "approved", nullable = false)
    private boolean approved;
    // Consructors, Getters, Setters
    @Column(name = "fee", nullable = false)
    private Double fee ;
//    @Column(name = "paid", nullable = false)
//    private boolean paid; // Trường này sẽ lưu trạng thái thanh toán
//
//    // Phương thức để kiểm tra trạng thái thanh toán
//    public boolean isPaid() {
//        return paid;
//    }
    
    public Registration() {
		
   	}
       public Registration( Student student, Subject subject, Date registrationDate,boolean approved,Double fee ) {
    	   super();
    	   this.student= student;
    	   this.subject= subject;
    	   this.registrationDate = registrationDate;
    	   this.approved = approved;
    	   this.fee = fee;
    	      
       }
	public void setPaid(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
