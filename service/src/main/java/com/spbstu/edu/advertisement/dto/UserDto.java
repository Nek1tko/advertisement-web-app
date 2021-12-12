package com.spbstu.edu.advertisement.dto;

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
    private String password;
    private String phoneNumber;
}
