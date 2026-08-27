package com.onist.animal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onist.animal.exception.AnimalNotFoundException;
import com.onist.animal.exception.ChipNumberAlreadyExistsException;
import com.onist.animal.feign.CommentaryFeign;
import com.onist.animal.model.AnimalModel;
import com.onist.animal.repository.AnimalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final CommentaryFeign commentaryFeign;

    // Retrieves a animal by their unique Id. If the animal is not found, it throws a AnimalNotFoundException.
    public List<AnimalModel> findAllAnimals() {
        return animalRepository.findAll();
    }

    // Searches animals by their name, or by the first/last name of either owner
    public List<AnimalModel> searchByName(String query) {
        return animalRepository.searchByNameOrOwner(query);
    }

    // Retrieves an animal by its unique id. Throws AnimalNotFoundException if not found
    public AnimalModel animalById(long id) { 
        return animalRepository.findById(id)
        .orElseThrow(()-> new AnimalNotFoundException("Animal not found with id " + id));
    }

    // Creates a new animal in the system after checking if the chipNumber already exists. If the chipNumber is unique, saves the animal to the repository.
    public AnimalModel createAnimal(AnimalModel animal) {
        if (animal.getChipNumber() != null && animalRepository.existsByChipNumber(animal.getChipNumber())) {
        throw new ChipNumberAlreadyExistsException("Microchip number already in use: " + animal.getChipNumber());
        }
        return animalRepository.save(animal);
    }

    // Retrieves a animal by their unique Id. If the animal is not found, it throws a AnimalNotFoundException
    public AnimalModel updateAnimal(Long id, AnimalModel updatedAnimal) {
        AnimalModel existingAnimal = animalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with id " + id));

        if(updatedAnimal.getChipNumber() != null
                && !updatedAnimal.getChipNumber().equals(existingAnimal.getChipNumber())
                && animalRepository.existsByChipNumber(updatedAnimal.getChipNumber())) {
            throw new ChipNumberAlreadyExistsException("Microchip number already in use: " + updatedAnimal.getChipNumber());
        }
        existingAnimal.setName(updatedAnimal.getName());
        existingAnimal.setGender(updatedAnimal.getGender());
        existingAnimal.setSpecies(updatedAnimal.getSpecies());
        existingAnimal.setAnimalFamily(updatedAnimal.getAnimalFamily());
        existingAnimal.setBreed(updatedAnimal.getBreed());
        existingAnimal.setBirthdate(updatedAnimal.getBirthdate());
        existingAnimal.setWeight(updatedAnimal.getWeight());
        existingAnimal.setChipNumber(updatedAnimal.getChipNumber());
        existingAnimal.setFirstNameOfOwner(updatedAnimal.getFirstNameOfOwner());
        existingAnimal.setLastNameOfOwner(updatedAnimal.getLastNameOfOwner());
        existingAnimal.setFirstNameOfSecondOwner(updatedAnimal.getFirstNameOfSecondOwner());
        existingAnimal.setLastNameOfSecondOwner(updatedAnimal.getLastNameOfSecondOwner());

        return animalRepository.save(existingAnimal);
    }

    // Deletes a animal by their unique Id. If the animal is not found, it throws a AnimalNotFoundException
    public void deleteAnimalById(long id) {
        if (!animalRepository.existsById(id)) {
            throw new AnimalNotFoundException("Animal not found with id " + id);
        }
        commentaryFeign.deleteAllCommentaryByAnimalId(id);
        animalRepository.deleteById(id);
    }
}
