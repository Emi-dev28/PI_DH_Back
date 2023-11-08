package com.PI_back.pi_back.dto;

import com.PI_back.pi_back.utils.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuthenticationResponse {
    @JsonProperty("token")
    private String token;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("role")
    private Role role;
}
