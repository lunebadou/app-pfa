package com.pfa.finance_erp.invoicing.repository;

import com.pfa.finance_erp.invoicing.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}