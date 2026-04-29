package com.pfa.finance_erp.web;

import com.pfa.finance_erp.invoicing.Entity.Client;
import com.pfa.finance_erp.invoicing.Entity.Invoice;
import com.pfa.finance_erp.invoicing.service.ClientService;
import com.pfa.finance_erp.invoicing.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InvoiceWebController {

    private final InvoiceService invoiceService;
    private final ClientService clientService;

    public InvoiceWebController(InvoiceService invoiceService, ClientService clientService) {
        this.invoiceService = invoiceService;
        this.clientService = clientService;
    }

    @GetMapping("/invoices")
    public String afficherFactures(Model model) {
        Invoice invoice = new Invoice();
        invoice.setClient(new Client());

        model.addAttribute("invoices", invoiceService.listerToutesLesFactures());
        model.addAttribute("clients", clientService.listerTousLesClients());
        model.addAttribute("invoiceForm", invoice);

        return "invoices";
    }

    @PostMapping("/invoices")
    public String enregistrerFacture(
            @Valid @ModelAttribute("invoiceForm") Invoice invoice,
            BindingResult bindingResult,
            Model model) {

        if (invoice.getClient() == null || invoice.getClient().getId() == null) {
            bindingResult.rejectValue("client", "client.invalide", "Le client est obligatoire");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("invoices", invoiceService.listerToutesLesFactures());
            model.addAttribute("clients", clientService.listerTousLesClients());
            return "invoices";
        }

        Client client = clientService.chercherParId(invoice.getClient().getId()).orElse(null);

        if (client == null) {
            bindingResult.rejectValue("client", "client.invalide", "Le client sélectionné est introuvable");
            model.addAttribute("invoices", invoiceService.listerToutesLesFactures());
            model.addAttribute("clients", clientService.listerTousLesClients());
            return "invoices";
        }

        invoice.setClient(client);
        invoiceService.enregistrerFacture(invoice);

        return "redirect:/invoices";
    }
}