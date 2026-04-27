package com.pfa.finance_erp.invoicing.dto;

import java.math.BigDecimal;

public class FinanceSummary {

    private BigDecimal totalFacture;
    private BigDecimal totalEncaisse;
    private BigDecimal resteAEncaisser;

    public FinanceSummary() {
    }

    public FinanceSummary(BigDecimal totalFacture, BigDecimal totalEncaisse, BigDecimal resteAEncaisser) {
        this.totalFacture = totalFacture;
        this.totalEncaisse = totalEncaisse;
        this.resteAEncaisser = resteAEncaisser;
    }

    public BigDecimal getTotalFacture() {
        return totalFacture;
    }

    public void setTotalFacture(BigDecimal totalFacture) {
        this.totalFacture = totalFacture;
    }

    public BigDecimal getTotalEncaisse() {
        return totalEncaisse;
    }

    public void setTotalEncaisse(BigDecimal totalEncaisse) {
        this.totalEncaisse = totalEncaisse;
    }

    public BigDecimal getResteAEncaisser() {
        return resteAEncaisser;
    }

    public void setResteAEncaisser(BigDecimal resteAEncaisser) {
        this.resteAEncaisser = resteAEncaisser;
    }
}