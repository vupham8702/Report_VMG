package com.example.vmg_report.service.Impl;

import com.example.vmg_report.Repository.CompanyRepository;
import com.example.vmg_report.Repository.FileRepository;
import com.example.vmg_report.Repository.ReportRepository;
import com.example.vmg_report.Repository.UserRepository;
import com.example.vmg_report.model.company.Company;
import com.example.vmg_report.model.report.Report;
import com.example.vmg_report.model.user.User;
import com.example.vmg_report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public List<Report> getDataByCompanyId(int companyId) {
        List<Report> reportList = reportRepository.findByCompany_Id(companyId);
        return reportList;
    }
    @Override
    public List<Report> searchReport(String keywordR) {
        return reportRepository.searchReport(keywordR);
    }

    @Override
    public List<Report> getReportCompany() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getCompanyReports(Principal principal) {
        String currentUserEmail = principal.getName();
        User user = userRepository.findByEmail(currentUserEmail);
        String currentUserCompany = user.getCompany();
        Company company = companyRepository.findByName(currentUserCompany);

        return reportRepository.findByCompany(company);
    }

}
