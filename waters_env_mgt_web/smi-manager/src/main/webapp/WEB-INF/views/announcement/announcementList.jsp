<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ taglib uri="/tld/dictionary" prefix="dictionary"%>
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
//-------------------------------------------myview定义--------------------------------------------
var myview = $.extend({},$.fn.datagrid.defaults.view,{
		onAfterRender:function(target){
		    $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
		    var opts = $(target).datagrid('options');
		    var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
		    vc.children('div.datagrid-empty').remove();
		    if (!$(target).datagrid('getRows').length){
		        var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
		        d.css({
		            position:'absolute',
		            left:0,
		            top:50,
		            width:'100%',
		            textAlign:'center'
		        });
		    }
		}
	});
	var url;
//--------------------------------------------初始化页面--------------------------------------------
$(function() {
		$('#searchBt').click(function() {
		var queryParams = $('#announcement_List').datagrid('options').queryParams;
		queryParams.title = $("#title_s").val();
		queryParams.createTime = $('#createTime_s').combobox('getValue'); //设置值
		queryParams.releaseChannel = $('#releaseChannel_s').combobox('getValue'); //设置值
		$('#announcement_List').datagrid('options').queryParams = queryParams;
		$("#announcement_List").datagrid('load');
	});
		$('#clearQry').click(function() {
			$('#queryForm').form('clear');
	});
});
function formatOper(val,row,index){  
	return '<a href="#" onclick="show('+index+')">查看</a> / <a href="#" onclick="del('+index+')">删除</a> ';  
	}  
function show(index) {
		 $('#announcement_List').datagrid('selectRow',index);
		var row = $('#announcement_List').datagrid('getSelected');	
		if (row) {
			$('#show_fm').form('load', row);
			$('#show_dlg').dialog({
				title: '查看公告',
				modal: true
			});
			 $('#show_dlg').dialog('open');
			 $('#realseChannel').combobox('disable');
			 $.post('${ctx}/admin/announcement/getInfo', {id : row.id}, function(result) {
					$('#show_fm').form('load',result.data);
				}, 'json');
		}else{
			$.messager.alert('温馨提示','请选定一行记录再操作!','info');
			return ;
		}
	}
function fixWidth(percent)  
{  
    return document.body.clientWidth * percent ; 
}  

//--------------------------------------------保存--------------------------------------------	
var checkSubmitFlg = false;
	function checkSubmit(){
		if(checkSubmitFlg == true){ 
			return false; //当表单被提交过一次后checkSubmitFlg将变为true,根据判断将无法进行提交。
		}
		checkSubmitFlg = true;
		return true;
	}
