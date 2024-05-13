package com.example.vmg_report.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {
    private String username;
    private double sumProfit;
    private double sumAccountsPayable;
    private double sumExpenses;
    private double sumRevenue;
}
