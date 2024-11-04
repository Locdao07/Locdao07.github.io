package test.Server.impl;


import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.Repository.StudentRepository;
import test.Repository.SubjectRepository;
import test.entity.Registration;
import test.entity.Student;
import test.entity.Studentdto;
import test.entity.Subject;
import test.service.StudentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAllOrderByNameAsc();
    }

    @Override
    public void saveStudent(Studentdto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setImageUrl(studentDto.getImageUrl());
        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }
    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    
    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }
    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }


    @Override
    public boolean isLastNameUnique(String lastName) {
        return studentRepository.findByLastName(lastName) == null;
    }

    @Override
    public boolean isEmailUnique(String email) {
        return studentRepository.findByEmail(email) == null;
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        return studentRepository.findByFirstNameContainingOrLastNameContaining(keyword, keyword);
    }

//    @Override
//    public Optional<Student> findOptionalByEmail(String email) {
//        return studentRepository.findOptionalByEmail(email);
//    }

    
    @Override
    public Page<Student> findPaginated(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    @Override
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }
    private Student convertDTOToEntity(Studentdto studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setImageUrl(studentDTO.getImageUrl());
        return student;
    }
    

    @Override
    public List<Registration> getRegistrationsByStudentEmail(String email) {
        Student student = studentRepository.findByEmail(email);
        return student != null ? student.getRegistrations() : null;
    }
    
    @Override
    public Studentdto getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            return null;
        }
        return convertToDTO(student);
    }
    
    private Studentdto convertToDTO(Student student) {
        Studentdto dto = new Studentdto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        // Set other properties if needed
        return dto;
    }

//    @Override
//    public List<Student> getStudentsByClassAndBatch(String className, String batch) {
//        return studentRepository.findByClassNameAndBatch(className, batch);
//    }
}