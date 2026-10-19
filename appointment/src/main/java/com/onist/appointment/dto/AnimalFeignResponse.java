package com.onist.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Génère Getters, Setters, ToString, Equals, HashCode
@Builder // Génère le Builder pattern
@NoArgsConstructor // Génère le constructeur vide (nécessaire pour Jackson/JPA)
@AllArgsConstructor // Génère le constructeur avec tous les arguments
public class AnimalFeignResponse {
    private Long id;
    private String name;
    private String chipNumber;
    private Long ownerId;
}
