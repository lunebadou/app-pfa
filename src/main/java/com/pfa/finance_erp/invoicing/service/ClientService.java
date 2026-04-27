package com.pfa.finance_erp.invoicing.service;

import com.pfa.finance_erp.invoicing.Entity.Client;
import com.pfa.finance_erp.invoicing.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client enregistrerClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> listerTousLesClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> chercherParId(Long id) {
        return clientRepository.findById(id);
    }

    public void supprimerClient(Long id) {
        clientRepository.deleteById(id);
    }
}
