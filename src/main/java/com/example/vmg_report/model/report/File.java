package com.example.vmg_report.model.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "create_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime updateAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "name")
    private String name;

    @Column(name = "revenue")
    private double revenue;

    @Column(name = "expenses")
    private double expenses;

    @Column(name = "profit")
    private double profit;

    @Column(name = "accounts_payable")
    private double accountsPayable;

    @OneToOne(mappedBy = "file",cascade=CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    private Report report;

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", name='" + name + '\'' +
                ", revenue=" + revenue +
                ", expenses=" + expenses +
                ", profit=" + profit +
                ", accountsPayable=" + accountsPayable +
                ", report=" + report +
                '}';
    }
}
