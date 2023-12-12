package com.PI_back.pi_back.dto;

import com.PI_back.pi_back.model.Favorite;
import com.PI_back.pi_back.model.Reserve;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String surname;
    private String username;
    private String completeName;
    private Set<Reserve> reserves;
    private Set<Favorite> favorites;
}
