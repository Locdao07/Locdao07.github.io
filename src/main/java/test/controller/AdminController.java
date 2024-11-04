package test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import test.entity.RegistrationDTO;
import test.entity.Student;
import test.entity.Studentdto;
import test.service.RegistrationService;
import test.service.StudentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/students")
public class AdminController {

    private final StudentService studentService;
    private RegistrationService registrationService;

    

    @Autowired
    public AdminController(StudentService studentService, RegistrationService registrationService) {
        this.studentService = studentService;
        this.registrationService = registrationService;
    }

    @GetMapping
    public String viewStudentsPage(Model model,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Student> studentPage = studentService.findPaginated(PageRequest.of(page, size));
        model.addAttribute("studentPage", studentPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "students";
    }

    @GetMapping("/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Studentdto());
        model.addAttribute("subjects", studentService.getAllSubjects());
        return "create_student";
    }

    @PostMapping
    public String saveStudent(@Valid @ModelAttribute("student") Studentdto studentDto,
                              BindingResult bindingResult,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
        	 model.addAttribute("subjects", studentService.getAllSubjects());
            return "create_student";
        }

        if (!studentService.isLastNameUnique(studentDto.getLastName())) {
            bindingResult.rejectValue("lastName", "duplicate", "Tên đã tồn tại");
            model.addAttribute("subjects", studentService.getAllSubjects());
            return "create_student";
        }

        if (!studentService.isEmailUnique(studentDto.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "Email đã tồn tại");
            model.addAttribute("subjects", studentService.getAllSubjects());
            return "create_student";
        }

        try {
            if (!imageFile.isEmpty()) {
                byte[] bytes = imageFile.getBytes();
                Path path = Paths.get("src/main/resources/static/uploads/" + imageFile.getOriginalFilename());
                Files.write(path, bytes);
                studentDto.setImageUrl("/uploads/" + imageFile.getOriginalFilename());
            }
            studentService.saveStudent(studentDto);
        } catch (IOException e) {
            model.addAttribute("student", studentDto);
            model.addAttribute("subjects", studentService.getAllSubjects());
            model.addAttribute("errorMessage", "Lỗi khi lưu ảnh: " + e.getMessage());
            return "create_student";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký sinh viên thành công!");
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("subjects", studentService.getAllSubjects());
        return "edit_student";
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") Studentdto studentDto,
                                BindingResult bindingResult,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("subjects", studentService.getAllSubjects());
            return "edit_student";
        }

        Student existingStudent = studentService.getStudentById(id);

        if (!existingStudent.getLastName().equals(studentDto.getLastName()) && !studentService.isLastNameUnique(studentDto.getLastName())) {
            bindingResult.rejectValue("lastName", "duplicate", "Tên đã tồn tại");
            model.addAttribute("subjects", studentService.getAllSubjects());
            return "edit_student";
        }

        if (!existingStudent.getEmail().equals(studentDto.getEmail()) && !studentService.isEmailUnique(studentDto.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "Email đã tồn tại");
            model.addAttribute("subjects", studentService.getAllSubjects());
            return "edit_student";
        }

        try {
            if (!imageFile.isEmpty()) {
                byte[] bytes = imageFile.getBytes();
                Path path = Paths.get("src/main/resources/static/uploads/" + imageFile.getOriginalFilename());
                Files.write(path, bytes);
                studentDto.setImageUrl("/uploads/" + imageFile.getOriginalFilename());
            }
            existingStudent.setFirstName(studentDto.getFirstName());
            existingStudent.setLastName(studentDto.getLastName());
            existingStudent.setEmail(studentDto.getEmail());
            existingStudent.setImageUrl(studentDto.getImageUrl());
            studentService.updateStudent(existingStudent);
        } catch (IOException e) {
            model.addAttribute("student", studentDto);
            model.addAttribute("subjects", studentService.getAllSubjects());
            model.addAttribute("errorMessage", "Lỗi khi cập nhật ảnh: " + e.getMessage());
            return "edit_student";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sinh viên thành công!");
        return "redirect:/students";
    }


    @GetMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
    @GetMapping("/{id}/registrations")
    public String getStudentRegistrations(@PathVariable Long id, Model model) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsByStudent(id);
        model.addAttribute("registrations", registrations);
        return "student_registrations";
    }
    
}
