package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import test.entity.RegistrationDTO;
import test.entity.Subjectdto;
import test.service.RegistrationService;
import test.service.SubjectService;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String viewSubjects(Model model) {
        List<Subjectdto> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjects";
    }

    @GetMapping("/search")
    public ResponseEntity<List<Subjectdto>> searchSubjects(@RequestParam("keyword") String keyword) {
        List<Subjectdto> subjects = subjectService.searchSubjects(keyword);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/new")
    public String showSubjectForm(Model model) {
        model.addAttribute("subject", new Subjectdto());
        return "create_subject";
    }

    @PostMapping("/new")
    public String addSubject(@Valid @ModelAttribute("subject") Subjectdto subjectDto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "create_subject";
        }
        boolean subjectNameExists = !subjectService.isSubjectNameUnique(subjectDto.getSubjectName(), null);
        boolean subjectCodeExists = !subjectService.isSubjectCodeUnique(subjectDto.getSubjectCode(), null);

        if (subjectNameExists) {
            model.addAttribute("subjectNameError", "Subject name already exists");
        }
        if (subjectCodeExists) {
            model.addAttribute("subjectCodeError", "Subject code already exists");
        }
        if (subjectNameExists || subjectCodeExists) {
            return "create_subject";
        }

        subjectService.saveSubject(subjectDto);
        redirectAttributes.addFlashAttribute("successMessage", "Subject created successfully!");
        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String showEditSubjectForm(@PathVariable Long id, Model model) {
        Subjectdto subjectDto = subjectService.getSubjectById(id);
        if (subjectDto == null) {
            return "error/404"; // or some appropriate error page
        }
        model.addAttribute("subject", subjectDto);
        return "edit_subject";
    }

    @PostMapping("/edit/{id}")
    public String updateSubject(@PathVariable Long id,
                                @Valid @ModelAttribute("subject") Subjectdto subjectDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "edit_subject";
        }
        boolean subjectNameExists = !subjectService.isSubjectNameUnique(subjectDto.getSubjectName(), id);
        boolean subjectCodeExists = !subjectService.isSubjectCodeUnique(subjectDto.getSubjectCode(), id);

        if (subjectNameExists) {
            bindingResult.rejectValue("subjectName", "error.subject", "Subject name already exists");
        }
        if (subjectCodeExists) {
            bindingResult.rejectValue("subjectCode", "error.subject", "Subject code already exists");
        }
        if (subjectNameExists || subjectCodeExists) {
            return "edit_subject";
        }

        subjectDto.setId(id);
        subjectService.updateSubject(subjectDto);
        return "redirect:/subjects";
    }


    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        subjectService.deleteSubjectById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Subject deleted successfully!");
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/registrations")
    public String getRegistrationsBySubject(@PathVariable Long id, Model model) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsBySubject(id);
        model.addAttribute("registrations", registrations);
        return "registrations_by_subject";
    }
}
