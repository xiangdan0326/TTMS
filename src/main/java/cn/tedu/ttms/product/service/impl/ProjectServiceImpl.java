package cn.tedu.ttms.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;
@Service 
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectDao projectDao;
	
	/**执行修改操作
	 * @param entity指向的对象封装了页面上要修改的数据
	 */
	@Override
	public void updateObject(Project entity) {
		if(entity==null)
		throw new ServiceException("更新数据不能为空");
		int rows=projectDao.updateObject(entity);
		if(rows<=0)
		throw new ServiceException("更新失败");
	}
	/**  根据ID查找project对象 */
	@Override
	public Project findObjectById(Integer id) {
		//1.验证id的有效性
		if(id==null||id<1)
		throw new ServiceException("id的值无效");
		//2.根据id查找数据
		Project pro=projectDao.findObjectById(id);
		//3.验证结果是否正确
		if(pro==null)
		throw new ServiceException("此记录已经不存在");
		return pro;
	}
	
	/**执行写入操作
	 * @param entity 封装用户页面上输入的数据
	 */
	@Override
	public void saveObject(Project entity) {
		//1.验证参数的有效性
		if(entity==null)
		throw new ServiceException("保存对象不能为空");
		//2.执行保存操作
		int rows= projectDao.insertObject(entity);
		//3.验证结果
		if(rows<=0)
		throw new ServiceException("保存失败");
	}
	
	/**
	 * 启动或禁用项目信息
	 * @param checkedIds 包含页面上选中的多个ID值
	 * @param valid 具体启用和禁用的值
	 */
	@Override
	public void validById(Integer id ,//1,2,3,4
			Integer valid) {
		/*//1.验证业务数据的有效性
		if(StringUtils.isEmpty(checkedIds))
		throw new ServiceException("请先选择");
		if(valid!=0&&valid!=1)
		throw new ServiceException("启用或禁用的状态值不正确");
		//2.执行更新操作
		String[] ids=checkedIds.split(",");
		int rows=projectDao.validById(id, valid);
		//3.验证更新结果(成功,失败)
		if(rows<=0)
		throw new ServiceException("更新失败,rows="+rows);*/
		if (id == null)
			throw new NullPointerException("项目id不能为空");
		int rows = projectDao.validById(id, valid);
		if (rows != 1)
			throw new ServiceException("修改失败");
		
	}
	
	
	
	
	/**查询、获取项目信息*/
	@Override
	public Map<String, Object> findPageObjects( String name, Integer valid, Integer pageCurrent) {
		//1.验证参数的有效性
		if(pageCurrent==null||pageCurrent<1)
		throw new ServiceException("参数值无效,pageCurrent="+pageCurrent);
		//2.获取当前页数据
		//2.1 计算startIndex的值
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		//2.2 根据startIndex的值获取当前页数据
		List<Project> list = projectDao.findPageObjects( name, valid, startIndex, pageSize, "createdTime");
		//3.获取总记录数(根据此值计算总页数)
		int rowCount = projectDao.getRowCount(name,valid);
		PageObject pageObject=new PageObject();
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setStartIndex(startIndex);//可选
	    //4.封装查询和计算结果到map对象
		//1)HashMap底层结构?(数组+链表+红黑树)-->JDK1.8
		//2)HashMap是否线程安全?(不安全,多线程并发访问)
		//3)HashMap是否能保证添加元素的有序性(不能,
		//假如希望保证有序性可以选择LinkedHashMap)?
		//4)HashMap在并发场景下如何使用?(将其转换为同步集合或者
		//直接使用ConcurrentHashMap)
		
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("list", list);
		map.put("pageObject",pageObject);
		return map;
	}
}






