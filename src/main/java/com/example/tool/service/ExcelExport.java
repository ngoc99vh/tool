package com.example.tool.service;

import com.example.tool.model.TransactionDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExport {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Account", "Txn_hand", "Method", "Block", "Date and Time", "From", "To", "Direction", "Value", "Txn_fee"};
    static String SHEET = "Transactions";

    public static ByteArrayInputStream transactionToExcel(List<TransactionDTO> transactions) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 1;
            for (TransactionDTO transaction : transactions) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(transaction.getAccount());
                row.createCell(1).setCellValue(transaction.getTxnhand());
                row.createCell(2).setCellValue(transaction.getMethoh());
                row.createCell(3).setCellValue(transaction.getBlock());
                row.createCell(4).setCellValue(transaction.getDatetime());
                row.createCell(5).setCellValue(transaction.getFrom());
                row.createCell(6).setCellValue(transaction.getTo());
                row.createCell(7).setCellValue(transaction.getDirection());
                row.createCell(8).setCellValue(transaction.getValue());
                row.createCell(9).setCellValue(transaction.getTxnfee());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
