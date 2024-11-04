package test.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import test.entity.RegistrationDTO;
import test.service.RegistrationService;
import test.service.StudentService;
import test.service.SubjectService;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String getAllRegistrations(Model model,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size) {
        Page<RegistrationDTO> registrationPage = registrationService.getPaginatedRegistrations(PageRequest.of(page, size));
        model.addAttribute("registrationPage", registrationPage);
        return "registrations";
    }

    @GetMapping("/new")
    public String createRegistrationForm(Model model) {
        model.addAttribute("registration", new RegistrationDTO());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "create_registration";
    }

    @PostMapping
    public String saveRegistration(@ModelAttribute("registration") RegistrationDTO registrationDTO) {
        registrationService.saveRegistration(registrationDTO);
        return "redirect:/registrations";
    }

    @GetMapping("/delete/{id}")
    public String deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return "redirect:/registrations";
    }

    @GetMapping("/approve/{id}")
    public String approveRegistration(@PathVariable Long id) {
        registrationService.approveRegistration(id);
        return "redirect:/registrations";
    }

    @GetMapping("/subject/{id}")
    public String getRegistrationsBySubject(@PathVariable Long id, Model model) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsBySubject(id);
        model.addAttribute("registrations", registrations);
        return "registrations_by_subject";
    }


        @GetMapping("/students/{studentId}/registrations")
        public String getRegistrationsByStudentId(@PathVariable("studentId") Long studentId, Model model) {
            List<RegistrationDTO> registrations = registrationService.getRegistrationsByStudent(studentId);
            Double totalFee = registrationService.getTotalFeeByStudent(studentId);
            model.addAttribute("registrations", registrations);
            model.addAttribute("totalFee", totalFee);
            return "registrations/student-registrations";
        }
}
