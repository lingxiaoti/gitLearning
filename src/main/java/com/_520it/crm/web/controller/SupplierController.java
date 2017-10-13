package com._520it.crm.web.controller;


import com._520it.crm.page.PageResult;
import com._520it.crm.query.SupplierQueryObject;
import com._520it.crm.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 华硕 on 2017/10/12.
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("")
    public String index(){
        return "supplier";
    }

    //列表显示
    @RequestMapping("/list")
    @ResponseBody
    public PageResult list(SupplierQueryObject qo){
        PageResult result =null;
        result=supplierService.queryPage(qo);
        return result;
    }
}
