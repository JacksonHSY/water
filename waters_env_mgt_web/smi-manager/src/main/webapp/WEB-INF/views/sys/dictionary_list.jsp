<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/particular.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/popup.css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/extension/jquery-easyui-crud/jquery.uigrid.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/crmmain.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/crm.css"/>
<script type="text/javascript" src="${ctx}/static/js/date/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/utilTools.js"></script>
<%@ include file="/static/common/doing.jsp"%>
<script type="text/javascript">
var dataType='WS_TYPE';
var ids = [];
$(function() {
	 $('#dic_list').datagrid({
	    url:'${ctx}/admin/dictionary/findByDataType',
	    striped:true,
	    singleSelect:true,
	    rownumbers:true,
	    fitColumns:true,
	    selectOnCheck:true,
	    columns:[[
			{field : 'id',title : '',align : 'center',width : fixWidth(0.05),sortable : true,formatter : controllIfSelected},      
			{field:'dataType',title:'字典类型',width:fixWidth(0.2),align:'center'},
			{field:'dataName',title:'字典名称',width:fixWidth(0.2),align:'center'},
			{field:'dataValue',title:'值',width:fixWidth(0.2),align:'center'},
			{field:'status',title:'状态',width:fixWidth(0.2),align:'center',formatter: function(value,row,index){
				if (value=="1"){
					return "启用";
				} else {
					return "停用";
				}
			}}
			
			]]
	}); 
	
});

function fixWidth(percent)  
{  
    return document.body.clientWidth * percent ; 
}  
//--------------------------------------------根据字典类型获取详细的字典键值对--------------------------------------------
 function getDetail(type,typeName) {
	dataType=type;
	dataName=typeName;
	ids=[];	
 	$('#dic_list').datagrid('reload', {
		'dataType' : type
	});
	/* $('#dic_list').datagrid({
		url: '${ctx}/admin/dictionary/findByDataType',
		loadMsg:"waiting..."
	}); */ 

 }

//--------------------------------------------显示新建字典对话框--------------------------------------------
function showCreateDia(){
	$('#edit_dlg').dialog({
	 	title: '新建字典',
	    modal: true
	});
	$('#edit_dlg').dialog('open');	
	$('#edit_fm').form('clear');
	$('#dataType').combobox('setValue', dataType);	
	$('#dataValue').removeAttr("readonly"); 
}
//--------------------------------------------显示编辑字典对话框--------------------------------------------
	function showEditDia() {
	if(ids.length!=1){
		$.messager.alert('温馨提示','请选定一行记录再操作!','info');
		return ;
	}

	$('#edit_dlg').dialog({
	 	title: '修改字典',
	    modal: true
	});
	$('#edit_dlg').dialog('open');
	var row = $('#dic_list').datagrid('getSelected');
	$("#dataType").removeAttr("readonly");
	/* comboboxUtil.setComboboxByData("dataType", dataNameList, 'dataType', 'dataName', '100', true); */
	/* $('#dataType').combobox({editable:false,disabled:true });
	$('#dataValue').attr("readonly","readonly"); */
	$.post('${ctx}/admin/dictionary/findDicById', {id : ids[0]}, function(result) {
		$('#edit_fm').form('load',result);
		$('#dataType').combobox('setValue', result.dataType);	
	}, 'json');


} 
//---------------------------------保存--------------------------------------------
var checkSubmitFlg = false;
	function checkSubmit(){
		if(checkSubmitFlg == true){ 
			return false; //当表单被提交过一次后checkSubmitFlg将变为true,根据判断将无法进行提交。
		}
		checkSubmitFlg = true;
		return true;
	}
	
function saveDictionary() {
	if (checkSubmit()) {
		$('#submit').linkbutton({  
		    disabled:true
		});
		
		$('#edit_fm').form('submit', {
			url : '${ctx}/admin/dictionary/saveDictionary',
			onSubmit : function() {
				if($(this).form('validate')){
					return true;
				}else{
					debugger;
					$('#submit').linkbutton({  
					    disabled:false
					});
					checkSubmitFlg = false;
					return false;
				}
			},
			success : function(result) {
				$('#submit').linkbutton({  
				    disabled:false
				});
				
				var result = eval('(' + result + ')');
				if (result.status) {
					ids=[];				
					$('#edit_dlg').dialog('close');
					$('#dic_list').datagrid('reload');
				//	window.location.href = '${namespacePath}/list';
				} else {
					$.messager.show({
						title : '提示',
						msg : result.data
					});
				}
				
				checkSubmitFlg = false;
			}
		})
	}
}

