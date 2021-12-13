package com.spbstu.edu.advertisement.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectData {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("creationDateTime")
    private Date creationDateTime;
}
