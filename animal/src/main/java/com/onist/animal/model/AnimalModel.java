package com.onist.animal.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="owner_id", nullable = false)
    private Owner owner;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="second_owner_id")
    private Owner secondOwner;

    @Column(name="birth_date")
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name="species", nullable = false)
    private Species species;

    @Column(name = "animal_family", nullable = false)
    private String animalFamily;

    @Column(name="breed")
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name="gender", nullable = false)
    private Gender gender;

    @Column(name="weight")
    private Double weight;

    @Column(name="chip_number", unique = true)
    private String chipNumber;
}
