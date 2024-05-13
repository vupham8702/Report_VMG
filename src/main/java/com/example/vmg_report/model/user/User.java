package com.example.vmg_report.model.user;


import com.example.vmg_report.model.report.Report;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "company")
    private String company;

    @Column(name = "status")
    private boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    @JsonIgnore
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    private List<Report> listReportUser = new ArrayList<>();

    public boolean getStatus() {
        return status;
    }


}
