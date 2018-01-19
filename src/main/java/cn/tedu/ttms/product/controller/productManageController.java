package cn.tedu.ttms.product.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;

@RequestMapping("/product/")
@Controller
public class productManageController {//BaseController
	@Autowired
	@Qualifier("projectServiceImpl")
	private ProjectService projectService;
	
	@RequestMapping("listUI")
    public String listUI(){
		return "product/productManageList";
    }
	@RequestMapping("editUI")
	public String editUI(){
		return "product/project_edit";
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id){
		return new JsonResult(
		 projectService.findObjectById(id));
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(Project entity){
		projectService.updateObject(entity);
		return new JsonResult();
	}

	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(Project entity){
		/*当执行这个数据保存时假如出现了异常,spring mvc如何处理?
		1)先检测当前控制层对象内容是否定义了这个异常的处理函数,
		2)假如定义了就直接调用异常处理函数处理
		3)假如没有定义要检测是否有全局异常处理类(@ControllerAdvice),
		然后在全局异常处理类中查找对应的异常处理函数处理异常.
		*/
		projectService.saveObject(entity);
		return new JsonResult();//{message:ok,state:1}
	}

	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(
			String checkedIds,
			Integer valid){
		    //state (状态码:1,0)
		    //message(ok,error)
		    return new JsonResult();
		  //{state:1,message:"ok",data:null}
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String name,
			Integer valid,
			Integer pageCurrent){
		     //state
		     //message
		     //data
		 Map<String,Object> map=projectService
		.findPageObjects(name,valid,pageCurrent);
		return new JsonResult(map);
		/*
		{
		  state:1,
		  message:ok,
		  data:{list:[{},{},{}],pageObject:{}}	
		}
		$.getJSON(url,params,function(result){
		    result.state
		    result.message
		    result.data.list
		    result.data.pageObject
		})
		*/
	}

}






