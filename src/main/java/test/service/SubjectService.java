package test.service;



import java.util.List;

import test.entity.Subject;
import test.entity.Subjectdto;

public interface SubjectService {
    List<Subjectdto> getAllSubjects();
    void saveSubject(Subjectdto subjectDto);
    Subjectdto getSubjectById(Long id);
    Subject updateSubject(Subjectdto subjectDto);
    void deleteSubjectById(Long id);
    List<Subjectdto> searchSubjects(String keyword);
    boolean isSubjectNameUnique(String subjectName, Long id);
    boolean isSubjectCodeUnique(String subjectCode, Long id);
    List<Subjectdto> getRegisteredSubjectsByStudentId(Long studentId); // Add this method

    
}
