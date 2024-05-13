package com.example.vmg_report.service;

import com.example.vmg_report.model.report.Report;

import java.security.Principal;
import java.util.List;
public interface ReportService {

    List<Report> getDataByCompanyId(int companyId);

    List<Report> searchReport(String keywordR);

    List<Report> getReportCompany();

    List<Report> getCompanyReports(Principal principal) throws Exception;

}
