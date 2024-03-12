package com.example.tool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
//    private long id;
//    private String brand, model, color, fuel;
//    private int year;

    private Long id;
    private String idNumber;
    private String branch;
    private String clientNo;
    private String clientName;
    private Float totalBalance;
    private Float totalValue;
    private String createdTime;
    private String submittedUser;
    private String approvedUser;
    private String accountExec;
    private String requestStatus;
    private String ccy;
    private boolean requestExpired = false;
}
