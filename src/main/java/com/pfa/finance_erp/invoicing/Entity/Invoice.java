package com.pfa.finance_erp.invoicing.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le numéro de facture est obligatoire")
    @Size(max = 50, message = "Le numéro de facture ne doit pas dépasser 50 caractères")
    @Column(name = "numero", nullable = false, unique = true, length = 50)
    private String numero;

    @NotNull(message = "La date de facture est obligatoire")
    @Column(name = "date_facture", nullable = false)
    private LocalDate dateFacture;

    @NotNull(message = "Le montant HT est obligatoire")
    @DecimalMin(value = "0.0", inclusive = true, message = "Le montant HT doit être positif ou nul")
    @Column(name = "montant_ht", nullable = false, precision = 12, scale = 2)
    private BigDecimal montantHt;

    @NotNull(message = "Le montant de TVA est obligatoire")
    @DecimalMin(value = "0.0", inclusive = true, message = "La TVA doit être positive ou nulle")
    @Column(name = "tva", nullable = false, precision = 12, scale = 2)
    private BigDecimal tva;

    @NotNull(message = "Le montant TTC est obligatoire")
    @DecimalMin(value = "0.0", inclusive = true, message = "Le montant TTC doit être positif ou nul")
    @Column(name = "montant_ttc", nullable = false, precision = 12, scale = 2)
    private BigDecimal montantTtc;

    @NotBlank(message = "Le statut est obligatoire")
    @Size(max = 20, message = "Le statut ne doit pas dépasser 20 caractères")
    @Column(name = "statut", nullable = false, length = 20)
    private String statut;

    @NotNull(message = "Le client est obligatoire")
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Invoice() {
    }

    public Invoice(String numero, LocalDate dateFacture, BigDecimal montantHt, BigDecimal tva, BigDecimal montantTtc, String statut, Client client) {
        this.numero = numero;
        this.dateFacture = dateFacture;
        this.montantHt = montantHt;
        this.tva = tva;
        this.montantTtc = montantTtc;
        this.statut = statut;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public BigDecimal getMontantHt() {
        return montantHt;
    }

    public void setMontantHt(BigDecimal montantHt) {
        this.montantHt = montantHt;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getMontantTtc() {
        return montantTtc;
    }

    public void setMontantTtc(BigDecimal montantTtc) {
        this.montantTtc = montantTtc;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}