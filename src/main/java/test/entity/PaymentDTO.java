package test.entity;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
public class PaymentDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private Long studentId;
	    private Long subjectId;
	    private Date paymentDate;
	    private Double totalFee;
	    private String subjectName;
//	    private String paymentMethod;
//	    private String studentName; // Add this field
//
//	    public PaymentDTO() {
//	    }
//
//	    public PaymentDTO(Payment payment) {
//	        this.id = payment.getId();
//	        this.studentId = payment.getStudent().getId();
//	        this.subjectId = payment.getSubject().getId();
//	        this.paymentDate = payment.getPaymentDate();
//	        this.totalFee = payment.getTotalFee();
//	        this.paymentMethod = payment.getPaymentMethod();
//	        this.studentName = payment.getStudent().getFirstName() + " " + payment.getStudent().getLastName();
//	    }

	    
    // Getters and setters
}
