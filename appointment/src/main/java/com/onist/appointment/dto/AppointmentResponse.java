package com.onist.appointment.dto;

import java.time.LocalDateTime;

import com.onist.appointment.model.Reason;
import com.onist.appointment.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {
    private Long id;
    private Long animalId;
    private String animalName;
    private Long veterinarianId;
    private String veterinarianLastName;
    private String veterinarianFirstName;
    private LocalDateTime appointmentDateTime;
    private Integer durationMinutes;
    private Status status;
    private Reason reason;
    private String notes;
}
