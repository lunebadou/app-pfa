package com.pfa.finance_erp.invoicing.service;

import com.pfa.finance_erp.invoicing.Entity.Invoice;
import com.pfa.finance_erp.invoicing.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice enregistrerFacture(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> listerToutesLesFactures() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> chercherParId(Long id) {
        return invoiceRepository.findById(id);
    }

    public void supprimerFacture(Long id) {
        invoiceRepository.deleteById(id);
    }
}