package com.onist.animal.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="animals")
public class AnimalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    @NotBlank(message="Le prénom de l'animal doit être renseigné")
    private String name;

    @Column(name="firstName_of_owner", nullable = false)
    @NotBlank(message="Le prénom du propriétaire doit être renseigné")
    private String firstNameOfOwner;

    @Column(name="lastName_of_owner", nullable = false)
    @NotBlank(message="Le nom du propriétaire doit être renseigné")
    private String lastNameOfOwner;

     @Column(name="firstName_of_second_owner")
    private String firstNameOfSecondOwner;

    @Column(name="lastName_of_second_owner")
    private String lastNameOfSecondOwner;

    @Column(name="Birth_date")
    @NotNull(message = "La date de naissance doit être renseignée")
    private LocalDate birthdate;

    @Column(name="species", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message="La classification de l'animal doit être renseigné")
    private Species species;

    @Column(name = "animal_family", nullable = false)
    @NotBlank(message = "L'espèce précise de l'animal doit être renseignée")
    private String animalFamily;

    @Column(name="breed")
    private String breed;

    @Column(name="gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message="Le genre de l'animal doit être renseigné")
    private Gender gender;

    @Column(name="weight")
    private double weight;

    @Column(name="chip_number", unique = true)
    private String chipNumber;
}
