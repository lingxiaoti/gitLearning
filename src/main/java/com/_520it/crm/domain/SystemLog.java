package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter@Getter
public class SystemLog {
    private Long id;
    private Employee opUser;
    private Date opTime;
    private String opIp;
    private String function;
    private String params;
}