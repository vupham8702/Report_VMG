package com.example.vmg_report.Repository;

import com.example.vmg_report.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query("select c from Company c WHERE c.name=?1")
    Company findByName(String name);
}
