package com.pfa.finance_erp.invoicing.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clients")
public class Client {

    // Identifiant unique du client
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom du client obligatoire
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    // Email obligatoire et valide
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    // Téléphone facultatif
    @Size(max = 30, message = "Le téléphone ne doit pas dépasser 30 caractères")
    @Column(name = "telephone", length = 30)
    private String telephone;

    // Adresse facultative
    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    @Column(name = "adresse", length = 255)
    private String adresse;

    // Constructeur vide obligatoire pour JPA
    public Client() {
    }

    // Constructeur pratique
    public Client(String nom, String email, String telephone, String adresse) {
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    // Getter de id
    public Long getId() {
        return id;
    }

    // Setter de id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter de nom
    public String getNom() {
        return nom;
    }

    // Setter de nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter de email
    public String getEmail() {
        return email;
    }

    // Setter de email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter de telephone
    public String getTelephone() {
        return telephone;
    }

    // Setter de telephone
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Getter de adresse
    public String getAdresse() {
        return adresse;
    }

    // Setter de adresse
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
