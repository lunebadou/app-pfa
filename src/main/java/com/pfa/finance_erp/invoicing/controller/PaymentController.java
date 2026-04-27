package com.pfa.finance_erp.invoicing.controller;

import com.pfa.finance_erp.invoicing.Entity.Payment;
import com.pfa.finance_erp.invoicing.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment creerPaiement(@Valid @RequestBody Payment payment) {
        return paymentService.enregistrerPaiement(payment);
    }

    @GetMapping
    public List<Payment> listerTousLesPaiements() {
        return paymentService.listerTousLesPaiements();
    }

    @GetMapping("/{id}")
    public Optional<Payment> chercherPaiementParId(@PathVariable Long id) {
        return paymentService.chercherParId(id);
    }

    @DeleteMapping("/{id}")
    public void supprimerPaiement(@PathVariable Long id) {
        paymentService.supprimerPaiement(id);
    }
}