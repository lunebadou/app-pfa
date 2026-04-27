package com.pfa.finance_erp.invoicing.controller;

import com.pfa.finance_erp.invoicing.dto.FinanceSummary;
import com.pfa.finance_erp.invoicing.service.FinanceSummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinanceSummaryController {

    private final FinanceSummaryService financeSummaryService;

    public FinanceSummaryController(FinanceSummaryService financeSummaryService) {
        this.financeSummaryService = financeSummaryService;
    }

    @GetMapping("/api/finance/summary")
    public FinanceSummary obtenirSynthese() {
        return financeSummaryService.calculerSynthese();
    }
}