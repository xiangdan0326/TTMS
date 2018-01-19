package cn.tedu.ttms.product.service;

import java.util.List;
import java.util.Map;

import cn.tedu.ttms.product.entity.Team;

public interface TeamService {
	/***
	 * 分页查询图团目信息
	 * @param projectName
	 * @param pageCurrent
	 * @return
	 */
	Map<String,Object> findObjects(Integer valid,Integer projectId,Integer pageCurrent);
	/**查询项目ID和名称*/	
	List<Map<String,Object>> findPrjIdAndNames();
	/**禁用和启用团信息*/
	void validById(String ids , Integer valid);
	/**保存团信息*/
	void saveObject(Team entity);
	/**修改团信息*/
	void updateObject(Team entity);
	/**根据ID查找团信息*/
	Team findObjectById(Integer id);
	
}
