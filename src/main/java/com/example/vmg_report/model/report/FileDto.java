package com.example.vmg_report.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private int id;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String createBy;

    private String updateBy;

    private String name;

    private double revenue;

    private double expenses;

    private double profit;

    private double accountsPayable;

    private Report report ;
}
