package test.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findBySubjectName(String subjectName);
    Subject findBySubjectCode(String subjectCode);
    List<Subject> findBySubjectNameContainingOrSubjectCodeContaining(String subjectNameKeyword, String descriptionKeyword);
    boolean existsBySubjectName(String subjectName);
    boolean existsBySubjectNameAndIdNot(String subjectName, Long id);
    boolean existsBySubjectCode(String subjectCode);
    boolean existsBySubjectCodeAndIdNot(String subjectCode, Long id);
    
    @Query("SELECT s FROM Subject s JOIN s.students st WHERE st.id = :studentId")
    List<Subject> findAllByStudentId(@Param("studentId") Long studentId);
}