package com.pfa.finance_erp.invoicing.service;

import com.pfa.finance_erp.invoicing.Entity.Invoice;
import com.pfa.finance_erp.invoicing.Entity.Payment;
import com.pfa.finance_erp.invoicing.dto.FinanceSummary;
import com.pfa.finance_erp.invoicing.repository.InvoiceRepository;
import com.pfa.finance_erp.invoicing.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinanceSummaryService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public FinanceSummaryService(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    public FinanceSummary calculerSynthese() {
        List<Invoice> factures = invoiceRepository.findAll();
        List<Payment> paiements = paymentRepository.findAll();

        BigDecimal totalFacture = BigDecimal.ZERO;
        BigDecimal totalEncaisse = BigDecimal.ZERO;

        for (Invoice facture : factures) {
            if (facture.getMontantTtc() != null) {
                totalFacture = totalFacture.add(facture.getMontantTtc());
            }
        }

        for (Payment paiement : paiements) {
            if (paiement.getMontant() != null) {
                totalEncaisse = totalEncaisse.add(paiement.getMontant());
            }
        }

        BigDecimal resteAEncaisser = totalFacture.subtract(totalEncaisse);

        return new FinanceSummary(totalFacture, totalEncaisse, resteAEncaisser);
    }
}