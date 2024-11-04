package test.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.entity.Payment;
import test.entity.Registration;
import test.entity.Subject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findBySubjectId(Long subjectId);
    List<Registration> findByStudentId(Long studentId);
    List<Registration> findBySubjectIdAndApprovedTrue(Long subjectId);
    Page<Registration> findAll(Pageable pageable);
    List<Registration> findByStudentIdAndApproved(Long studentId, boolean approved);
//    @Query("SELECT r FROM Registration r WHERE r.paymentDate IS NOT NULL")
//    List<Registration> findAllPayments();
//
//    @Query("SELECT r.subject FROM Registration r WHERE r.student.id = :studentId AND r.paymentDate IS NULL")
//    List<Subject> findSubjectsToPayByStudentId(@Param("studentId") Long studentId);

}