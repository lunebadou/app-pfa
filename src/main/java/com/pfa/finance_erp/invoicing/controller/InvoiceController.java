package com.pfa.finance_erp.invoicing.controller;

import com.pfa.finance_erp.invoicing.Entity.Invoice;
import com.pfa.finance_erp.invoicing.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Invoice creerFacture(@Valid @RequestBody Invoice invoice) {
        return invoiceService.enregistrerFacture(invoice);
    }

    @GetMapping
    public List<Invoice> listerToutesLesFactures() {
        return invoiceService.listerToutesLesFactures();
    }

    @GetMapping("/{id}")
    public Optional<Invoice> chercherFactureParId(@PathVariable Long id) {
        return invoiceService.chercherParId(id);
    }

    @DeleteMapping("/{id}")
    public void supprimerFacture(@PathVariable Long id) {
        invoiceService.supprimerFacture(id);
    }
}