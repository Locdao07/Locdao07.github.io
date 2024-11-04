package test.Server.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import test.Repository.PaymentRepository;
import test.Repository.RegistrationRepository;
import test.Repository.StudentRepository;
import test.Repository.SubjectRepository;
import test.entity.Payment;
import test.entity.PaymentDTO;
import test.entity.Registration;
import test.entity.Student;
import test.entity.Subject;
import test.entity.Subjectdto;
import test.service.PaymentService;
import test.service.RegistrationService;
import test.service.StudentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private RegistrationRepository registrationRepository;
    public PaymentServiceImpl(PaymentRepository paymentRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<PaymentDTO> getPaymentsByStudent(Long studentId) {
        List<Payment> payments = paymentRepository.findByStudentId(studentId);
        return payments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public void pay(Long studentId, List<Long> registrationIds, Double totalFee) {
    	Double remainingFee = totalFee;
        for (Long registrationId : registrationIds) {
            Registration registration = registrationRepository.findById(registrationId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid registration Id:" + registrationId));
            Double registrationFee = registration.getSubject().getFee();
            Double paymentAmount = Math.min(remainingFee, registrationFee);
            Payment payment = new Payment();
            payment.setStudent(registration.getStudent());
            payment.setSubject(registration.getSubject());
            payment.setPaymentDate(new Date());
           // payment.setTotalFee(totalFee != null ? totalFee : 0.0);
            //payment.setTotalFee(totalFee);
            //payment.setTotalFee(registration.getFee());
            payment.setTotalFee(registration.getSubject().getFee());
            paymentRepository.save(payment);
            // Cập nhật lại mức phí còn lại
            remainingFee -= paymentAmount;

            // Nếu mức phí còn lại <= 0 thì dừng vòng lặp
            if (remainingFee <= 0) {
                break;
            }
        }
    }

    public boolean hasPaid(Long studentId) {
        List<Payment> payments = paymentRepository.findByStudentId(studentId);
        return payments != null && !payments.isEmpty();
    }


    @Transactional
    public Double calculateTotalFee(List<Long> registrationIds) {
        Double totalFee = 0.0;
        for (Long registrationId : registrationIds) {
            Registration registration = registrationRepository.findById(registrationId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid registration Id:" + registrationId));
            
            if (registration.getFee() == null) {
                registration.setFee(0.0);
            }
            totalFee += registration.getFee();
        }
        return totalFee;
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setStudentId(payment.getStudent().getId());
        dto.setSubjectId(payment.getSubject().getId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setTotalFee(payment.getTotalFee());
        return dto;
    }
}






