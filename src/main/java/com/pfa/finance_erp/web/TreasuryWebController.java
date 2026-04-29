package com.pfa.finance_erp.web;

import com.pfa.finance_erp.invoicing.Entity.Invoice;
import com.pfa.finance_erp.invoicing.Entity.Payment;
import com.pfa.finance_erp.invoicing.service.InvoiceService;
import com.pfa.finance_erp.invoicing.service.TreasuryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class TreasuryWebController {

    private final TreasuryService treasuryService;
    private final InvoiceService invoiceService;

    public TreasuryWebController(TreasuryService treasuryService, InvoiceService invoiceService) {
        this.treasuryService = treasuryService;
        this.invoiceService = invoiceService;
    }

    /**
     * Afficher la page de trésorerie avec liste des paiements
     */
    @GetMapping("/treasury")
    public String afficherTresorerie(Model model) {
        Payment payment = new Payment();
        payment.setDatePaiement(LocalDate.now());

        model.addAttribute("payments", treasuryService.listerTousLesPaiements());
        model.addAttribute("invoices", invoiceService.listerToutesLesFactures());
        model.addAttribute("paymentForm", payment);
        model.addAttribute("totalEncaisse", treasuryService.calculerTotalDesEncaissements());

        return "treasury";
    }

    /**
     * Enregistrer un nouveau paiement
     */
    @PostMapping("/treasury")
    public String enregistrerPaiement(
            @Valid @ModelAttribute("paymentForm") Payment payment,
            BindingResult bindingResult,
            Model model) {

        // Vérifier que la facture sélectionnée existe
        if (payment.getInvoice() == null || payment.getInvoice().getId() == null) {
            bindingResult.rejectValue("invoice", "invoice.invalide", "La facture est obligatoire");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("payments", treasuryService.listerTousLesPaiements());
            model.addAttribute("invoices", invoiceService.listerToutesLesFactures());
            model.addAttribute("totalEncaisse", treasuryService.calculerTotalDesEncaissements());
            return "treasury";
        }

        // Récupérer la facture complète
        Optional<Invoice> invoiceOpt = invoiceService.chercherParId(payment.getInvoice().getId());
        if (invoiceOpt.isEmpty()) {
            bindingResult.rejectValue("invoice", "invoice.invalide", "La facture sélectionnée est introuvable");
            model.addAttribute("payments", treasuryService.listerTousLesPaiements());
            model.addAttribute("invoices", invoiceService.listerToutesLesFactures());
            model.addAttribute("totalEncaisse", treasuryService.calculerTotalDesEncaissements());
            return "treasury";
        }

        payment.setInvoice(invoiceOpt.get());
        treasuryService.enregistrerPaiement(payment);

        return "redirect:/treasury";
    }
}