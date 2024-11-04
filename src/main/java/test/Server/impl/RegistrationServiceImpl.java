package test.Server.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.Repository.RegistrationRepository;
import test.Repository.StudentRepository;
import test.Repository.SubjectRepository;
import test.entity.Payment;
import test.entity.Registration;
import test.entity.RegistrationDTO;
import test.entity.Student;
import test.entity.Subject;
import test.service.RegistrationService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository, SubjectRepository subjectRepository) {
        this.registrationRepository = registrationRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationDTO saveRegistration(RegistrationDTO registrationDTO) {
        Registration registration = convertToEntity(registrationDTO);
        registration.setRegistrationDate(new Date()); // Set registration date to current date
        Registration savedRegistration = registrationRepository.save(registration);
        return convertToDTO(savedRegistration);
    }

    @Override
    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public void approveRegistration(Long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow();
        registration.setApproved(true);
        registrationRepository.save(registration);
    }

    @Override
    public List<RegistrationDTO> getRegistrationsBySubject(Long subjectId) {
        return registrationRepository.findBySubjectIdAndApprovedTrue(subjectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<RegistrationDTO> getRegistrationsByStudent(Long studentId) {
//        return registrationRepository.findByStudentId(studentId).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
    @Override
    public List<RegistrationDTO> getRegistrationsByStudent(Long studentId) {
        List<Registration> registrations = registrationRepository.findByStudentId(studentId);
        return registrations.stream()
                .map(registration -> {
                    Subject subject = registration.getSubject();
                    RegistrationDTO dto = new RegistrationDTO();
                    dto.setId(registration.getId());
                    dto.setSubjectName(subject != null ? subject.getSubjectName() : "");
                    dto.setRegistrationDate(registration.getRegistrationDate());
                    dto.setFee(subject != null ? subject.getFee() : 0.0); // Set giá trị fee từ Subject
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<RegistrationDTO> getPaginatedRegistrations(Pageable pageable) {
        Page<Registration> registrationPage = registrationRepository.findAll(pageable);
        List<RegistrationDTO> registrationDTOs = registrationPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(registrationDTOs, pageable, registrationPage.getTotalElements());
    }

    private RegistrationDTO convertToDTO(Registration registration) {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setId(registration.getId());
        registrationDTO.setStudentId(registration.getStudent().getId());
        registrationDTO.setSubjectId(registration.getSubject().getId());
        registrationDTO.setRegistrationDate(registration.getRegistrationDate());
        registrationDTO.setApproved(registration.isApproved());

        // Add student and subject names to DTO for display purposes
        registrationDTO.setStudentName(registration.getStudent().getFirstName() + " " + registration.getStudent().getLastName());
        registrationDTO.setSubjectName(registration.getSubject().getSubjectName());
        registrationDTO.setFee(registration.getSubject().getFee()); // Add fee

        return registrationDTO;
    }

    private Registration convertToEntity(RegistrationDTO registrationDTO) {
        Registration registration = new Registration();
        registration.setId(registrationDTO.getId());

        Student student = studentRepository.findById(registrationDTO.getStudentId()).orElse(null);
        Subject subject = subjectRepository.findById(registrationDTO.getSubjectId()).orElse(null);
        registration.setStudent(student);
        registration.setSubject(subject);
        registration.setApproved(registrationDTO.isApproved());

        return registration;
    }
    
    @Override
    public Double getTotalFeeByStudent(Long studentId) {
        List<Registration> registrations = registrationRepository.findByStudentId(studentId);
        return registrations.stream()
                .map(registration -> subjectRepository.findById(registration.getSubject().getId()).orElse(null))
                .filter(subject -> subject != null)
                .mapToDouble(Subject::getFee)
                .sum();
    }
    
    @Override
    public List<Subject> getRegisteredSubjects(Long studentId) {
        // Query registrations by studentId
        List<Registration> registrations = registrationRepository.findByStudentId(studentId);

        // Extract subjects from registrations
        List<Subject> registeredSubjects = registrations.stream()
                .map(Registration::getSubject)
                .collect(Collectors.toList());

        return registeredSubjects;
    }
    
//    public List<Payment> getAllPayments() {
//        return registrationRepository.findAllPayments();
//    }
//
//    @Override
//    public List<Subject> getSubjectsToPay(Long studentId) {
//        return registrationRepository.findSubjectsToPayByStudentId(studentId);
//    }

//    @Override
//    public void setPaid(List<Long> registrationIds) {
//        List<Registration> registrations = registrationRepository.findAllById(registrationIds);
//        for (Registration registration : registrations) {
//            registration.setPaid(true); // Update the 'paid' status
//            registrationRepository.save(registration);
//        }
//    }
}
