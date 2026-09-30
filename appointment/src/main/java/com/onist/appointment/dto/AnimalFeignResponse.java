package com.onist.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalFeignResponse {
    private Long id;
    private String name;
    private String chipNumber;
    private Long ownerId;
}
