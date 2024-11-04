package test.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Studentdto {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotBlank(message = "Tên là bắt buộc ")
    @Size(min = 5, max = 250)
   // @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Họ phải bắt đầu bằng chữ in hoa và chỉ chứa các chữ cái")
    private String firstName;
    
    @NotEmpty(message = "Không được để trống ")
   // @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Tên phải bắt đầu bằng chữ in hoa và chỉ chứa các chữ cái")
    private String lastName;
    
    @NotEmpty(message = "Không được để trống")
    @Email(message = "Email is required")
    //@Min(value = 18)
    private String email;
    
    //private MultipartFile imageUrl;
    
    private String imageUrl;
    private String password;
    private String username;
    //private Long classId;
    private List<PaymentDTO> payments;

    
    public Studentdto() {
		
	}
    public Studentdto( Long id, String firstName, String lastName, String email, String imageUrl, String password,String username) {
       this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
        this.username = username;
       // this.classId = classId;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	

//	public MultipartFile getImageUrl(){
//        return imageUrl;
//    }
//
//    public void setImageUrl(MultipartFile imageUrl) {
//        this.imageUrl = imageUrl;
//    }
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
