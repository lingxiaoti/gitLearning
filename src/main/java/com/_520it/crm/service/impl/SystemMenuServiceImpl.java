package com._520it.crm.service.impl;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.SystemMenu;
import com._520it.crm.mapper.SystemMenuMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.ISystemMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class SystemMenuServiceImpl implements ISystemMenuService {
	@Autowired
	private SystemMenuMapper systemMenuMapper;
	
	public int deleteByPrimaryKey(Long id) {
		return systemMenuMapper.deleteByPrimaryKey(id);
	}

	public int insert(SystemMenu record) {
		return systemMenuMapper.insert(record);
	}

	public SystemMenu selectByPrimaryKey(Long id) {
		return systemMenuMapper.selectByPrimaryKey(id);
	}

	public List<SystemMenu> selectAll() {
		return systemMenuMapper.selectAll();
	}

	public int updateByPrimaryKey(SystemMenu record) {
		return systemMenuMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		Long count = systemMenuMapper.queryPageCount(qo);
		if(count<=0){
			return new PageResult(0L, Collections.EMPTY_LIST);
		}
		List<SystemMenu> result = systemMenuMapper.queryPageDataResult(qo);
		PageResult pageResult = new PageResult(count,result);
		return pageResult;
	}

	@Override
	public List<SystemMenu> queryTree() {
		return systemMenuMapper.queryTree();
	}

	@Override
	public List<SystemMenu> queryForRole() {
		return systemMenuMapper.queryTree();
	}

	@Override
	public List<Long> queryMenuIdsListForRole(Long roleId) {
		return systemMenuMapper.systemMenuMapper(roleId);
	}

	@Override
	public List<SystemMenu> indexMenu() {
		Employee current=(Employee) SecurityUtils.getSubject().getPrincipal();
		List<SystemMenu> userMenus = systemMenuMapper.queryTree();
		//判断当前的用户是否是管理员,如果是,拥有全部的菜单权限
		if(current.isAdmin()){
			return userMenus;
		}else {
		//如果不是管理员,根据当前用户的id去查询用户的菜单id集合,然后筛选出该用户的菜单集合
			List<Long> ids=systemMenuMapper.queryMenuIdsByEmployeeId(current.getId());
			//筛选操作
			filterMenu(userMenus,ids);
			return userMenus;
		}
	}

	//筛选出当前用户所拥有的菜单集合
	private void filterMenu(List<SystemMenu> userMenus, List<Long> ids) {
		SystemMenu menu=null;
		//所有菜单集合
		for(int i=userMenus.size()-1;i>=0;i--){
			menu= userMenus.get(i);
			if(!ids.contains(menu.getId())){//如果不在ids集合中,就删除元素
				userMenus.remove(i);
				continue;
			}else {
				if(menu.getChildren() !=null&&menu.getChildren().size()>0){
				filterMenu(menu.getChildren(),ids);
				}
			}
		}
	}
}
