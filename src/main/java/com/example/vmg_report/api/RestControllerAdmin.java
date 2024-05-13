package com.example.vmg_report.api;

import com.example.vmg_report.Repository.FileRepository;
import com.example.vmg_report.Repository.ReportRepository;
import com.example.vmg_report.Repository.RoleRepository;
import com.example.vmg_report.Repository.UserRepository;
import com.example.vmg_report.model.DashboardDto;
import com.example.vmg_report.model.report.File;
import com.example.vmg_report.model.report.Report;
import com.example.vmg_report.model.user.CustomUserDetails;
import com.example.vmg_report.model.user.User;
import com.example.vmg_report.model.user.UserDto;
import com.example.vmg_report.service.CustomFileService;
import com.example.vmg_report.service.ReportService;
import com.example.vmg_report.service.UpLoadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("Admin/api")
public class RestControllerAdmin {


    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UpLoadFileService upLoadFileService;

    @Autowired
    private CustomFileService customFileService;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/adminHome")
    public ResponseEntity<?> adminHome() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = customUserDetails.getName();
        double sumProfit = customFileService.findSumProfit();
        double sumAccountsPayable = customFileService.findSumAccountsPayable();
        double sumRevenue = customFileService.findSumRevenue();
        double sumExpenses = customFileService.findSumExpenses();
        DashboardDto dashboardDto = new DashboardDto(username, sumProfit, sumAccountsPayable, sumExpenses, sumRevenue);
        return ResponseEntity.ok(dashboardDto);
    }

    @GetMapping("/adminListUser")
    public ResponseEntity<?> listUser(@RequestParam(required = false) String keyword) {
        try {
            List<User> users;
//            if (keyword != null && !keyword.isEmpty()) {
//                users = userRepository. searchUser(keyword);
//            } else {
            users = userRepository.findAll();
//            }
            return ResponseEntity.ok(users);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + ex.getMessage());
        }
    }


    @PostMapping(value = "/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setStatus(true);
            user.setRoles(new HashSet<>(roleRepository.findByCode("USER")));

            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }

    }

    @GetMapping("/editor")
    public ResponseEntity<?> editUser(@RequestParam int id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");

            }

            UserDto userDto = new UserDto();
            userDto.setId(user.get().getId());
            userDto.setName(user.get().getName());
            userDto.setCompany(user.get().getCompany());
            userDto.setEmail(user.get().getEmail());
            userDto.setUsername(user.get().getUsername());
            userDto.setPassword(user.get().getPassword());
            userDto.setStatus(true);
            return ResponseEntity.ok(userDto);

        } catch (Exception ex) {
            System.out.println("Exception:" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + ex.getMessage());
        }

    }

    @PostMapping("/editor")
    public ResponseEntity<String> editUser(@RequestParam int id, @RequestBody UserDto userDto) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
            }

            User user = optionalUser.get();
            user.setName(userDto.getName());
            user.setUsername(userDto.getUsername());
            user.setCompany(userDto.getCompany());
            user.setStatus(true);

            userRepository.save(user);

            return ResponseEntity.ok("Sửa thông tin người dùng thành công");
        } catch (Exception ex) {
            ex.printStackTrace(); // In ra stack trace của lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + ex.getMessage());
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<?> disableUser(@RequestParam int id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
            }

            User user = optionalUser.get();
            user.setStatus(false);
            userRepository.save(user);

            return ResponseEntity.ok("Người dùng đã bị vô hiệu hóa");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + ex.getMessage());
        }
    }

    @GetMapping("/listReport")
    public ResponseEntity<?> listReport() {
        List<Report> reportList = reportRepository.findAll();
        return ResponseEntity.ok(reportList);

    }

    @GetMapping("/deleteReport")
    public ResponseEntity<?> deleteReport(@RequestParam int id){
        try {
            Optional<Report> report = reportRepository.findById(id);
            if(report.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Báo cáo không tồn tại");
            }
            Optional<File> file = customFileService.findFileByReportId(report.get().getId());
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Báo cáo không tồn tại");

            }
            reportRepository.delete(report.get());
            fileRepository.delete(file.get());


            return ResponseEntity.ok("Đã xóa thành công báo cáo");
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi tìm kiếm báo cáo");
        }
    }

}