//package test.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import test.entity.PaymentDTO;
//import test.service.PaymentService;
//
//import java.util.List;
//
//
//@Controller
//public class PaymentViewController {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @GetMapping("/payments/view/{studentId}")
//    public String viewPayments(@PathVariable Long studentId, Model model) {
//        List<PaymentDTO> paidPayments = paymentService.getPaymentsByStudentId(studentId, "PAID");
//        List<PaymentDTO> unpaidPayments = paymentService.getPaymentsByStudentId(studentId, "UNPAID");
//
//        model.addAttribute("paidPayments", paidPayments);
//        model.addAttribute("unpaidPayments", unpaidPayments);
//        return "payments"; // name of the Thymeleaf template
//    }
//}
