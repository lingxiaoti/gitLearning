package com._520it.crm.service;
import com._520it.crm.domain.Role;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;

import java.util.ArrayList;
import java.util.List;

public interface IRoleService {
	int deleteByPrimaryKey(Long id);
    int insert(Role record);
    Role selectByPrimaryKey(Long id);
    List<Role> selectAll();
    int updateByPrimaryKey(Role record);
	PageResult queryPage(QueryObject qo);
	List<Long> queryRoleIdListForEmployeeForm(Long employeeId);
	void addMenu(ArrayList<Long> ids, Long roleId);

    /**
     * 根据当前用户的id查询所拥有的角色集合
     * @param id 用户id
     * @return 返回角色编码集合
     */
    List<String> queryRoleListByEmployeeId(Long id);
}
