package cn.tedu.ttms.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.Team;
import cn.tedu.ttms.product.service.TeamService;
@Controller
@RequestMapping("/team/")
public class TeamController {
	@Autowired
	@Qualifier("teamServiceImpl")
	private TeamService teamService;
	@RequestMapping("listUI")
	public String listUI() {
		return "product/team_list";
	}
	@RequestMapping("editUI")
	public String editUI() {
		return "product/team_edit";
	}
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects(Integer valid,Integer projectId,Integer pageCurrent) {
		Map<String,Object> map = teamService.findObjects( valid, projectId,pageCurrent);
		return new JsonResult(map);
	}
	@RequestMapping("doFindPrjIdAndNames")
	@ResponseBody
	public JsonResult doFindPrjAndNames() {
		List<Map<String,Object>> list = teamService.findPrjIdAndNames( );
		return new JsonResult(list);
	}
	
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(String checkedIds, Integer valid) {
		teamService.validById(checkedIds, valid);
		return new JsonResult();
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(Team entity) {
		teamService.saveObject(entity);
		return new JsonResult();
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(Team entity) {
		teamService.updateObject(entity);
		return new JsonResult();
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		Team team =  teamService.findObjectById(id);
		return new JsonResult(team);
	}
	
}
