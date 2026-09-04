package com.onist.animal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.onist.animal.dto.AnimalResponse;
import com.onist.animal.dto.CreateAnimalRequest;
import com.onist.animal.dto.UpdateAnimalRequest;
import com.onist.animal.service.AnimalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// This class is a REST controller that handles HTTP requests related to animal entities
@RestController
@RequestMapping("/api/v1/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    // Handles the creation of a new animal entity. It accepts a POST request with a CreateAnimalRequest payload, 
    // validates it, and returns the created AnimalResponse with a 201 Created status
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AnimalResponse> createAnimal(
        @Valid @RequestBody CreateAnimalRequest request) {

        AnimalResponse created = animalService.createAnimal(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Handles the search for animal entities by name. It accepts a GET request with a query parameter and
    //  returns a list of AnimalResponse objects that match the search criteria with a 200 OK status
    @GetMapping("/search")
    public ResponseEntity<List<AnimalResponse>> searchAnimals(@RequestParam String query) {
        return ResponseEntity.ok(animalService.searchByName(query));
    }

    // Retrieves all animal entities. It accepts a GET request and 
    // returns a list of AnimalResponse objects with a 200 OK status
    @GetMapping("/all")
    public ResponseEntity<List<AnimalResponse>> findAll() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    // Retrieves a specific animal entity by its ID. It accepts a GET request with a path variable and
    //  returns the corresponding AnimalResponse object with a 200 OK status
    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> animalById(@PathVariable Long id) {
       return ResponseEntity.ok(animalService.animalById(id));
    }

    // Updates an existing animal entity. It accepts a PUT request with a path variable for the animal ID and
    //  a UpdateAnimalRequest payload, validates it, and returns the updated AnimalResponse with a 200 OK status
    @PutMapping("/update/{id}")
    public ResponseEntity<AnimalResponse> updateAnimal (
        @PathVariable Long id, 
        @Valid @RequestBody UpdateAnimalRequest request) {

        AnimalResponse updated = animalService.updateAnimal(id, request);

        return ResponseEntity.ok(updated);
    }


    // Deletes an existing animal entity by its ID. It accepts a DELETE request with a path variable for the animal ID
    //  and returns a 204 No Content status if the deletion is successful
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {

        animalService.deleteAnimalById(id);

        return ResponseEntity.noContent().build();
    }
}

