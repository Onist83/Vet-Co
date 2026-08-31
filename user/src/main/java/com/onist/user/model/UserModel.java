package com.onist.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserModel {

    // The UserModel class represents a user entity in the system.
    // It is annotated with JPA annotations to map it to a database table named "users".
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable = false)
    @NotBlank(message="Le prénom est requis")
    private String firstname;
    
    @Column(name="last_name", nullable = false)
    @NotBlank(message="Le nom est requis")
    private String lastname;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message="Le role est obligatoire")
    private Role role;

    @Column(name="email", nullable = false, unique = true)
    @NotBlank(message="L' email obligatoire")
    @Email(message="Le format de l'email est invalide")
    private String email;

    @Column(name="password", nullable = false)
    @NotBlank(message="Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Builder.Default
    private boolean enabled = true; 
    
    @Column(name = "must_change_password", nullable = false)
    @Builder.Default
    private boolean mustChangePassword = true;
}
