package com.onist.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onist.appointment.dto.AnimalFeignResponse;
import com.onist.appointment.dto.AppointmentResponse;
import com.onist.appointment.dto.CreateAppointmentRequest;
import com.onist.appointment.dto.UpdateAppointmentRequest;
import com.onist.appointment.dto.UserFeignResponse;
import com.onist.appointment.exception.AnimalNotFoundException;
import com.onist.appointment.exception.AppointmentNotFoundException;
import com.onist.appointment.exception.VeterinarianNotFoundException;
import com.onist.appointment.feign.AnimalFeignClient;
import com.onist.appointment.feign.UserFeignClient;
import com.onist.appointment.model.AppointmentModel;
import com.onist.appointment.model.Status;
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
        return toAppointmentResponse(saved, animal, veterinarian);
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

        return toAppointmentResponse(appointment, animal, veterinarian);
    }

    // --- Méthodes utilitaires privées ---

    public AppointmentModel toAppointment(CreateAppointmentRequest request, Long animalId, Long veterinarianId) {
        return AppointmentModel.builder()
                .animalId(animalId)
                .veterinarianId(veterinarianId)
                .appointmentDateTime(request.getAppointmentDateTime())
                .durationMinutes(request.getDurationMinutes())
                .reason(request.getReason())
                .notes(request.getNotes())
                .status(Status.SCHEDULED) // Défaut
                .build();
    }

    private AppointmentResponse toAppointmentResponse(AppointmentModel appointment, AnimalFeignResponse animal,
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

    public List<AppointmentResponse> getAllAppointments() {
        List<AppointmentModel> appointments = appointmentRepository.findAll();
        return appointments
                .stream()
                .map(appointment -> {
                    AnimalFeignResponse animal = animalFeignClient.getAnimalById(appointment.getAnimalId());
                    UserFeignResponse veterinarian = userFeignClient.getUserById(appointment.getVeterinarianId());
                    return toAppointmentResponse(appointment, animal, veterinarian);
                })
                .toList();
    }

    /**
     * Récupère un rendez-vous ou lève une exception (utilitaire interne)
     */
    private AppointmentModel getAppointmentOrThrow(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Rendez-vous non trouvé avec l'ID : " + id));
    }

    private AnimalFeignResponse getAnimalOrThrow(Long animalId) {
        AnimalFeignResponse animal = animalFeignClient.getAnimalById(animalId);
        if (animal == null) {
            throw new AnimalNotFoundException("L'animal avec l'ID " + animalId + " n'existe pas.");
        }
        return animal;
    }

    private UserFeignResponse getVeterinarianOrThrow(Long veterinarianId) {
        UserFeignResponse veterinarian = userFeignClient.getUserById(veterinarianId);
        if (veterinarian == null) {
            throw new VeterinarianNotFoundException("Le vétérinaire avec l'ID " + veterinarianId + " n'existe pas.");
        }
        return veterinarian;
    }

    /**
     * Met à jour un rendez-vous.
     */
    public AppointmentResponse updateAppointment(Long id, UpdateAppointmentRequest request) {
        // 1. Vérifier que le RDV existe
        AppointmentModel existingAppointment = getAppointmentOrThrow(id);

        // 2. Valider les nouvelles données (Animal et Vet peuvent changer ? Oui, on
        // valide)
        AnimalFeignResponse animal = getAnimalOrThrow(request.getAnimalId());
        UserFeignResponse veterinarian = getVeterinarianOrThrow(request.getVeterinarianId());

        // 3. Mise à jour des champs via Builder (pattern cohérent)
        AppointmentModel updatedAppointment = AppointmentModel.builder()
                .id(existingAppointment.getId()) // On garde l'ID
                .animalId(request.getAnimalId())
                .veterinarianId(veterinarian.getId())
                .appointmentDateTime(request.getAppointmentDateTime())
                .durationMinutes(request.getDurationMinutes())
                .reason(request.getReason())
                .notes(request.getNotes())
                // On garde le statut existant ou on le force ? Ici on le laisse tel quel sauf
                // si demandé
                .status(existingAppointment.getStatus())
                .build();

        AppointmentModel saved = appointmentRepository.save(updatedAppointment);

        // 4. Retour enrichi
        return toAppointmentResponse(saved, animal, veterinarian);
    }

    public void deleteAppointmentById(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new AppointmentNotFoundException("Appointment not found with id " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
