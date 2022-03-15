package com.example.tool.Controller;

import com.example.tool.message.ResponseMessage;
import com.example.tool.repository.RepositoryTransaction;
import com.example.tool.service.ExcelService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MainController {
    @Autowired
    private ExcelService excelService;

    @Autowired
    private RepositoryTransaction repositoryTransaction;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> readExcelToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
        repositoryTransaction.deleteAll();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            String account = row.getCell(0).getStringCellValue();
            System.out.println(account);
            WebDriverManager.chromedriver().setup();
            ChromeDriver chromeDriver = new ChromeDriver();
            chromeDriver.manage().window().maximize();
            excelService.cloneData(account,chromeDriver);
        }
        return ResponseEntity.ok().body(new ResponseMessage("Upload thành công !!!"));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "Transaction.xlsx";
        InputStreamResource file = new InputStreamResource(excelService.load());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping("/file-upload")
    public ResponseEntity<String> fileUpload(MultipartFile file) {
        try {

            // upload directory - change it to your own
            String UPLOAD_DIR = "/opt/uploads";

            // create a path from file name
            Path path = Paths.get(UPLOAD_DIR, file.getOriginalFilename());

            // save the file to `UPLOAD_DIR`
            // make sure you have permission to write
            Files.write(path, file.getBytes());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Invalid file format!!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("File uploaded!!", HttpStatus.OK);
    }
}
