package com.pfa.finance_erp.invoicing.repository;

import com.pfa.finance_erp.invoicing.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    // Récupérer tous les paiements d'une facture
    List<Payment> findByInvoiceId(Long invoiceId);
}