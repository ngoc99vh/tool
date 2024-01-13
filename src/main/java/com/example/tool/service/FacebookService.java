package com.example.tool.service;

import com.example.tool.model.ContentDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class FacebookService {
    public static final int COLUMN_INDEX_CONTENT = 0;
    public static final int COLUMN_INDEX_IMAGE = 1;
    public static final int COLUMN_INDEX_ID_GROUP = 2;

    /**
     * Job định kỳ 2' một lần
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void job() throws IOException, InterruptedException {
        autoLogin();
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleTaskUsingCronExpression() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }

    public void autoLogin() throws InterruptedException, IOException {
        ContentDto contentDto = readFileExcel();

        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--disable-notifications");
        ChromeDriver chromeDriver = new ChromeDriver(ops);
        chromeDriver.manage().window().maximize();

        chromeDriver.get("https://www.facebook.com/");
        chromeDriver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(contentDto.getUserName());
        chromeDriver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys(contentDto.getPassword());
        chromeDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div/div/div/div[2]/div/div[1]/form/div[2]/button")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        contentDto.getContents().forEach(contents -> {
            chromeDriver.get("https://www.facebook.com/groups/" + contents.getIdGroup());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            chromeDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div[2]/div/div/div[4]/div/div[2]/div/div/div[1]/div[1]/div/div/div/div[2]/div/div[2]/div[1]/span[1]/img")).click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            chromeDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/div/div[1]/form/div/div[1]/div/div/div[1]/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/input")).sendKeys(contents.getImage());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            chromeDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/div/div[1]/form/div/div[1]/div/div/div[1]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div/div/div/div[2]/div/div/div/div")).sendKeys(contents.getContent());
            chromeDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/div/div[1]/form/div/div[1]/div/div/div[1]/div/div[3]/div[3]/div/div/div")).click();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            chromeDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div[4]/div/div[1]/div[1]/ul/li[1]/span/div/a")).click();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });


    }

    public ContentDto readFileExcel() throws IOException {
        FileInputStream file = new FileInputStream(
                new File("D:\\FB.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheetLogin = workbook.getSheet("Login");
        XSSFSheet sheetContent = workbook.getSheet("Content");
        ContentDto contentDto = new ContentDto();
        for (int i = 0; i < sheetLogin.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheetLogin.getRow(i);
            if (i == 0) {
                contentDto.setUserName(row.getCell(0).getStringCellValue());
            }
            if (i == 1) {
                contentDto.setPassword(row.getCell(0).getStringCellValue());
            }

        }

        List<ContentDto.Contents> contentDtos = new ArrayList<>();

        // Get all rows
        Iterator<Row> iterator = sheetContent.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for contentDto object
            ContentDto.Contents content = new ContentDto.Contents();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for contentDto object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_CONTENT:
                        content.setContent(cellValue.toString());
                        break;
                    case COLUMN_INDEX_IMAGE:
                        content.setImage(cellValue.toString());
                        break;
                    case COLUMN_INDEX_ID_GROUP:
                        content.setIdGroup(cellValue.toString());
                        break;
                    default:
                        break;
                }

            }
            contentDtos.add(content);
        }
        contentDto.setContents(contentDtos);
        return contentDto;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}

