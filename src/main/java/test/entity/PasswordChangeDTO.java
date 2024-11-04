package test.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeDTO {
	
	@NotBlank
    private String oldPassword;
	@NotBlank
    private String newPassword;

    // Getters and Setters
}
