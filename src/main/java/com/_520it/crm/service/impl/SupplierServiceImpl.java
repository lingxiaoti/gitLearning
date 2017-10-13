package com._520it.crm.service.impl;


import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Supplier;
import com._520it.crm.mapper.SupplierMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.SupplierQueryObject;
import com._520it.crm.service.ISupplierService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by 华硕 on 2017/10/12.
 */
@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private SupplierMapper supplierMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Supplier record) {
        //把当前登录的用户存放到数据库,存的是操作员
        Employee currentUser=(Employee) SecurityUtils.getSubject().getPrincipal();
        record.setOperator(currentUser);
        return supplierMapper.insert(record);
    }

    @Override
    public Supplier selectByPrimaryKey(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Supplier> selectAll() {
        return supplierMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Supplier record) {
        return supplierMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageResult queryPage(SupplierQueryObject qo) {
        Long count =supplierMapper.queryPageCount(qo);
        if(count <=0){
            return new PageResult(0L, Collections.EMPTY_LIST);
        }

        List<Supplier> data = supplierMapper.queryPageData(qo);
        return new PageResult(count,data);
    }
}
