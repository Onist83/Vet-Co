package com.onist.appointment.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.onist.appointment.model.Reason;
import com.onist.appointment.model.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAppointmentRequest {

    @NotNull(message = "L'identifiant de l'animal est obligatoire")
    private Long animalId;

    @NotNull(message = "L'identifiant du vétérinaire est obligatoire")
    private Long veterinarianId;

    @NotNull(message = "La date de prise de rendez-vous doit être précisée")
    @Future(message = "La date du rendez-vous doit être dans le futur")
    private LocalDateTime appointmentDateTime;

    @NotNull(message = "La durée du rendez-vous doit être précisée")
    private Integer durationMinutes;

    @NotBlank(message = "Le statut du rendez-vous doit être renseignée")
    private Status status = Status.SCHEDULED;

    @NotNull(message = "La raison du rendez-vous doit être renseignée")
    private Reason reason;

    private String notes;
}
