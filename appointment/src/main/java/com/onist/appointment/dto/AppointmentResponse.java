package com.onist.appointment.dto;

import java.time.LocalDateTime;

import com.onist.appointment.model.Reason;
import com.onist.appointment.model.Status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppointmentResponse {
    private Long id;
    private Long animalId;
    private Long veterinarianId;
    private LocalDateTime appointmentDateTime;
    private Integer durationMinutes;
    private Status status;
    private Reason reason;
    private String notes;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
