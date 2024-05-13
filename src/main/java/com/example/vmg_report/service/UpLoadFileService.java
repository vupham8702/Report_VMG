package com.example.vmg_report.service;


import com.example.vmg_report.Repository.FileRepository;
import com.example.vmg_report.model.report.File;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class UpLoadFileService {

    @Autowired
    private FileRepository fileRepository;

    public static boolean isValidExcelFile(MultipartFile file) throws IOException {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public void updateFileReport(double accountsPayable, double expenses, String name, double profit, double revenue, int report) {
        fileRepository.updateFile(accountsPayable, expenses, name, profit, revenue, report);
    }


    public static List<File> getCustomersDataFromExcel(InputStream inputStream) {
        List<File> files = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            int rowIndex = 0;
            String[] expectedColumns = {"name", "accounts_payable", "expenses", "revenue", "profit"};
        Iterator<Row> rows = sheet.iterator();
        Row firstRow = rows.next();
        Iterator<Cell> headerCells = firstRow.cellIterator();
        int columnIndex = 0;
        while (headerCells.hasNext()) {
            Cell headerCell = headerCells.next();
            String headerCellValue = headerCell.getStringCellValue();
            if (!headerCellValue.equals(expectedColumns[columnIndex])) {
               throw new IOException();
            }
            columnIndex++;
        }
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                File file = new File();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {

                        case 0 -> file.setName(cell.getStringCellValue());
                        case 1 -> file.setAccountsPayable(cell.getNumericCellValue());
                        case 2 -> file.setExpenses(cell.getNumericCellValue());
                        case 3 -> file.setRevenue(cell.getNumericCellValue());
                        case 4 -> file.setProfit(cell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                files.add(file);
            }
            workbook.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        return files;
    }


}