package test.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import test.entity.Student;



public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByLastName(String lastName);
    Student findByEmail(String email);
    //student findByFirstNameContainingOrLastNameContaining(String keyword);
    List<Student> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    Student findByUsername(String username);

    @Query("SELECT s FROM Student s ORDER BY s.lastName ASC, s.firstName ASC")
    List<Student> findAllOrderByNameAsc();
    
   // Optional<Student> findByEmail1(String email);

}
