package com.pfa.finance_erp.invoicing.service;

import com.pfa.finance_erp.invoicing.Entity.Payment;
import com.pfa.finance_erp.invoicing.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment enregistrerPaiement(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> listerTousLesPaiements() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> chercherParId(Long id) {
        return paymentRepository.findById(id);
    }

    public void supprimerPaiement(Long id) {
        paymentRepository.deleteById(id);
    }
}