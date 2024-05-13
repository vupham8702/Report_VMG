package com.example.vmg_report.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponseDto {
    private String accessToken;
}
