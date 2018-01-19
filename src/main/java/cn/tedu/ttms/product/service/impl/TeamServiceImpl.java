package cn.tedu.ttms.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.dao.TeamDao;
import cn.tedu.ttms.product.entity.Team;
import cn.tedu.ttms.product.service.TeamService;
//@Transactional
@Service
public class TeamServiceImpl implements TeamService {
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private ProjectDao projectDao;
	@Override
//	@Transactional(readOnly=true)
	public Map<String, Object> findObjects(Integer valid, Integer projectId,Integer pageCurrent) {
		//1.进行业务逻辑参数验证
		if(valid != null &&  valid != 0 &&valid!=1)
			throw new ServiceException("当前valid值无效,valid=" + valid);
		if(projectId != null &&  projectId <= 0)
			throw new ServiceException("项目id值无效,projectId=" + projectId);
		if(pageCurrent == null ||  pageCurrent <= 0)
		throw new ServiceException("当前页面无效,pageCurrent=" + pageCurrent);
		//2.获取当前数据
		int pageSize = 3;
		int startIndex = (pageCurrent-1)*pageSize;
		List<Map<String , Object>> list = teamDao.findObjects(valid, projectId,startIndex, pageSize);
		//3.获得总记录数，并计算封装分页信息
		int rowCount = teamDao.getRowCount(valid,projectId);
		PageObject pageObject = new PageObject();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setStartIndex(startIndex);
		//4.封装当前页面数据及分页信息到map集合
		Map<String , Object> map = new HashMap<>();
		map.put("list", list);
		map.put("pageObject",pageObject);
		return map;
		
	}
//	@Transactional(rollbackFor=Exception.class)
	@Override
	public void validById(String ids, Integer valid) {
		// 1.验证参数的有效性
		if(ids ==null || ids.length()==0)throw new ServiceException("请选择");
		if(valid !=0 && valid != 1) throw new ServiceException("启用或禁用的状态值不正确");
		//2.执行更新操作
		String [] idArray = ids.split(",");
		int rows = teamDao.validById(idArray, valid);
		//3.验证结果（成功以后返回的结果应该是>=1）
		if(rows <= 0) throw new ServiceException("更新失败，rows=" + rows);
	}
	@Override
	public void saveObject(Team entity) {
		// 1.对参数进行验证
		if(entity == null)throw new ServiceException("保存数据不能为空");
		//2.执行保存操作
		int rows = teamDao.insertObject(entity);
		System.out.println("rows==" + rows);
		if(rows<=0)throw new ServiceException("写入数据失败");
	}
	@Override
	public void updateObject(Team entity) {
		// 1.对参数进行验证
		if(entity == null)throw new ServiceException("修改数据不能为空");
		//2.执行保存操作
		int rows = teamDao.updateObject(entity);
		System.out.println("rows==" + rows);
		if(rows<=0)throw new ServiceException("写入数据失败");
		
	}
//	@Transactional(readOnly=true, propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	@Override
	public Team findObjectById(Integer id) {
		//1.判定参数的有效性
		if(id == null || id <0) throw new ServiceException("id的值无效，ID= " + id);
		//2.执行查找操作
		Team team = teamDao.findObjectById(id);
		//3.根据结果进行判定
		if(team == null)throw new ServiceException("记录不存在");
		return team;
	}
//	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> findPrjIdAndNames() {
		// TODO Auto-generated method stub
		return projectDao.findPrjIdAndNames();
	}

}








