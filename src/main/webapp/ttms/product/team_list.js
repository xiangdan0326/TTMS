$(function(){
	$("#queryFormId").on("click",".btn-search",doQueryObjects)
	.on("click",".btn-valid, .btn-invalid",doValidById)
	.on("click",".btn-add, .btn-update",doShowEditDialog);
	doGetProjectPageObjects();
	doGetObjects();
});

/*显示编辑对话框(内部会加载URL对应的页面)*/
function doShowEditDialog() {
	var title;
	if($(this).hasClass("btn-add")) {
		title="添加团信息";
	}
	if($(this).hasClass("btn-update")) {
		//修改时一般会在模态框上绑定一个id值，编辑页面加载完成要根据此id值查询团信息
		//1.获取当前行的id值
//		var  trId= $(this).parent().parent().data("id");
		var trId=$(this).parent().parent().data("id");
		console.log(trId);
		//2.将id值绑定到模态框
		$("#modal-dialog").data("id",trId);
		title = "修改团信息,id=" +$("#modal-dialog").data("id");
	}
	var url = "team/editUI.do";
	$("#modal-dialog .modal-body").load(url,function(){
		//更新模态框标题
		$(".modal-title").html(title);
		//显示模态框
		$("#modal-dialog").modal("show");
	});
	
}

/*实现团信息的禁用和启用*/
function doValidById() {
	var valid;
	if($(this).hasClass("btn-valid")){
		valid=1;
	}
	if($(this).hasClass("btn-invalid")){
		valid=0;
	}
//	return valid;
	console.log("valid="+valid);
	var checkedIds = getCheckedIds();
	var params = {"valid":valid,"checkedIds":checkedIds};
	var url = "team/doValidById.do";
	$.post(url,params,function(result) {
		if(result.state == 1) {
			doGetObjects();
		} else{
			alert("result.message");
		}
	});
}

/*获得用户选中的团记录的id*/
function getCheckedIds() {
	debugger;
	var checkedIds = "";//定义一个字符串对象
	$('#tbodyId input[name="checkItem"]').each(function(){
		//this	代表的是input对象
		if($(this).prop("checked")) {
			if(checkedIds == "") {
				checkedIds += $(this).val();
			} else {
				checkedIds += "," +$(this).val();
			}
		}
	});
	console.log("checkedIds = " +checkedIds);
	return checkedIds;
}

/*获取项目信息中的id和name，然后通过此数据初始化select列表*/
function doGetProjectPageObjects() {
	var url = "team/doFindPrjIdAndNames.do";
	$.getJSON(url,function(result){
		if(result.state == 1) {
			doInitProjectSelect(result.data);
		}else{
			alert(result.message);
		}
	});
}

/*初始化项目select(id与name列表)*/
function doInitProjectSelect(list) {
	var select = $("#searchPrjId");
	select.append('<option value="">选择归属</option>');
	var option = "<option value=[id]>[name]</option>";
	for(var i in list) {
		select.append(option.replace("[id]",list[i].id).replace("[name]",list[i].name));
	}
}

function doQueryObjects(){
	$("#pageId").data("pageCurrent",1);
	doGetObjects();
}
function doGetObjects(){
	//1.通过异步请求($.post)获得服务端团信息
	var url="team/doFindObjects.do";
	var params={"projectId":$("#searchPrjId").val(),"valid":$("#searchValidId").val()};
	console.log(JSON.stringify(params));
	var pageCurrent=$("#pageId").data("pageCurrent");
	if(!pageCurrent)pageCurrent=1;
	params.pageCurrent=pageCurrent;
	console.log(params)
	$.getJSON(url,params,function(result){
		if(result.state==1){
			setTableBodyRows(result.data.list);
			setPagination(result.data.pageObject);
		}else{
			alert(result.message);
		}
	});
}

/*获取查询表单中的数据*/

function setTableBodyRows(list){
	debugger
	var tBody=$("#tbodyId");
	tBody.empty();
	var tds=
	"<td><input type='checkbox' name='checkItem' value='[id]'></td>"+
//	"<td id='No'>[No]</td>"+
	"<td>[name]</td>"+
	"<td>[projectName]</td>"+
	"<td>[state]</td>"+
	"<td><button type='button' class='btn btn-default btn-update'>修改</button></td>"
	for(var i in list){
		var tr=$("<tr></tr>");
		tr.data("id",list[i].id);
//		$("#tbodyId").find("tr").each(function(i){
//			$(this).find("#No").html(i+1);
//		});
		tBody.append(tr.append(tds.replace('[id]',list[i].id)
//			.replace('[No]',list[i].No)
		   .replace('[name]',list[i].name)
		   .replace('[projectName]',list[i].projectName)
		   .replace('[state]',list[i].valid?"有效":"无效")));
		
	}
}











