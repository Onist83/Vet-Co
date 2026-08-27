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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.onist.animal.model.AnimalModel;
import com.onist.animal.service.AnimalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AnimalModel> createAnimal(@RequestBody AnimalModel animal) {
        AnimalModel created = service.createAnimal(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnimalModel>> findAll() {
        return ResponseEntity.ok(service.findAllAnimals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalModel> animalById(@PathVariable long id) {
       return ResponseEntity.ok(service.animalById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AnimalModel> updateAnimal (@PathVariable Long id, @Valid @RequestBody AnimalModel animal) {
        AnimalModel updated = service.updateAnimal(id, animal);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable Long id) {
        service.deleteAnimalById(id);
    }


}

