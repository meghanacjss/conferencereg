package com.example.conference_reg.service;
import com.example.conference_reg.entity.Payment;
import com.example.conference_reg.entity.Registration;
import com.example.conference_reg.model.PaymentModel;
import com.example.conference_reg.repository.PaymentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
@Service
public class PaymentService {
@Autowired
private PaymentRepository paymentRepository;
    public PaymentModel createPayment(@Valid PaymentModel paymentModel) {
        Payment payment = convertToEntity(paymentModel);
        Payment savedPayment = paymentRepository.save(payment);
        return convertToModel(savedPayment);
    }

//    public PaymentModel createPayment(@Valid PaymentModel paymentModel, Registration registration) {
//        Payment payment = convertToEntity(paymentModel);
//        payment.setRegistration(registration);
//        Payment savedPayment = paymentRepository.save(payment);
//       return convertToModel(savedPayment);
//    }

    public List<PaymentModel> getAllPayments(){
        return paymentRepository.findAll().stream()
                .filter(payment -> payment.getAmount() > 500)
                .sorted(Comparator.comparing(Payment::getPaymentDate))
                .map(this::convertToModel)
                .toList();
    }
    public List<PaymentModel> getPaymentsByRegistration(Registration registration) {
        List<Payment> payments = paymentRepository.findByRegistration(registration);
        return payments.stream()
                .map(this::convertToModel)
                .toList();
    }
    public long calculateTotalPayments(Registration registration) {
        List<Payment> payments = paymentRepository.findByRegistration(registration);
        return payments.stream().mapToLong(Payment::getAmount).sum();
    }

    public PaymentModel convertToModel(Payment payment) {
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setPid(payment.getPid());
        paymentModel.setAmount(payment.getAmount());
        paymentModel.setPaymentDate(payment.getPaymentDate());
        paymentModel.setRegistration(payment.getRegistration());
        return paymentModel;
    }

    public Payment convertToEntity(PaymentModel paymentModel) {
        Payment payment = new Payment();
        payment.setPid(paymentModel.getPid());
        payment.setAmount(paymentModel.getAmount());
        payment.setPaymentDate(paymentModel.getPaymentDate());
        payment.setRegistration(paymentModel.getRegistration());
        return payment;
    }
}
