package com._520it.crm.service;


import com._520it.crm.domain.Supplier;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.SupplierQueryObject;

import java.util.List;

/**
 * Created by 华硕 on 2017/10/12.
 */
public interface ISupplierService {
    int deleteByPrimaryKey(Long id);
    int insert(Supplier record);
    Supplier selectByPrimaryKey(Long id);
    List<Supplier> selectAll();
    int updateByPrimaryKey(Supplier record);

    /**
     * 根据(高级条件)条件查询
     * @param qo
     * @return
     */
    PageResult queryPage(SupplierQueryObject qo);
}
