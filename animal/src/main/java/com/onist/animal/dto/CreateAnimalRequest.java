package com.onist.animal.dto;

import java.time.LocalDate;

import com.onist.animal.model.Gender;
import com.onist.animal.model.Species;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAnimalRequest {

    @NotBlank(message="Le prénom de l'animal doit être renseigné")
    private String name;

    @NotBlank(message="Le prénom du propriétaire doit être renseigné")
    private String firstNameOfOwner;

    @NotBlank(message="Le nom du propriétaire doit être renseigné")
    private String lastNameOfOwner;

    @NotNull(message = "La date de naissance doit être renseignée")
    private LocalDate birthdate;

    @NotNull(message="La classification de l'animal doit être renseigné")
    private Species species;

    @NotBlank(message = "L'espèce précise de l'animal doit être renseignée")
    private String animalFamily;

    @NotNull(message="Le genre de l'animal doit être renseigné")
    private Gender gender;

    private String breed;

    private Double weight;

    private String chipNumber;
}
