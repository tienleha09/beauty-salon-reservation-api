package com.project.beautysalonreservationapi.security.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequest {
    @NotNull
    @Size(max = 255)
    private String username;
    @NotNull
    private String password;
}
