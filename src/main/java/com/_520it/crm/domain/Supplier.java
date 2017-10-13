package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

//供应商
@Setter@Getter
public class Supplier {
    private Long id;
    private String name;//供应商名字
    private String sn;//供应商编码
    private BigDecimal debt;//应付欠款
    private BigDecimal refund;//应收退款
    private String linkman;//联系人
    private String phone;//联系电话
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date inputTime;//录入时间
    private Employee operator;//操作人员

}