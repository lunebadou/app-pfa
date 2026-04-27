package com.pfa.finance_erp.invoicing.controller;


import com.pfa.finance_erp.invoicing.Entity.Client;
import com.pfa.finance_erp.invoicing.service.ClientService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client creerClient(@Valid @RequestBody Client client) {
        return clientService.enregistrerClient(client);
    }

    @GetMapping
    public List<Client> listerTousLesClients() {
        return clientService.listerTousLesClients();
    }

    @GetMapping("/{id}")
    public Optional<Client> chercherClientParId(@PathVariable Long id) {
        return clientService.chercherParId(id);
    }

    @DeleteMapping("/{id}")
    public void supprimerClient(@PathVariable Long id) {
        clientService.supprimerClient(id);
    }
}
