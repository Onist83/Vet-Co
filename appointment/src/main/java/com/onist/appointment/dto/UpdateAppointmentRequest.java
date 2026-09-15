package com.onist.appointment.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import com.onist.appointment.model.Reason;
import com.onist.appointment.model.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAppointmentRequest {

    @NotNull(message = "L'identifiant du vétérinaire est obligatoire")
    private Long veterinarianId;

    @NotNull(message = "La date de prise de rendez-vous doit être précisée")
    private LocalDateTime appointmentDateTime;

    @NotNull(message = "La durée du rendez-vous doit être précisée")
    private Integer durationMinutes;

    @NotNull(message = "Le statut du rendez-vous doit être renseignée")
    private Status status = Status.SCHEDULED;

    @NotNull(message = "La raison du rendez-vous doit être renseignée")
    private Reason reason;

    private String notes;

    private String cancellationReason;

}
