package com.example.vmg_report.api;

import com.example.vmg_report.Repository.CompanyRepository;
import com.example.vmg_report.Repository.FileRepository;
import com.example.vmg_report.Repository.ReportRepository;
import com.example.vmg_report.Repository.UserRepository;
import com.example.vmg_report.model.company.Company;
import com.example.vmg_report.model.report.File;
import com.example.vmg_report.model.report.Report;
import com.example.vmg_report.model.report.ReportDto;
import com.example.vmg_report.model.user.User;
import com.example.vmg_report.service.CustomFileService;
import com.example.vmg_report.service.UpLoadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("User/api")
public class RestControllerUser {
    @Autowired
    private ReportRepository repo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CustomFileService customFileService;

    @GetMapping("/fileDetail")
    public ResponseEntity<?> fileDetail(@RequestParam int id) {
        Optional<File> files = customFileService.findFileByReportId(id);
        return ResponseEntity.ok(files);
    }

    @PostMapping("/createReport")
    public ResponseEntity<?> createReport(@ModelAttribute ReportDto reportDto) throws IOException, IOException {
        User user = userRepository.findByName(reportDto.getCreateBy());
        Company company = companyRepository.findByName(user.getCompany());

        MultipartFile file = reportDto.getFile();
        String storageFileName = file.getOriginalFilename();
        String uploadDir = "public/data-files";
        Path uploadPath = Paths.get(uploadDir);
        InputStream inputStream = file.getInputStream();

        Report report = new Report();
        if (UpLoadFileService.isValidExcelFile(file)) {



            report.setUser(user);
            report.setCompany(company);

            report.setCreateBy(reportDto.getCreateBy());
            report.setCreateAt(reportDto.getCreateAt());
            report.setUpdateAt(reportDto.getCreateAt());
            report.setUpdateBy(reportDto.getCreateBy());


            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {
                System.out.println("Exception:" + ex.getMessage());
            }

            List<File> fileSave = UpLoadFileService.getCustomersDataFromExcel(file.getInputStream());
            for (File fileCreate : fileSave) {
                fileCreate.setCreateAt(reportDto.getCreateAt());
                fileCreate.setUpdateAt(reportDto.getCreateAt());
                fileCreate.setCreateBy(reportDto.getCreateBy());
                fileCreate.setUpdateBy(reportDto.getCreateBy());

                this.fileRepository.saveAll(fileSave);
            }
            File fileSaved = fileRepository.findFileNew();
            report.setFile(fileSaved);
            repo.save(report);
        }
        return ResponseEntity.ok("Tạo mới báo cáo thành công");
    }
}
