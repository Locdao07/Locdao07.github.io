package test.entity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import jakarta.annotation.security.DenyAll;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
@Data
public class Subjectdto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Subject name is required")
    private String subjectName;

    @NotBlank(message = "Subject code is required")
    private String subjectCode;
    
    @NotBlank(message = "Subject code is required")
    private String description;
    
    @NotNull(message = "Credits are required")
    private Integer credits;

    @NotNull(message = "Fee is required")
    private Double fee;
   
    // Constructors, getters, and setters
    public Subjectdto() {}
    public String getFormattedFee() {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        return formatter.format(fee);
    }

    public Subjectdto(Long id, String subjectName, String subjectCode, String description, Integer credits, Double fee) {
        this.id = id;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.description = description;
        this.credits = credits;
        this.fee = fee;
    }


}


    // Getters and setters

