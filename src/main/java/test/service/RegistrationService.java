package test.service;


import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import test.entity.Payment;
import test.entity.RegistrationDTO;
import test.entity.Subject;



public interface RegistrationService {
    List<RegistrationDTO> getAllRegistrations();
    RegistrationDTO saveRegistration(RegistrationDTO registrationDTO);
    void deleteRegistration(Long id);
    void approveRegistration(Long id);
    List<RegistrationDTO> getRegistrationsBySubject(Long subjectId);
    List<RegistrationDTO> getRegistrationsByStudent(Long studentId); // Thêm phương thức này
    Page<RegistrationDTO> getPaginatedRegistrations(Pageable pageable);
    Double getTotalFeeByStudent(Long studentId);
//    List<Payment> getAllPayments();
//    List<Subject> getSubjectsToPay(Long studentId);
    List<Subject> getRegisteredSubjects(Long studentId);
}