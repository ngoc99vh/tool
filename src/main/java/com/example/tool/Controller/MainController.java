package com.example.tool.Controller;

import com.example.tool.message.ResponseMessage;
import com.example.tool.model.Car;
import com.example.tool.repository.RepositoryTransaction;
import com.example.tool.service.ExcelService;
import com.example.tool.service.FacebookService;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private ExcelService excelService;

    @Autowired
    ApplicationContext context;

    @Autowired
    private RepositoryTransaction repositoryTransaction;

    @Autowired
    private FacebookService facebookService;

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<ResponseMessage> readExcelToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
//        repositoryTransaction.deleteAll();
//        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
//        XSSFSheet worksheet = workbook.getSheetAt(0);
//
//        for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
//            XSSFRow row = worksheet.getRow(i);
//            String account = row.getCell(0).getStringCellValue();
//            System.out.println(account);
//            WebDriverManager.chromedriver().setup();
//            ChromeDriver chromeDriver = new ChromeDriver();
//            chromeDriver.manage().window().maximize();
//            excelService.cloneData(account, chromeDriver);
//        }
//        return ResponseEntity.ok().body(new ResponseMessage("Upload thành công !!!"));
//    }
//
//    @GetMapping("/download")
//    public ResponseEntity<Resource> getFile() {
//        String filename = "Transaction.xlsx";
//        InputStreamResource file = new InputStreamResource(excelService.load());
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(file);
//    }
//
//    @PostMapping("/file-upload")
//    public ResponseEntity<String> fileUpload(MultipartFile file) {
//        try {
//
//            // upload directory - change it to your own
//            String UPLOAD_DIR = "/opt/uploads";
//
//            // create a path from file name
//            Path path = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
//
//            // save the file to `UPLOAD_DIR`
//            // make sure you have permission to write
//            Files.write(path, file.getBytes());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return new ResponseEntity<>("Invalid file format!!", HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>("File uploaded!!", HttpStatus.OK);
//    }

    @GetMapping("/loginFB")
    public ResponseEntity<String> autoLoginFacebook() throws IOException, InterruptedException {
        facebookService.autoLogin();
        return new ResponseEntity<>("Login success!", HttpStatus.OK);
    }
    @GetMapping(path = "/excel")
    @ResponseBody
    public void getExcel(javax.servlet.http.HttpServletResponse response) throws Exception {
        //Get JRXML template from resources folder
//        Resource resource = context.getResource("classpath:reports/" + jrxml + ".jrxml");
        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/car_list2.jrxml");
        JasperDesign design = JRXmlLoader.load(jasperStream);
        JasperReport report = JasperCompileManager.compileReport(design);

        Map<String, Object> params = new HashMap<>();

        List<Car> cars = new ArrayList<>();

        Car car = new Car(1L,"123","123","123","123", 123F,123F,"123","123","123","123","123","123",false);
        Car car1 = new Car(1L,"123","123","123","123",123F,123F,"123","123","123","123","123","123",false);
        Car car2 = new Car(1L,"123","123","123","123",123F,123F,"123","123","123","123","123","123",false);
        Car car3 = new Car(1L,"123","123","123","123",123F,123F,"123","123","123","123","123","123",false);
        Car car4 = new Car(1L,"123","123","123","123",123F,123F,"123","123","123","123","123","123",false);
//        Car car = new Car(1,"123","123","123","123",1999);
//        Car car1 = new Car(1,"123","123","123","123",1999);
//        Car car2 = new Car(1,"123","123","123","123",1999);
//        Car car3 = new Car(1,"123","123","123","123",1999);
//        Car car4 = new Car(1,"123","123","123","123",1999);
        cars.add(car);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car4);
        cars.add(car4);
        cars.add(car4);
        cars.add(car4);
        cars.add(car4);

        JRDataSource dataSource = new JRBeanCollectionDataSource(cars);
        params.put("datasource", dataSource);

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        File outputFile = new File("excelTest.xlsx");
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
//        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//        configuration.setDetectCellType(true);//Set configuration as you like it!!
//        configuration.setCollapseRowSpan(false);
//        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
//    @GetMapping(path = "/excel2")
//    @ResponseBody
//    private void getDownloadReportXlsx(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
//        try {
//            //uncomment this codes if u are want to use servlet output stream
//            ServletOutputStream servletOutputStream = response.getOutputStream();
//
//            Map<String, Object> params = new HashMap<>();
//
//            List<Car> cars = new ArrayList<>();
//            Car car = new Car(1,"123","123","123","123",1999);
//            cars.add(car);
//
//            //Data source Set
//            JRDataSource dataSource = new JRBeanCollectionDataSource(cars);
//            params.put("datasource", dataSource);
//
//            //get real path for report
//            InputStream jasperStream = this.getClass().getResourceAsStream("/reports/car_list.jrxml");
//            JasperDesign design = JRXmlLoader.load(jasperStream);
//            JasperReport report = JasperCompileManager.compileReport(design);
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
//
//            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "car_list.xls");
//
//            //uncomment this codes if u are want to use servlet output stream
////        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
//
//            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
////        xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
////        xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput("car_list.xlsx"));
////        xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
//            xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
//            xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
//            xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//            xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
////        xlsxExporter.exportReport();
//
//
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setHeader("Content-Disposition", "attachment; filename=car_list.xls");
//
//            //uncomment this codes if u are want to use servlet output stream
////        servletOutputStream.write(os.toByteArray());
//
//            response.getOutputStream().write(os.toByteArray());
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
//            response.flushBuffer();
//        } catch (JRException ex) {
//            System.out.println("Error : " + ex.getMessage());
//        } catch (IOException ex) {
//            System.out.println("IOException " + ex.getMessage());
//        }
//    }
//    @GetMapping(path = "/pdf")
//    @ResponseBody
////    public void getPdf(@PathVariable String jrxml, HttpServletResponse response) throws Exception {
//    public void getPdf(HttpServletResponse response) throws Exception {
//        //Get JRXML template from resources folder
////        Resource resource = context.getResource("classpath:reports/" + jrxml + ".jrxml");
//        Resource resource = context.getResource("classpath:reports/car_list.jrxml");
//        //Compile to jasperReport
//        InputStream inputStream = resource.getInputStream();
//        JasperReport report = JasperCompileManager.compileReport(inputStream);
//        //Parameters Set
//        Map<String, Object> params = new HashMap<>();
//
//        List<Car> cars = new ArrayList<>();
//        Car car = new Car(1,"123","123","123","123",1999);
//        cars.add(car);
//
//        //Data source Set
//        JRDataSource dataSource = new JRBeanCollectionDataSource(cars);
//        params.put("datasource", dataSource);
//
//        //Make jasperPrint
//        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
//        //Media Type
//        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//        //Export PDF Stream
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//    }
}
