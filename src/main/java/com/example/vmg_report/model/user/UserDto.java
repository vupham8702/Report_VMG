package com.example.vmg_report.model.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private int id;

    private String email;

    private String username;

    private String name;

    private String password;

    private String company;

    private boolean status;
}