package com.example.vmg_report.service.Impl;

import com.example.vmg_report.Repository.FileRepository;
import com.example.vmg_report.Repository.ReportRepository;
import com.example.vmg_report.model.report.File;
import com.example.vmg_report.service.CustomFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomFileServiceIpl implements CustomFileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ReportRepository reportRepository;



    @Override
    public Optional<File> findFileByReportId(int id) {
        return fileRepository.findByReportId(id);
    }

    @Override
    public double findSumProfit() {
        return fileRepository.SumProfit();
    }

    @Override
    public double findSumAccountsPayable() {
        return fileRepository.SumAccountsPayable();
    }
    
    @Override
    public double findSumRevenue() {
        return fileRepository.SumRevenue();
    }

    @Override
    public double findSumExpenses() {
        return fileRepository.SumExpenses();
    }

}



