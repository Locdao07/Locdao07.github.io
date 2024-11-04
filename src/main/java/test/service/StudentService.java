package test.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import test.entity.Registration;
import test.entity.Student;
import test.entity.Studentdto;
import test.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface StudentService {
	List<Student> getAllStudents();
    void saveStudent(Studentdto studentDto);
    Student getStudentById(Long id);
    Student updateStudent(Student student);
    void deleteStudentById(Long id);
    boolean isLastNameUnique(String lastName);
    boolean isEmailUnique(String email);
    List<Student> searchStudents(String keyword);
    Page<Student> findPaginated(Pageable pageable);
    Student findByUsername(String username);
    List<Subject> getAllSubjects();
    

    List<Registration> getRegistrationsByStudentEmail(String email);
  
    Studentdto getStudentByEmail(String email);
    Student findByEmail(String email);
    Optional<Student> findById(Long id);
}
