package com.onist.animal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onist.animal.dto.AnimalResponse;
import com.onist.animal.dto.CreateAnimalRequest;
import com.onist.animal.dto.UpdateAnimalRequest;
import com.onist.animal.exception.AnimalNotFoundException;
import com.onist.animal.exception.ChipNumberAlreadyExistsException;
import com.onist.animal.feign.CommentaryFeign;
import com.onist.animal.model.AnimalModel;
import com.onist.animal.model.Owner;
import com.onist.animal.repository.AnimalRepository;
import com.onist.animal.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final CommentaryFeign commentaryFeign;
    private final OwnerRepository ownerRepository;

    // Recherche un Owner existant par prénom/nom (insensible à la casse), sinon le crée
    private Owner findOrCreateOwner(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            return null;
        }
        return ownerRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName)
                .orElseGet(() -> ownerRepository.save(
                        Owner.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .build()));
    }

    private AnimalResponse toAnimalResponse(AnimalModel animal) {

        return AnimalResponse.builder()
                .id(animal.getId())
                .name(animal.getName())
                .firstNameOfOwner(animal.getOwner().getFirstName())
                .lastNameOfOwner(animal.getOwner().getLastName())
                .firstNameOfSecondOwner(animal.getSecondOwner() != null ? animal.getSecondOwner().getFirstName() : null)
                .lastNameOfSecondOwner(animal.getSecondOwner() != null ? animal.getSecondOwner().getLastName() : null)
                .birthdate(animal.getBirthdate())
                .species(animal.getSpecies())
                .animalFamily(animal.getAnimalFamily())
                .breed(animal.getBreed())
                .gender(animal.getGender())
                .weight(animal.getWeight())
                .chipNumber(animal.getChipNumber())
                .build();
            }

    // Retrieves all animals in the system and maps them to AnimalResponse DTOs
    public List<AnimalResponse> getAllAnimals() {

        return animalRepository.findAll()
                .stream()
                .map(this::toAnimalResponse)
                .toList();
    }

    // Searches animals by their name, or by the first/last name of either owner
    public List<AnimalResponse> searchByName(String query) {
        return animalRepository.searchByNameOrOwner(query)
                .stream()
                .map(this::toAnimalResponse)
                .toList();
    }

    // Retrieves an animal by its unique id. Throws AnimalNotFoundException if not found
    public AnimalResponse animalById(Long id) {
        return toAnimalResponse(getAnimalOrThrow(id));
    }

    // Retrieves an animal by its unique id. Throws AnimalNotFoundException if not found
    private AnimalModel getAnimalOrThrow(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with id " + id));
    }

    // Creates a new animal in the system after checking if the chipNumber already exists. If the chipNumber is unique, saves the animal to the repository.
    public AnimalResponse createAnimal(CreateAnimalRequest request) {
         if (request.getChipNumber() != null && animalRepository.existsByChipNumber(request.getChipNumber())) {
            throw new ChipNumberAlreadyExistsException("Microchip number already in use: " + request.getChipNumber());
         }

        Owner owner = findOrCreateOwner(request.getFirstNameOfOwner(), request.getLastNameOfOwner());
        Owner secondOwner = findOrCreateOwner(request.getFirstNameOfSecondOwner(), request.getLastNameOfSecondOwner());
 

        AnimalModel animal = AnimalModel.builder()
                .name(request.getName())
                .birthdate(request.getBirthdate())
                .species(request.getSpecies())
                .animalFamily(request.getAnimalFamily())
                .breed(request.getBreed())
                .gender(request.getGender())
                .weight(request.getWeight())
                .chipNumber(request.getChipNumber())
                .owner(owner)
                .secondOwner(secondOwner)
                .build();

        AnimalModel savedAnimal = animalRepository.save(animal);
        return toAnimalResponse(savedAnimal);
    }

    // Retrieves a animal by their unique Id. If the animal is not found, it throws a AnimalNotFoundException
    public AnimalResponse updateAnimal(Long id, UpdateAnimalRequest request) {
       AnimalModel existingAnimal = getAnimalOrThrow(id);

        if(request.getChipNumber() != null
                && !request.getChipNumber().equals(existingAnimal.getChipNumber())
                && animalRepository.existsByChipNumber(request.getChipNumber())) {
            throw new ChipNumberAlreadyExistsException("Microchip number already in use: " + request.getChipNumber());
        }

        Owner owner = findOrCreateOwner(request.getFirstNameOfOwner(), request.getLastNameOfOwner());
        Owner secondOwner = findOrCreateOwner(request.getFirstNameOfSecondOwner(), request.getLastNameOfSecondOwner());


        existingAnimal.setName(request.getName());
        existingAnimal.setGender(request.getGender());
        existingAnimal.setSpecies(request.getSpecies());
        existingAnimal.setAnimalFamily(request.getAnimalFamily());
        existingAnimal.setBreed(request.getBreed());
        existingAnimal.setBirthdate(request.getBirthdate());
        existingAnimal.setWeight(request.getWeight());
        existingAnimal.setChipNumber(request.getChipNumber());
        existingAnimal.setOwner(owner);
        existingAnimal.setSecondOwner(secondOwner);

        AnimalModel updatedAnimal = animalRepository.save(existingAnimal);
        return toAnimalResponse(updatedAnimal);
    }

    // Deletes a animal by their unique Id. If the animal is not found, it throws a AnimalNotFoundException
    public void deleteAnimalById(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new AnimalNotFoundException("Animal not found with id " + id);
        }
        commentaryFeign.deleteAllCommentaryByAnimalId(id);
        animalRepository.deleteById(id);
    }
}
