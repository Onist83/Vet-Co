package com.onist.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onist.appointment.dto.AnimalFeignResponse;
import com.onist.appointment.dto.AppointmentResponse;
import com.onist.appointment.dto.CreateAppointmentRequest;
import com.onist.appointment.dto.UserFeignResponse;
import com.onist.appointment.feign.AnimalFeignClient;
import com.onist.appointment.feign.UserFeignClient;
import com.onist.appointment.model.AppointmentModel;
import com.onist.appointment.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AnimalFeignClient animalFeignClient;
    private final UserFeignClient userFeignClient;

    /**
     * Recherche des animaux par nom (pour l'UI)
     */
    public List<AnimalFeignResponse> searchAnimalsByName(String name) {
        if (name == null || name.isBlank()) {
            return List.of();
        }
        // Appel au service Animal via Feign
        return animalFeignClient.searchAnimalsByName(name);
    }

    /**
     * Recherche des vétérinaires par prénom et nom (pour l'UI)
     */
    public List<UserFeignResponse> searchVeterinariansByNames(String firstName, String lastName) {
        if ((firstName == null || firstName.isBlank()) && (lastName == null || lastName.isBlank())) {
            return List.of();
        }
        // Appel au service User via Feign
        return userFeignClient.searchUsersByNames(firstName, lastName);
    }

    /**
     * Création d'un rendez-vous avec validation des IDs
     */
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        // 1. Valider l'animal
        AnimalFeignResponse animal = animalFeignClient.getAnimalById(request.getAnimalId());
        if (animal == null) {
            throw new RuntimeException("L'animal avec l'ID " + request.getAnimalId() + " n'existe pas.");
        }

        // 2. Valider le vétérinaire
        UserFeignResponse veterinarian = userFeignClient.getUserById(request.getVeterinarianId());
        if (veterinarian == null) {
            throw new RuntimeException("Le vétérinaire avec l'ID " + request.getVeterinarianId() + " n'existe pas.");
        }

        // 3. Vérifier les conflits de planning (optionnel mais recommandé)
        boolean conflict = appointmentRepository.existsByVeterinarianIdAndAppointmentDateTimeBetween(
                request.getVeterinarianId(),
                request.getAppointmentDateTime(),
                request.getAppointmentDateTime().plusMinutes(request.getDurationMinutes()));

        if (conflict) {
            throw new RuntimeException("Le vétérinaire a déjà un rendez-vous à cette heure-là.");
        }

        // 4. Créer l'entité via Builder
        AppointmentModel appointment = AppointmentModel.builder()
                .animalId(request.getAnimalId())
                .veterinarianId(request.getVeterinarianId())
                .appointmentDateTime(request.getAppointmentDateTime())
                .durationMinutes(request.getDurationMinutes())
                .reason(request.getReason()) // Assure-toi que Reason est bien géré (enum ou string)
                .notes(request.getNotes())
                .status(com.onist.appointment.model.Status.SCHEDULED)
                .build();

        AppointmentModel saved = appointmentRepository.save(appointment);

        // 5. Retourner le DTO via Builder
        return mapToResponse(saved, animal, veterinarian);
    }

    /**
     * Récupérer un RDV par ID
     */
    public AppointmentResponse getAppointmentById(Long id) {
        AppointmentModel appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID : " + id));

        // On récupère les infos de l'animal et du vet pour le DTO complet
        AnimalFeignResponse animal = animalFeignClient.getAnimalById(appointment.getAnimalId());
        UserFeignResponse veterinarian = userFeignClient.getUserById(appointment.getVeterinarianId());

        return mapToResponse(appointment, animal, veterinarian);
    }

    // --- Méthodes utilitaires privées ---

    private AppointmentResponse mapToResponse(AppointmentModel appointment, AnimalFeignResponse animal,
            UserFeignResponse veterinarian) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .animalId(appointment.getAnimalId())
                .animalName(animal != null ? animal.getName() : "Inconnu")
                .veterinarianId(appointment.getVeterinarianId())
                .veterinarianFirstName(veterinarian != null ? veterinarian.getFirstName() : "")
                .veterinarianLastName(veterinarian != null ? veterinarian.getLastName() : "")
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .durationMinutes(appointment.getDurationMinutes())
                .status(appointment.getStatus())
                .reason(appointment.getReason())
                .notes(appointment.getNotes())
                .build();
    }
}