$(document).ready(function(){
	doGetObjects();
	$("#queryFormId")
	.on("click",".btn-search",doQueryObjects)
	.on("click",".doValidById",doValidById)
	.on("click",".btn-add,.btn-update",doShowEditDialog);
});
function doShowEditDialog(){
	var title;
	if($(this).hasClass("btn-add")){
		title="添加项目信息"
	}
	if($(this).hasClass("btn-update")){
		//获取button所在行的id值
		var id=$(this).parent().parent().data("id");
		//将id值绑定到模态框上(根据此id判定要执行添加还是修改)
		$("#modal-dialog").data("id",id);
		title="修改项目信息,id="+id;
	}
	var url="project/editUI.do";//project_edit.jsp
	$("#modal-dialog .modal-body")
	.load(url,function(){
		$("#myModalLabel").html(title);
		//显示模态框
		$("#modal-dialog").modal('show');
	});
}

/*禁用或启用项目信息*/
function doValidById(){
	console.log("doValidById");debugger
	var id = $(this).parent().parent().data('id');;
	//1.获取向服务端要传递的参数数据
	//1.1根据操作(禁用,启动)设置状态信息
	var content = $(this).text();
	var valid;
	if(content=="启用"){
		valid=1;
	}
	if(content=="禁用"){
		valid=0;
	}
	console.log("valid="+valid);
	//1.2获取选中(checkbox)的项目id值
	//2.发起异步请求,禁用或启用项目信息?
	var url="project/doValidById.do";
	var params={"id":id,"valid":valid};
	$.post(url,params,function(result){
		if(result.state==1){
			alert("修改成功");
			doGetObjects();
		}else{
			alert(result.message);
		}
	});
}



function getCheckedIds(){
//	debugger
	var checkedIds="";
	$("#tbodyId input[name=checkItem]")
	.each(function(){
		//this 代表的是input对象
		if($(this).prop("checked")){//checked等于true表示选中了
			if(checkedIds==""){//1,2,3,4
				checkedIds+=$(this).val();
			}else{
				checkedIds+=","+$(this).val();
			}
		}
	});
	console.log("checkedIds="+checkedIds);
	return checkedIds;
}



function doQueryObjects(){
	//1.初始化当前页码信息
	$('#pageId').data("pageCurrent",1);
	//2.执行查询操作
	//2.1获得表单数据
	//2.2提交表单数据执行查询
	doGetObjects();
}
/*获取表单数据*/
function getQueryFormData(){
	var params={
		"name":$("#searchNameId").val(),
		"valid":$("#searchValidId").val()
	}
	return params;
}
/*异步(ajax)加载服务端数据*/
function doGetObjects(){
	//1.定义访问项目信息的url
	var url="project/doFindPageObjects.do";
	//2.获取表单数据(查询时用)
	var params=getQueryFormData();
	//3.动态设置分页页码数据
	var pageCurrent=$('#pageId').data("pageCurrent");
	if(!pageCurrent)pageCurrent=1;
	params.pageCurrent=pageCurrent;
	//4.发起异步ajax请求{name:"",valid:"",pageCurrent:1}
	$.get(url,params,function(result){//result-->JsonResult-->{}
		//debugger;
		//console.log(result);
		if(result.state==1){
		  //设置当前页数据
		  setTableBodyRows(result.data.list);
		  //设置分页信息(setPagination方法在page.js中)
		  //console.log(result.pageObject);//undefined
		  setPagination(result.data.pageObject);
		}else{
		  alert(result.message);
		}
	});
}
/*将服务端返回的json对象数据显示在页面上*/
function setTableBodyRows(list){
debugger
	//debugger
	//1.获得具体dom对象(显示数据位置的那个dom)
	var tBody=$("#tbodyId");//jquery的函数
	tBody.empty();
	//2.迭代result对象
	var tds =
		'<td>[code]</td>'+
		'<td>[name]</td>'+
		'<td>[beginDate]</td>'+
		'<td>[endDate]</td>'+
		'<td><font color="[colorVal]">[stateStr]</font></td>'+
		'<td>[note]</td>'+
		'<td><a class="btn btn-primary btn-update">修改</a>'+
		'<a class="btn btn-primary doValidById">[state]</a></td>';
	for(var i in list){//for(var i=0;i<result.length;i++)
	  //2.1构建tr对象
		var tr=$("<tr></tr>");
		tr.data("id",list[i].id);
		
		var state,stateStr,colorVal;
		if(list[i].valid == 0){
			stateStr = '无效';
			state = "启用";
			colorVal = 'red';
		}
		if(list[i].valid == 1){
			stateStr = '有效';
			state = "禁用";
			colorVal = 'green';
		}
		tBody.append(tr.append(tds.replace('[id]',list[i].id)
		                    .replace('[code]',list[i].code)
							.replace('[name]',list[i].name)
							.replace('[beginDate]',list[i].beginDate)
							.replace('[endDate]',list[i].endDate)
							.replace('[stateStr]',stateStr)
							.replace('[colorVal]',colorVal)
							.replace('[note]',list[i].note)
							.replace('[state]',state)));
	}
}



