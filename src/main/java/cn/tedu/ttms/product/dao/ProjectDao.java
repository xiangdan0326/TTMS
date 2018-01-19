package cn.tedu.ttms.product.dao;
import java.util.List;
import java.util.Map;

import cn.tedu.ttms.product.entity.Project;
import org.apache.ibatis.annotations.Param;

import cn.tedu.ttms.product.entity.Project;
public interface ProjectDao {
	
	/**
	 * 更新项目信息
	 * @param entity
	 * @return
	 */
	int updateObject(Project entity);
	
	/**
	 * 根据id获取项目对象
	 * @param id
	 * @return
	 */
	Project findObjectById(Integer id);
	
	/**
	 * 保存项目信息
	 * @param entity
	 * @return
	 */
	int insertObject(Project entity);
	
	/**
	 * 执行禁用和启用操作时都执行此函数
	 * @param ids  (要禁用和启动的项目对象的id)
	 * @param valid (禁用或启用状态值:1或者0)
	 * @return 禁用或启用了多少条记录
	 */
	int validById(
		 @Param("id") Integer id,
		 @Param("valid") Integer valid);
	
	/**定义分页查询函数
	 * @param startIndex 对应limit语句中offset,表示从哪个位置取数据
	 * @param pageSize 每页最多显示多少条记录
	 * 
	 * @Param 是MyBatis中的一个用于定义参数的注解,
	 * 一般dao方法中的参数个数多于一个时,建议使用
	 * 此注解进行声明.
	 * 
	 * @return 表示当前页数据
	 * */
	List<Project> findPageObjects(
		@Param("name")String name,
		@Param("valid")Integer valid,
		@Param("startIndex")int startIndex,
		@Param("pageSize")int pageSize,
		@Param("orderBy")String orderBy);
	
	/**获取表中记录的总行数,我们要根据这个结果计算总页数*/
	int getRowCount(
			@Param("name")String name,
			@Param("valid")Integer valid);

	/**查询所有启用项目的id以及名字*/
	List<Map<String, Object>> findPrjIdAndNames();
	

}








