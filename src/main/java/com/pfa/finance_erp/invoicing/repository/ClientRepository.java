package com.pfa.finance_erp.invoicing.repository;

import com.pfa.finance_erp.invoicing.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
