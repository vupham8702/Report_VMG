package com.example.vmg_report.model.report;

import com.example.vmg_report.model.company.Company;
import com.example.vmg_report.model.user.User;
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
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
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

    @ManyToOne()
    @JoinColumn(name = "id_company")
    private Company company;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    @JsonBackReference
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file ;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", company=" + company +
                ", user=" + user +
                ", file=" + file +
                '}';
    }

//    public void setFile(int file_id) {
//    }
}
