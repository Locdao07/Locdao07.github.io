package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import test.Repository.PaymentRepository;

import test.entity.Payment;
import test.entity.PaymentDTO;
import test.entity.RegistrationDTO;
import test.entity.Student;
import test.entity.Studentdto;
import test.entity.Subject;
import test.entity.Subjectdto;
import test.service.PaymentService;
import test.service.RegistrationService;
import test.service.StudentService;
import test.service.SubjectService;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/payments")
@RequestMapping("/admin/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private RegistrationService registrationService;
  
    @Autowired
    public PaymentController(PaymentService paymentService,StudentService studentService,SubjectService subjectService) {
        this.paymentService = paymentService;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }
    

    @GetMapping
    public String showPaymentsPage() {
        return "admin-payments"; // Tên của template Thymeleaf để hiển thị trang thanh toán cho admin
    }
    @GetMapping("/search")
    public String searchStudent(@RequestParam String email, Model model) {
        Student student = studentService.findByEmail(email);
        if (student != null) {
            List<RegistrationDTO> registrations = registrationService.getRegistrationsByStudent(student.getId());
            model.addAttribute("student", student);
            model.addAttribute("registrations", registrations);

            // Check if the student has already paid
            boolean hasPaid = paymentService.hasPaid(student.getId());
            model.addAttribute("hasPaid", hasPaid);
            if (hasPaid) {
                model.addAttribute("message", "Bạn đã thanh toán học phí.");
            }
        } else {
            model.addAttribute("error", "Student not found");
        }
        return "admin-payments";
    }

    @PostMapping("/pay")
    public String pay(@RequestParam Long studentId, @RequestParam List<Long> registrationIds, Model model) {
        if (registrationIds == null || registrationIds.isEmpty()) {
            model.addAttribute("error", "No subjects selected for payment.");
            return "admin-payments";
        }

        try {
            // Tính tổng số tiền dựa trên các đăng ký được chọn
            Double totalFee = paymentService.calculateTotalFee(registrationIds);
            
            // Thực hiện thanh toán
            paymentService.pay(studentId, registrationIds, totalFee);

            // Add success message
            model.addAttribute("success", "Payment successful.");
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while processing the payment: " + e.getMessage());
            return "admin-payments";
        }

        // Redirect to the search page with a success message
        return "redirect:/admin/payments/search?email=" + studentService.getStudentById(studentId).getEmail();
    }




}

