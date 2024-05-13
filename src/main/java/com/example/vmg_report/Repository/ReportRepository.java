package com.example.vmg_report.Repository;

import com.example.vmg_report.model.company.Company;
import com.example.vmg_report.model.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query("SELECT r FROM Report r WHERE r.createBy  LIKE %?1%")
    List<Report> searchReport(String keywordR);
    @Query("select r from Report r where r.company =?1 ")
    List<Report> findByCompany(Company currentUserCompany);
    List<Report> findByCompany_Id(int companyId);
}
