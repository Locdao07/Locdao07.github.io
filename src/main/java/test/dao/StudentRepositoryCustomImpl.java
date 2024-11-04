//package test.dao;
//
//import java.util.Optional;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.NoResultException;
//import jakarta.persistence.PersistenceContext;
//import test.Repository.StudentRepositoryCustom;
//import test.entity.Student;
//
//public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public Optional<Student> findOptionalByEmail(String email) {
//        try {
//            String query = "SELECT s FROM Student s WHERE s.email = :email";
//            Student student = entityManager.createQuery(query, Student.class)
//                                           .setParameter("email", email)
//                                           .getSingleResult();
//            return Optional.of(student);
//        } catch (NoResultException e) {
//            return Optional.empty();
//        }
//    }
//}
