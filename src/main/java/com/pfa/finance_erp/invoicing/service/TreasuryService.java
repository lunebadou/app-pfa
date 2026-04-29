package com.pfa.finance_erp.invoicing.service;

import com.pfa.finance_erp.invoicing.Entity.Invoice;
import com.pfa.finance_erp.invoicing.Entity.Payment;
import com.pfa.finance_erp.invoicing.repository.InvoiceRepository;
import com.pfa.finance_erp.invoicing.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TreasuryService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public TreasuryService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Enregistrer un nouveau paiement pour une facture
     */
    public Payment enregistrerPaiement(Payment payment) {
        return paymentRepository.save(payment);
    }

    /**
     * Lister tous les paiements
     */
    public List<Payment> listerTousLesPaiements() {
        return paymentRepository.findAll();
    }

    /**
     * Obtenir les paiements d'une facture spécifique
     */
    public List<Payment> obtenirPaiementsParFacture(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }

    /**
     * Calculer le total payé pour une facture
     */
    public BigDecimal calculerTotalPayeParFacture(Long invoiceId) {
        List<Payment> payments = paymentRepository.findByInvoiceId(invoiceId);
        return payments.stream()
                .map(Payment::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculer le montant restant à payer pour une facture
     */
    public BigDecimal calculerResteAPayerParFacture(Long invoiceId) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        if (invoiceOpt.isEmpty()) {
            return BigDecimal.ZERO;
        }

        Invoice invoice = invoiceOpt.get();
        BigDecimal totalPaye = calculerTotalPayeParFacture(invoiceId);
        return invoice.getMontantTtc().subtract(totalPaye);
    }

    /**
     * Calculer le total des paiements enregistrés
     */
    public BigDecimal calculerTotalDesEncaissements() {
        List<Payment> payments = listerTousLesPaiements();
        return payments.stream()
                .map(Payment::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Vérifier si une facture est entièrement payée
     */
    public boolean estFacturePayee(Long invoiceId) {
        BigDecimal resteAPayer = calculerResteAPayerParFacture(invoiceId);
        return resteAPayer.compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * Supprimer un paiement
     */
    public void supprimerPaiement(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}