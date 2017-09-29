package com._520it.crm.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Role;
import com._520it.crm.mapper.EmployeeMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.EmployeeQueryObject;
import com._520it.crm.service.IEmployeeService;
import com._520it.crm.service.ISystemMenuService;
@Service
public class EmployeeServiceImpl implements IEmployeeService {
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private ISystemMenuService systemMenuService;
	public int deleteByPrimaryKey(Long id) {
		return employeeMapper.deleteByPrimaryKey(id);
	}
	public int insert(Employee record) {
		//需要给用户默认的密码,默认的状态,默认是普通用户,设置入职时间
		record.setState(Employee.NORMAL);
		record.setAdmin(false);
		record.setInputtime(new Date());
		Md5Hash md5 = new Md5Hash("1",record.getUsername(),1);
		record.setPassword(md5.toString());
		int count = employeeMapper.insert(record);
		//处理员工和角色的中间表关系
		for(Role role:record.getRoles()){
			employeeMapper.insertRelation(role.getId(),record.getId());
		}
		return count;
	}

	public Employee selectByPrimaryKey(Long id) {
		return employeeMapper.selectByPrimaryKey(id);
	}

	public List<Employee> selectAll() {
		return employeeMapper.selectAll();
	}

	public int updateByPrimaryKey(Employee record) {
		int count = employeeMapper.updateByPrimaryKey(record);
		//删除之前所有的关系
		employeeMapper.deleteRelation(record.getId());
		//处理员工和角色的中间表关系
		for(Role role:record.getRoles()){
			employeeMapper.insertRelation(role.getId(),record.getId());
		}
		return count;
	}

	/**
	 * 模拟需求:如果是HR的角色可以看到所有的员工数据
	 *	如果没有HR角色只能看到自己的数据.
	 * @param qo
	 * @return
     */
	public PageResult queryPage(EmployeeQueryObject qo) {
		//获取当前用户的角色,然后判断当前的用户是否有HR角色,如果没有就传自己的id去查询自己所能看到的数据
		Subject subject = SecurityUtils.getSubject();
		if(!subject.hasRole("HR")){
			Employee currentUser=(Employee) subject.getPrincipal();
			qo.setCurrentUserId(currentUser.getId());
		}
		//符合条件的总数
		Long count = employeeMapper.queryPageCount(qo);
		if(count <= 0){
			return new PageResult(0L, Collections.EMPTY_LIST);
		}
		List<Employee> result =  employeeMapper.queryPageData(qo);
		return new PageResult(count, result);
	}

	public void quit(Long id) {
		employeeMapper.changeState(id,Employee.LEAVE);
	}

	@Override
	public Employee queryByName(String username) {
		return employeeMapper.queryByName(username);
	}

}
