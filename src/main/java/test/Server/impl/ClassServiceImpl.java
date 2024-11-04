//package test.Server.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import test.Repository.ClassRepository;
//import test.entity.ClassDTO;
//import test.service.ClassService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ClassServiceImpl implements ClassService {
//
//    @Autowired
//    private ClassRepository classRepository;
//
//    @Override
//    public ClassDTO getClassById(Long id) {
//        Class aClass = classRepository.findById(id).orElseThrow(() -> new RuntimeException("Class not found"));
//        return convertToDTO(aClass);
//    }
//
//    @Override
//    public List<ClassDTO> getAllClasses() {
//        return classRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public ClassDTO createClass(ClassDTO classDTO) {
//        Class aClass = convertToEntity(classDTO);
//        aClass = classRepository.save(aClass);
//        return convertToDTO(aClass);
//    }
//
//    @Override
//    public ClassDTO updateClass(Long id, ClassDTO classDTO) {
//        Class aClass = classRepository.findById(id).orElseThrow(() -> new RuntimeException("Class not found"));
//        aClass.setClassName(classDTO.getClassName());
//        // Update other fields if necessary
//        aClass = classRepository.save(aClass);
//        return convertToDTO(aClass);
//    }
//
//    @Override
//    public void deleteClass(Long id) {
//        classRepository.deleteById(id);
//    }
//
//    private ClassDTO convertToDTO(Class aClass) {
//        ClassDTO classDTO = new ClassDTO();
//        classDTO.setId(aClass.getId());
//        classDTO.setClassName(aClass.getClassName());
//        if (aClass.getCourse() != null) {
//            classDTO.setCourseId(aClass.getCourse().getId());
//        }
//        return classDTO;
//    }
//
//    private Class convertToEntity(ClassDTO classDTO) {
//        Class aClass = new Class();
//        aClass.setClassName(classDTO.getClassName());
//        if (classDTO.getCourseId() != null) {
//            Course course = new Course();
//            course.setId(classDTO.getCourseId());
//            aClass.setCourse(course);
//        }
//        return aClass;
//    }
//}
//