//----------------------------------启用停用--------------------------------------------
function updateStatusDic(status) {
	var info='';
	if (ids.length == 0) {
		$.messager.alert('温馨提示','请选定一行记录再操作!','info');
		return ;
	}
	if(status==1){
		info='确定启用吗?';
	}
	if(status==0){
		info='确定停用吗?';
	}
	$.messager.confirm('确认',info , function(r) {
		if (r) {
			$.post('${ctx}/admin/dictionary/updateStatus?ids='+ids, {
				status:status
			}, function(result) {
				if (result.status){
					$('#dic_list').datagrid('reload');	// reload the user data
					ids=[];
				} else {
					$.messager.show({
						title: 'Error',
						msg: result.data
					});
				}
			}, 'json');
		}
	}); 
}
//----------------------------------操作单选框方法----------------------------------

//列表勾选框事件
function controllIfSelected(value, rec) {
	return "<input type=\"checkbox\" onchange='opSelected(" + value
			+ ",this)' />";
}
function opSelected(id, obj) {
	if (obj.checked) {
		if (contains(ids, id) == -1) {
			ids.push(id);
		}
	} else {
		removeFromArray(ids, id);
	}
}
//是否已勾选
function contains(array, key) {
	for (var i = 0; i < array.length; i++) {
		if (key == array[i]) {
			return i;
		}
	}
	return -1;
}
function removeFromArray(array, key) {
	var index;
	index = contains(array, key);
	if (index != -1) {
		ids.splice(index, 1);
	}
}
</script>
<style type="text/css">
.datagrid-btn-separator {
    float: none;
}
</style>
<body class="easyui-layout">
    <div data-options="region:'west',split:true" style="width:200px;">
    	<ul id="tt" class="easyui-tree">
		    <li>
				<span>字典列表</span>
				<ul>
					<c:forEach var="item" items="${dictiontypes}"> 
               			<li>
               				<span>
               				<div onclick="getDetail('${item.key}','${item.value}');" style="height:20px">
               				${item.value}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               					&nbsp;&nbsp;&nbsp;</div>
               				</span>
               			</li> 
					</c:forEach>			
				</ul>
			</li>
		</ul>
    </div>
    <div data-options="region:'center'" style="padding:5px;">
    	<!-- 以下表头工具栏-->
		<div region="north" border="false" style="overflow:hidden; border-bottom:1px solid #ccc;" >
		<div style="padding: 5px; border-bottom:1px solid #f2f3f5">
		<span class="datagrid-btn-separator"></span>
							<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showCreateDia();" >新建</a>
		<span class="datagrid-btn-separator"></span>					
							<a href="#" id="editBt" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showEditDia()">编辑</a> 
		<span class="datagrid-btn-separator"></span>					
							<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="updateStatusDic(1);">启用</a>
		<span class="datagrid-btn-separator"></span>					
							<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="updateStatusDic(0);">停用</a>	
		<span class="datagrid-btn-separator"></span>						
			</div>			
		</div>
		<!-- 以下列表 -->
		<div region="center" border="false">
			<table id="dic_list"></table>
		</div>
		<!-- 新建 编辑对话框 -->
		<div id="edit_dlg" class="easyui-dialog" style="text-align:center;width:500px; " closed="true" buttons="#${entityName}_dlg-buttons">
			<form id="edit_fm" method="post">
			<input name="id" id="id" type="hidden">
			<table style="font-size:12px; width:100%; text-align:right;" singleSelect="true">
				<tr>
					<td><label>字典类型：</label></td>
					<td align="left">
						<select class="easyui-combobox" name="dataType" id="dataType" style="width: 170px" required="true" disabled="disabled"/>
						</select>
					</td>				
				 <tr>
					<td><label>字典名称：</label></td>
					<td align="left"><input id="dataName" name="dataName" class="easyui-validatebox" required="true" maxLength="50"></td>										
				</tr>
				<tr>			
					<td><label>值：</label></td>
					<td align="left"><input id="dataValue" name="dataValue" class="easyui-validatebox" required="true" maxLength="1000"></td>		
				</tr>				
			</table>
			</form>
		</div>
		<div id="${entityName}_dlg-buttons" style="text-align:center;width:500px; ">
			<a href="#" id="submit" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveDictionary();">提交</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#edit_dlg').dialog('close')">取消</a>
		</div>	
    </div>
</body>

