package com._520it.crm.mapper;

import com._520it.crm.domain.Supplier;
import com._520it.crm.query.SupplierQueryObject;

import java.util.List;

public interface SupplierMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Supplier record);
    Supplier selectByPrimaryKey(Long id);
    List<Supplier> selectAll();
    int updateByPrimaryKey(Supplier record);

    /**
     * 查询总记录数
     * @param qo
     * @return
     */
    Long queryPageCount(SupplierQueryObject qo);

    /**
     * 查询总结果数据
     * @param qo
     * @return
     */
    List<Supplier> queryPageData(SupplierQueryObject qo);
}