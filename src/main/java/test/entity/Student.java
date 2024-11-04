package test.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Data
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "image_url")
    private String imageUrl;
	
	@Column(name = "password")
	private String password;
	@Column(name = "username")
	 private String username;
//	  @ManyToOne
//	    @JoinColumn(name = "class_id")
//	    private Class aClass;
	 @ManyToOne
	    @JoinColumn(name = "user_id", referencedColumnName = "User_Id")
	    private AppUser user;

	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "student_subject",
	            joinColumns = @JoinColumn(name = "student_id"),
	            inverseJoinColumns = @JoinColumn(name = "subject_id"))
	 private List<Subject> subjects;

	    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Registration> registrations;

	   
	    @OneToMany(mappedBy = "student")
	    private List<Payment> payments;

	public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
	
	public Student() {
		
	}
	
	public Student(String firstName, String lastName, String email, String imageUrl, String password,String username) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.imageUrl = imageUrl;
		this.password = password;
		this.username = username;

	}

}
