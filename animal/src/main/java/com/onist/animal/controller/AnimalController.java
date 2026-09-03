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

@RestController
@RequestMapping("/api/v1/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AnimalResponse> createAnimal(
        @Valid @RequestBody CreateAnimalRequest request) {

        AnimalResponse created = animalService.createAnimal(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Searches animals by name, or by the first/last name of either owner
    @GetMapping("/search")
    public ResponseEntity<List<AnimalResponse>> searchAnimals(@RequestParam String query) {
        return ResponseEntity.ok(animalService.searchByName(query));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnimalResponse>> findAll() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> animalById(@PathVariable Long id) {
       return ResponseEntity.ok(animalService.animalById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AnimalResponse> updateAnimal (
        @PathVariable Long id, 
        @Valid @RequestBody UpdateAnimalRequest request) {

        AnimalResponse updated = animalService.updateAnimal(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {

        animalService.deleteAnimalById(id);

        return ResponseEntity.noContent().build();
    }
}

