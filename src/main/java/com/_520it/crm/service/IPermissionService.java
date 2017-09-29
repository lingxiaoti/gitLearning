package com._520it.crm.service;
import com._520it.crm.domain.Permission;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface IPermissionService {
	int deleteByPrimaryKey(Long id);
    int insert(Permission record);
    Permission selectByPrimaryKey(Long id);
    List<Permission> selectAll();
    int updateByPrimaryKey(Permission record);
	PageResult queryPagePage(QueryObject qo);
	void load();
	PageResult queryPageForRoleForm();
	PageResult selectPageForRoleFormByRoleId(Long roleId);

	List<String> queryPermissionListByEmployeeId(Long id);
}
