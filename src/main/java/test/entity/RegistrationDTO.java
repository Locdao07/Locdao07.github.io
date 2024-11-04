package test.entity;



import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

@Data
public class RegistrationDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Long subjectId;
    private Date registrationDate;
    private boolean approved;
    
    private Double fee;
    private String studentName;
    private String subjectName;
    private Date paymentDate; 
   
    // Constructors, Getters, Setters
    public RegistrationDTO() {
    }
    public String getFormattedFee() {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        return formatter.format(fee);
    }
    public RegistrationDTO(Long studentId, Long subjectId, Date registrationDate ,String studentName ,String subjectName,boolean approved ,Double fee ) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.registrationDate = registrationDate;
        this.approved = approved;
        this.studentName = studentName;
        this.subjectName = subjectName;
        this.fee= fee;
        
    }

    // Getter and Setter for subjectId
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}

