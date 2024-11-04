package test.service;

import java.util.List;

import test.entity.Payment;
import test.entity.PaymentDTO;
//import test.entity.PaymentInfo;

public interface PaymentService {
//	Payment createPayment(PaymentDTO paymentDTO);
//    List<Payment> getAllPayments();
    
//    List<PaymentDTO> getPaymentsByStudent(Long studentId);
//    void savePayment(PaymentDTO paymentDTO);
	List<PaymentDTO> getPaymentsByStudent(Long studentId);
	void pay(Long studentId, List<Long> registrationIds, Double totalFee);
	Double calculateTotalFee(List<Long> registrationIds);
	boolean hasPaid(Long studentId);
	//List<PaymentDTO> getPaidStudents();
}
