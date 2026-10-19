package com.onist.appointment.controller;

import java.util.List;

import jakarta.validation.Valid;

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

import com.onist.appointment.dto.AnimalFeignResponse;
import com.onist.appointment.dto.AppointmentResponse;
import com.onist.appointment.dto.CreateAppointmentRequest;
import com.onist.appointment.dto.UpdateAppointmentRequest;
import com.onist.appointment.dto.UserFeignResponse;
import com.onist.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AppointmentResponse> createAppointment(
            @Valid @RequestBody CreateAppointmentRequest request) {

        AppointmentResponse created = appointmentService.createAppointment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Endpoint pour rechercher des animaux par nom (pour l'UI)
     */
    @GetMapping("/animal/search")
    public ResponseEntity<List<AnimalFeignResponse>> searchAnimalsByName(@RequestParam String query) {
        return ResponseEntity.ok(appointmentService.searchAnimalsByName(query));
    }

    /**
     * Endpoint pour rechercher des vétérinaires par nom/prénom (pour l'UI)
     */
    @GetMapping("/veterinarian/search")
    public ResponseEntity<List<UserFeignResponse>> searchVeterinariansByNames(
            @RequestParam(required = false) String firstName, String lastName) {
        return ResponseEntity.ok(appointmentService.searchVeterinariansByNames(firstName, lastName));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponse>> findAll() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> appointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAppointmentRequest request) {

        AppointmentResponse updated = appointmentService.updateAppointment(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointmentById(id);

        return ResponseEntity.noContent().build();
    }

}
