package com.pfa.finance_erp.web;

import com.pfa.finance_erp.invoicing.dto.FinanceSummary;
import com.pfa.finance_erp.invoicing.service.FinanceSummaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final FinanceSummaryService financeSummaryService;

    public DashboardController(FinanceSummaryService financeSummaryService) {
        this.financeSummaryService = financeSummaryService;
    }

    @GetMapping({"/", "/dashboard"})
    public String afficherDashboard(Model model) {
        FinanceSummary synthese = financeSummaryService.calculerSynthese();
        model.addAttribute("synthese", synthese);
        return "dashboard";
    }
}