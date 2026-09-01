package com.onist.animal.dto;

import java.time.LocalDate;

import com.onist.animal.model.Gender;
import com.onist.animal.model.Species;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnimalResponse {

    private Long id;
    private String name;
    private String firstNameOfOwner;
    private String lastNameOfOwner;
    private String firstNameOfSecondOwner;
    private String lastNameOfSecondOwner;
    private LocalDate birthdate;
    private Species species;
    private String animalFamily;
    private String breed;
    private Gender gender;
    private double weight;
    private String chipNumber;
}
