package com.spbstu.edu.advertisement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;

    private String name;

    private String surname;

    @JsonIgnore
    private String password;

    private String phoneNumber;
}
