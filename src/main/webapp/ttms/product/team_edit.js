$(function(){
	doGetProjectIdAndNames();
	//1.模态框上绑定click事件
	$("#modal-dialog").on("click",".ok",doSaveOrUpdate)
	//2.模态框隐藏时移除click事件,移除绑定的数据
	$("#modal-dialog").on("hidden.bs.modal",function(){
		$("#modal-dialog").off('click','.ok');
		$("#modal-dialog").removeData("id");
	});
	//3.获取模态框上绑定的id值(可能有值也可能没有)
//	var id=$("#modal-dialog").data("id");
	//假如id有值,一定是修改,然后根据id查记录,
	//将记录信息初始化到表单中
//	if(id)doFindObjectById(id);
});
/*根据id查找记录*/
function doFindObjectById(id){
	debugger;
	var url="team/doFindObjectById.do";
	var id=$("#modal-dialog").data("id");
	var params={"id":id};
	$.getJSON(url,params,function(result){
		if(result.state==1){
			setEditFormData(result.data);
		}else{
			alert(result.message);
		}
	});
}
/*通过查询的记录信息初始化表单页面*/
function setEditFormData(result){
	$("#nameId").val(result.name);
	$("#projectId").val(result.projectId);
	$("#noteId").html(result.note);
	$("#editFormId input[name='valid'][value='"+result.valid+"']").prop("checked",true);
	/*$("#editFormId input[name='valid']")
	.each(function(){
		if($(this).val()==result.valid){
			$(this).prop("checked",true);
		}
	})*/
	//难点:valid状态值的设置(可参考1.02)
}

/*执行保存或更新操作*/
function doSaveOrUpdate(){
	//0.对表单数据进行非空验证
	if(!$("#editFormId").valid())return;
	//1.获取表单数据
	var params=getEditFormData();
	//2.异步提交表单数据
	var insertUrl="team/doSaveObject.do";
	var updateUrl="team/doUpdateObject.do";
	var id=$("#modal-dialog").data("id");
	var url=id?updateUrl:insertUrl;
	if(id)params.id=id;//修改时需要根据id进行修改,
	//当key比较复杂时可采用params['key']=value形式赋值
	$.post(url,params,function(result){
		if(result.state==1){
		  alert(result.message);
		  doGetObjects();
		  $("#modal-dialog").modal('hide');
		}else{
		  alert(result.message);
		}
	});
}
/*获取表单数据*/
function getEditFormData(){
	var params={
	    "name":$("#nameId").val(),
	    "projectId":$("#projectId").val(),
	    "valid":$("input[name='valid']:checked").val(),
	    "note":$("#noteId").val()
	};
	return params;
}

/*获取项目的id和名称*/
function doGetProjectIdAndNames() {
	var url = "team/doFindPrjIdAndNames.do";
	$.getJSON(url,function(result){
		if(result.state ==1) {
			doInitProjectSelect(result.data);
			var id = $("#modal-dialog").data("id");
			if(id)doFindObjectById();
		} else {
			alert(result.message);
		}
	});
}
/*初始化所属项目的select下拉框*/
function doInitProjectSelect(list) {
	var select = $("#projectId");
	select.append('<option >===请选择===</option>');
	var option = "<option value=[id]>[name]</option>";
	for(var i in list) {
		select.append(option.replace("[id]",list[i].id).replace("[name]",list[i].name));
	}
}










