package com.pfa.finance_erp.web;

import com.pfa.finance_erp.invoicing.Entity.Client;
import com.pfa.finance_erp.invoicing.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientWebController {

    private final ClientService clientService;

    public ClientWebController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String afficherClients(Model model) {
        model.addAttribute("clients", clientService.listerTousLesClients());
        model.addAttribute("clientForm", new Client());
        return "clients";
    }

    @PostMapping("/clients")
    public String enregistrerClient(
            @Valid @ModelAttribute("clientForm") Client client,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientService.listerTousLesClients());
            return "clients";
        }

        clientService.enregistrerClient(client);
        return "redirect:/clients";
    }
}