function saveAnnouncement(){
		if (checkSubmit()) {
			$('#submit').linkbutton({  
			    disabled:true
			});
			
			$('#edit_fm').form('submit', {
				url : '${ctx}/admin/announcement/saveAnnouncement',
				onSubmit : function() {
					if($(this).form('validate')){
						return true;
					}else{
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
						$('#announcement_List').datagrid('reload');
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
//--------------------------------------------显示新建公告对话框--------------------------------------------
function showCreateAnnou(){
	$('#edit_dlg').dialog({
	 	title: '新建公告',
	    modal: true
	});
	$('#edit_dlg').dialog('open');	
	
	$('#edit_fm').form('clear');
}
//--------------------------------------------显示编辑公告对话框--------------------------------------------
function edit(){
		var row = $('#announcement_List').datagrid('getSelected');
		if(row){
			$('#edit_dlg').dialog({
			 	title: '修改公告',
			    modal: true
			});
			$('#edit_dlg').dialog('open');
			$.post('${ctx}/admin/announcement/getInfo', {id : row.id}, function(result) {
				$('#edit_fm').form('load',result.data);
			}, 'json');
		}else{
			$.messager.alert('温馨提示','请选定一行记录再操作!','info');
			return ;
		}
		
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
//----------------------------------删除公告--------------------------------------------
function del(index) {
	$('#announcement_List').datagrid('selectRow',index);
	var row = $('#announcement_List').datagrid('getSelected');
	if (row) {
			$.messager.confirm('确认', '您确定要删除这条数据吗?', function(r) {
				if (r) {
					$.post('${ctx}/admin/announcement/del', {
						id : row.id
					}, function(result) {
						$('#announcement_List').datagrid('reload'); 
						msg(result);
					}, 'json');
				}
			});
	}else{
		$.messager.alert('温馨提示','请选定一行记录再操作!','info');
		return ;
	}
}
function msg(result){
	if (result.status){
		$('#announcement_List').datagrid('reload');	
	} else {
		$.messager.show({
			title: '提示',
			msg: result.data
		});
	}
}
//--------------------------------------------格式化日期字段--------------------------------------------
function formatterdate(val, row) {
	var date = new Date(val);
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}
</script>
<style type="text/css">
.datagrid-btn-separator {
    float: none;
}
.datagrid-body {
  overflow-x:auto;
  overflow-y:auto;
}
</style>
<body>
	<table id="announcement_List" class="easyui-datagrid" striped="true"  toolbar="#tb" rownumbers="true" pagination="true" singleSelect="true" fitColumns="true" fit="true" style="overflow-x:hidden"
	pageSize="15"  pageList=[15,30,45,60] 
	 data-options="url :'${ctx}/admin/announcement/announcementList',
	 			view: myview,
				singleSelect:true,
				rownumbers:true,
				fitColumns:true,
				selectOnCheck:true,
		      emptyMsg: '暂无数据',
		onDblClickRow : function(rowIndex, rowData) {
		},
		onSelect : function() {
		}" >
		<thead>
			<tr>
			    <th field="id" sortable="true" hidden="hidden" formatter : controllIfSelected sortable="true">ID</th>
				<th field="title" width ="30" align="center">标题</th>
				<th field="createTime"  width ="30" formatter="formatterdate" align="center">时间</th>
				<th field="releaseChannel"  width ="30" align="center">发布频道</th>
				<th field="issue"  width ="30" align="center">发布人</th>
				<th data-options="field:'_operate',width:30,align:'center',formatter:formatOper">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div style="padding: 5px; border-bottom:1px solid #f2f3f5">
		<span class="datagrid-btn-separator"></span>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="showCreateAnnou()">新建</a>
		<span class="datagrid-btn-separator"></span>	
			<a href="#" id="editBt" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="edit()">编辑</a>
		</div>
		<div style="padding: 5px; border-bottom:1px solid #f2f3f5">
			<form id="queryAnnouncement" method="post">
				<table border="0" width="100%" style="margin-left: 20px;">
					<tr>
						<td >
							标题：<input id="title_s" name="title" type="text"/> 
						</td>
						<td>
							时间：<input  class="easyui-datebox " id="createTime_s" name = "createTime"/>						
						</td>
						<td>
							发布频道：
							<select class="easyui-combobox" name="releaseChannel" id="releaseChannel_s" required="true" 
								data-options="valueField:'releaseChannel',textField:'announcementList'" editable="false">
									<option value="">请选择</option>
                				<dictionary:options type="REL_CHANNEL" status="1" />
							</select>					
						</td>
						<td>
							<span class="datagrid-btn-separator"></span>	
								<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 新建公告会话框 -->
	<div  id="edit_dlg" class="easyui-dialog" style="text-align:center;width:500px; " closed="true" buttons="#${entityName}_dlg-buttons" >
			<form id="edit_fm" method="post">
			 <table  style="width:100% ; border-spacing:0px 10px;" class="m_table">
			 	<input name="id" id="id" type="hidden" />
			   <tr>
	      			<td><label class="bitian">标题</label></td>
	      			<td  align="left">
						<input type="text" id="titleShow" style="width:200px" class="easyui-validatebox" size="49" name="title" id="title" required="true"/>
					</td>
			   </tr>
			   <tr >
	      			<td><label class="bitian">公告内容</label></td>
	      			<td align="left" >
						<textarea name="content" id="content" maxlength="200" style="width: 70%" rows="5"></textarea>	
					</td>	
			   </tr> 
			   <tr>
	      			<td><label>发布渠道</label>
	      			<td align="left">
						<select class="easyui-combobox" name="releaseChannel" id="releaseChannel" >
                			<dictionary:options type="REL_CHANNEL" status="1" />
						</select>
					</td>
			   </tr>
			</table>
			</form>
		</div>
		<div id="${entityName}_dlg-buttons" style="text-align:center;width:500px; ">
			<a href="#" id="submit" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAnnouncement();">提交</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#edit_dlg').dialog('close')">取消</a>
		</div>
		<!-- 查看公告 -->	
		<div id="show_dlg" class="easyui-dialog" style="text-align:center;width:500px; " closed="true" >
			<form id="show_fm" method="post">
			 <table  style="width:100% ; border-spacing:0px 10px;" class="m_table">
			   <tr >
	      			<td><label class="bitian">标题</label></td>
	      			<td align="left">
						<input readonly="readonly" type="text" id="titleshows" style="width:200px" class="easyui-validatebox" size="49" name="title" id="title" />
					</td>
			   </tr>
			   <tr >
	      			<td><label class="bitian">公告内容</label>
	      			<td align="left" >
						<textarea disabled="false" name="content" id="contentshow" maxlength="200" style="width: 70%" rows="5" ></textarea>	
					</td>	
			   </tr> 
			   <tr>
	      			<td><label>发布渠道</label>
	      			<td align="left">
					<select disabled="false" class="easyui-combobox" name="releaseChannel" id="rc"  >
                			<dictionary:options type="REL_CHANNEL" status="1" />
					</select>
					</td>
			   </tr>
			</table>
			</form>
		</div>
</body>