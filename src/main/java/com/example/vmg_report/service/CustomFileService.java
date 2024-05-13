package com.example.vmg_report.service;

import com.example.vmg_report.model.report.File;

import java.util.Optional;

public interface CustomFileService {

    Optional<File> findFileByReportId(int id);

    double findSumProfit();

    double findSumAccountsPayable();

    double findSumRevenue();

    double findSumExpenses();
}
