package cn.tedu.ttms.product.service;
import java.util.Map;
import cn.tedu.ttms.product.entity.Project;
/**
 * 项目管理模块的业务层对象，负责具体项目信息的业务处理
 * @author xiangdan
 *
 */
public interface ProjectService {
	/**
	 * 更新项目信息
	 * @param entity
	 * @return
	 */
	 void updateObject(Project entity);
	
	 /**
	  * 根据id查找project对象
	  * @param id
	  * @return
	  */
	 Project findObjectById(Integer id);
	 
	
	  /**
	   * 保存项目信息
	   * @param entity
	   */
	  void saveObject(Project entity);
	  
	  /**禁用启用项目信息*/
	  void validById( Integer id, Integer valid);
	  /**获得当前页面信息以及分页信息
	   * 1) 项目信息封装到List<project>
	   * 2) 分页信息封装到PageObject将项目信息和分页信息再次封装，封装map然后统一返回
	   * @param pageCurrent 当前页码的值
	   * @return 返回当前页数据以及分页信息
	   */
	  Map<String,Object>  findPageObjects(String name, Integer valid, Integer pageCurrent);
}





