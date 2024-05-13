package com.example.vmg_report.Repository;


import com.example.vmg_report.model.report.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    @Query("SELECT f FROM File f order by f.id DESC limit 1")
    File findFileNew();

    @Query("SELECT f FROM File f where f.report.id=?1")
    Optional<File> findByReportId(int id);

    @Query("SELECT SUM(f.profit)FROM File f")
    double SumProfit();

    @Query("SELECT SUM(f.accountsPayable)FROM File f")
    double SumAccountsPayable();

    @Query("SELECT SUM(f.revenue)FROM File f")
    double SumRevenue();

    @Query("SELECT SUM(f.expenses)FROM File f")
    double SumExpenses();


    @Modifying
    @Transactional
    @Query("UPDATE File f SET f.accountsPayable = :accountsPayable, f.expenses = :expenses, f.name = :name, f.profit = :profit, f.revenue = :revenue WHERE f.report.id = :report")
    void updateFile(@Param("accountsPayable") double accountsPayable, @Param("expenses") double expenses, @Param("name") String name, @Param("profit") double profit, @Param("revenue") double revenue, @Param("report") Integer id);

}
