package test.Server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.Repository.RegistrationRepository;
import test.Repository.SubjectRepository;
import test.entity.Registration;
import test.entity.Subject;
import test.entity.Subjectdto;
import test.service.SubjectService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository,RegistrationRepository registrationRepository) {
        this.subjectRepository = subjectRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public List<Subjectdto> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(subject -> new Subjectdto(
                subject.getId(),
                subject.getSubjectName(),
                subject.getSubjectCode(),
                subject.getDescription(),
                subject.getCredits(),
                subject.getFee()
        )).collect(Collectors.toList());
    }

    @Override
    public void saveSubject(Subjectdto subjectDto) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectDto.getSubjectName());
        subject.setSubjectCode(subjectDto.getSubjectCode());
        subject.setDescription(subjectDto.getDescription());
        subject.setCredits(subjectDto.getCredits());
        subject.setFee(subjectDto.getFee());
        subjectRepository.save(subject);
    }

    @Override
    public Subjectdto getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id).orElse(null);
        if (subject == null) {
            return null;
        }
        return new Subjectdto(
                subject.getId(),
                subject.getSubjectName(),
                subject.getSubjectCode(),
                subject.getDescription(),
                subject.getCredits(),
                subject.getFee()
        );
    }

    @Override
    public Subject updateSubject(Subjectdto subjectDto) {
        Subject subject = subjectRepository.findById(subjectDto.getId()).orElse(null);
        if (subject == null) {
            return null;
        }
        subject.setSubjectName(subjectDto.getSubjectName());
        subject.setSubjectCode(subjectDto.getSubjectCode());
        subject.setDescription(subjectDto.getDescription());
        subject.setCredits(subjectDto.getCredits());
        subject.setFee(subjectDto.getFee());
        return subjectRepository.save(subject);
    }

    @Override
    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public List<Subjectdto> searchSubjects(String keyword) {
        List<Subject> subjects = subjectRepository.findBySubjectNameContainingOrSubjectCodeContaining(keyword, keyword);
        return subjects.stream().map(subject -> new Subjectdto(
                subject.getId(),
                subject.getSubjectName(),
                subject.getSubjectCode(),
                subject.getDescription(),
                subject.getCredits(),
                subject.getFee()
        )).collect(Collectors.toList());
    }
    
    @Override
    public boolean isSubjectNameUnique(String subjectName, Long id) {
        if (id == null) {
            return !subjectRepository.existsBySubjectName(subjectName);
        } else {
            return !subjectRepository.existsBySubjectNameAndIdNot(subjectName, id);
        }
    }

    @Override
    public boolean isSubjectCodeUnique(String subjectCode, Long id) {
        if (id == null) {
            return !subjectRepository.existsBySubjectCode(subjectCode);
        } else {
            return !subjectRepository.existsBySubjectCodeAndIdNot(subjectCode, id);
        }
    }
  
    @Override
    public List<Subjectdto> getRegisteredSubjectsByStudentId(Long studentId) {
        List<Registration> registrations = registrationRepository.findByStudentId(studentId);
        return registrations.stream()
                .map(registration -> {
                    Subject subject = registration.getSubject();
                    return new Subjectdto(subject.getId(), subject.getSubjectName(), subject.getSubjectCode(), subject.getDescription(), subject.getCredits(), subject.getFee());
                })
                .collect(Collectors.toList());
    }
}
