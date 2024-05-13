package com.example.vmg_report.model.report;


import com.example.vmg_report.model.company.Company;
import com.example.vmg_report.model.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportDto {

    private User idUser;

    private Company company ;

    private String name;

    @NotEmpty
    private String createBy;

    private MultipartFile file;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;


}
