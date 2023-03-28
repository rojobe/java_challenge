package com.java.challenge.java_challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.challenge.java_challenge.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String username;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean isActive;

    private LocalDate created;

    private LocalDate lastLogin;

    private String token;

    private Set<Phone> phones;

}
