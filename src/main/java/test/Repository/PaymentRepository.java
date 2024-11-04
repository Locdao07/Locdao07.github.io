package test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import test.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	@Query("SELECT p FROM Payment p JOIN FETCH p.subject WHERE p.subject IS NOT NULL")
    List<Payment> findAllPaymentsWithSubjects();
//
//    @Query("SELECT p.student.firstName, p.paymentDate, SUM(p.totalFee), MAX(p.paymentMethod) FROM Payment p GROUP BY p.student.firstName, p.paymentDate")
//    List<Object[]> findPaidStudentsData();
	
	 List<Payment> findByStudentId(Long studentId);
	  boolean existsByStudentId(Long studentId);
	  boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
