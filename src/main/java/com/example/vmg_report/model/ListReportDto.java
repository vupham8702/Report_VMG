package com.example.vmg_report.model;

import com.example.vmg_report.model.report.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListReportDto {
    private String fileName;
    private List<Report> reports;
}
