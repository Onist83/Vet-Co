package com.onist.appointment.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onist.appointment.model.AppointmentModel;

public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long> {

    // Vérifier s'il y a déjà un RDV pour ce vétérinaire à cette heure-là
    // (approximatif)
    boolean existsByVeterinarianIdAndAppointmentDateTimeBetween(Long vetId, LocalDateTime start, LocalDateTime end);

    // Trouver tous les RDV d'un animal
    List<AppointmentModel> findByAnimalId(Long animalId);
}
