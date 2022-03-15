package com.example.tool.service;

import com.example.tool.model.TransactionDTO;
import com.example.tool.repository.RepositoryTransaction;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    private RepositoryTransaction repositoryT;

    public void cloneData(String account, ChromeDriver chromeDriver) {

        chromeDriver.get("https://etherscan.io/");
        chromeDriver.findElement(By.id("txtSearchInput")).sendKeys(account);
        chromeDriver.findElement(By.id("txtSearchInput")).sendKeys(Keys.ENTER);
        chromeDriver.findElement(By.id("lnkTxAgeDateTime")).click();
        List<WebElement> listR = chromeDriver.findElements(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr"));
        if (chromeDriver.findElements(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[1]/td/div")).isEmpty()) {
            for (int i = 1; i <= listR.size(); i++) {
                String txn_Hash = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 2 + "]")).getText();
                String method = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 3 + "]")).getText();
                String block = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 4 + "]")).getText();
                String age = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 5 + "]")).getText();
                String from = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 7 + "]")).getText();
                String to = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 8 + "]")).getText();
                String direction = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 9 + "]")).getText();
                String value = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 10 + "]")).getText();
                String txn_fee = chromeDriver.findElement(By.xpath("//*[@id=\"transactions\"]/div[2]/table/tbody/tr[" + i + "]/td[" + 11 + "]")).getText();
                TransactionDTO transaction = new TransactionDTO(account, txn_Hash, method, block, age, from, direction,to , value, txn_fee);
                repositoryT.save(transaction);

            }
        }
        chromeDriver.close();
    }
    public ByteArrayInputStream load(){
        List<TransactionDTO> transactions = repositoryT.findAll();
        ByteArrayInputStream in = ExcelExport.transactionToExcel(transactions);
        return in;
    }

}
