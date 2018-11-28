<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>

<html>
<head>
<title>角色列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/default/tooltip.css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.tooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script>
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

		//$('#tt').datagrid("options").url="${ctx}/role/rolePage";
/*   		$('#tt').datagrid({
 			  url :"${ctx}/role/rolePage",
 			  pageSize :15,
 			  pageList:[15,30,45,60],
		      view: myview,
		      emptyMsg: '暂无数据'
		});
 */  		$('#searchBt').click(function() {
			var queryParams = $('#tt').datagrid('options').queryParams;
			//设置值
			queryParams.roleName = $('#roleName').val(); 
			queryParams.roleCode = $('#roleCode1').val();
			queryParams.status = $('#status').combobox('getValue'); 
			$('#tt').datagrid('options').queryParams = queryParams;
			$("#tt").datagrid('load');
		});

	});
	
	$(function(){
	  $('#fm input').each(function () {
            if ($(this).attr('required') || $(this).attr('validtype'))
                $(this).validatebox();
        })
	 });
	
	//--------------------------------------------格式化日期字段--------------------------------------------
	function formatterdate(val, row) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
	
	//--------------------------------------------新建角色页面--------------------------------------------	
	function _new() {
		$('#dlg').dialog({
			title: '新建角色',
			modal: true
		});
		$('#dlg').dialog('open');
		$('#fm').form('clear');
		$("#roleCode").removeAttr("disabled");
		$("#status1").combobox({disabled:true});  
		$('#status1').combobox('setValue','1');
		url = '${ctx}/admin/role/save';
	}

	//--------------------------------------------编辑角色页面--------------------------------------------
	function edit() {
		$("#roleCode").removeAttr("onblur");
		var row = $('#tt').datagrid('getSelected');		
		if (row) {
			$('#fm').form('load', row);
			$('#dlg').dialog({
				title: '编辑角色',
				modal: true
			});
			$('#dlg').dialog('open');
 			if(row.systemCode=='0'||row.systemCode=='2'){
				$('#systemCode_a').attr('checked','checked');				
			}
			if(row.systemCode=='1'||row.systemCode=='2'){
				$('#systemCode_w').attr('checked','checked');				
			}
			$("#roleCode").attr("disabled","disabled");
			$("#status1").combobox({disabled:false});
			url = '${ctx}/admin/role/save?id=' + row.id;
		}else{
			$.messager.alert('温馨提示','请选定一行记录再操作!','info');
			return ;
		}
	}

	//--------------------------------------------删除角色页面--------------------------------------------
	function removeRole() {
		var row = $('#tt').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认', '确定删除吗?', function(r) {
				if (r) {
					$.post('${ctx}/admin/role/remove', {
						id : row.id
					}, function(result) {
						$('#tt').datagrid('reload'); 
						msg(result);
					}, 'json');
				}
			});
		}else{
			$.messager.alert('温馨提示','请选定一行记录再操作!','info');
			return ;
		}
	}

	//--------------------------------------------光标移开校验角色编号唯一性--------------------------------------------
	function checkroleCode(){
		var roleCode =$("#roleCode").val();
		if(roleCode==""){
			return;
		}
		$.post('${ctx}/admin/role/checkroleCode', {
			roleCode : roleCode
		}, function(result) {
			if (!result.status){
				$.messager.show({
					title: '提示',
					msg: result.data
				});
			}
		}, 'json');
	}
	//--------------------------------------------保存角色页面--------------------------------------------
	function saveRole() {
		this.disabled = true;
		if($('#systemCode_a').is(':checked') && $('#systemCode_w').is(':checked')){
			$('#systemCode').val(2);
	    }else if($('#systemCode_a').is(':checked')){	    		    
			$('#systemCode').val(0);
	    }else if($('#systemCode_w').is(':checked')){
	    	$('#systemCode').val(1);
	    }
	    else
	    	$('#systemCode').val("");
		$("#roleCode").removeAttr("onblur");
		$('#fm').form('submit', {
			url : '${ctx}/admin/role/save',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('('+result+')');
				if (result.status){
					$('#dlg').dialog('close');
					$.messager.show({
						title: '提示',
						msg: result.data
					});
					$('#tt').datagrid('reload');	
				} else {
					$.messager.show({
						title: '提示',
						msg: result.data
					});
				}
			}
		});
	}
	
	
	function msg(result){
		if (result.status){
			$('#dlg').dialog('close');		
			$('#tt').datagrid('reload');	
		} else {
			$.messager.show({
				title: '提示',
				msg: result.data
			});
		}
	}
	
	/** 操作 */
	function fmtOperation(val,row,index){
		return '<a href="#" onclick="addFunction('+index+')">授权</a>';		
	}
	
	//--------------------------------------------添加权限--------------------------------------------	
	function addFunction(index){
		$('#tt').datagrid('selectRow', index);	
		var row = $('#tt').datagrid('getSelected'); 
            
		if(row){
			createTabsInframePage('添加权限','${ctx}/admin/role/role_function?rolId='+row.id);
		} else{
			$.messager.alert('温馨提示','请选定一行记录再操作!','info');
			return ;
		}
	}
	
	function formatStatus(val, row) {
		var status = "";
		if(val == "1"){
			status = "启用";
		}else if(val == "0"){
			status = "禁用";
		}
		return status;
	}
	
</script>
<style type="text/css">
.datagrid-body {
	overflow-x:hidden;
	overflow-y:auto;
}
.datagrid-btn-separator {
    float: none;
}
</style>

</head>
<body>
	<table id="tt" class="easyui-datagrid" striped="true"  toolbar="#tb" rownumbers="true" pagination="true" 
	singleSelect="true" fitColumns="true" fit="true" style="overflow-x:hidden" pageSize="15"  pageList=[15,30,45,60] 
	 data-options="url :'${ctx}/admin/role/rolePage',
	 			view: myview,
		      emptyMsg: '暂无数据',
		onDblClickRow : function(rowIndex, rowData) {
			edit();
		},
		onSelect : function() {
			$('#editBt').linkbutton({
				disabled : false
			});
			$('#removeBt').linkbutton({
				disabled : false
			});
		}">
		<thead>
			<tr>
			    <th field="id" width="60" sortable="true" hidden="hidden">ID</th>
				<th field="roleCode" width="120" sortable="true" align="center">角色编号</th>
				<th field="roleName" width="120" sortable="true" align="center">角色名称</th>
				<th field="status" width="100" sortable="true" align="center" formatter="formatStatus">角色状态</th>
				<th field="meno" width="220" align="center">备注</th>
				<th field="operation" width="100" align="center" formatter="fmtOperation">功能权限</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style=" height: auto">
		<div style="padding: 5px; border-bottom:1px solid #f2f3f5">
		<span class="datagrid-btn-separator"></span>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="_new()">新建</a>
		<span class="datagrid-btn-separator"></span>	
			<a href="#" id="editBt" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="edit()">编辑</a> 
		<span class="datagrid-btn-separator"></span>	
			<!-- <a href="#" id="removeBt" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeRole()">删除</a>
		<span class="datagrid-btn-separator"></span> -->	
		</div>
		<div style="padding: 5px;">
		角色名称： <input id="roleName"> 
		角色编码： <input id="roleCode1"> 
		角色状态：
		<select id="status"  class="easyui-combobox" editable="false" style="width: 100px" data-options="panelHeight:'auto'">
			    <option value="">全部</option>   
		    	<option value="1">启用</option>   
		    	<option value="0">禁用</option> 		    			    			    	
			</select>
		<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="text-align:center;width:480px; " closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
		<table style="font-size:12px; width:100%; text-align:right;">
			<tr>
				<td></td>
				<td align="left"><input id="id" name="id" class="easyui-validatebox" hidden="hidden"></td>
			</tr>		
			<tr>
				<td><label>角色编号:</label></td>
				<td align="left"><input id="roleCode" name="roleCode" class="easyui-validatebox" required="true" maxlength="15" onblur="checkroleCode()"></td>	
			</tr>
			 <tr>
				<td><label>角色名称:</label></td>
				<td align="left"><input id="roleName" name="roleName" class="easyui-validatebox" required="true" maxlength="15"></td>							
			</tr>
			<!-- <tr>
				<td><label>分配权限:</label></td>
				<td align="left">
				     <input type="hidden"  id = "systemCode" name="systemCode">
					<input type="checkbox" id = "systemCode_w" name="systemCode_w">具有WEB权限
					<input type="checkbox" id = "systemCode_a" name="systemCode_a">具有APP权限
				</td>							
			</tr>	 -->		
			<tr>
				<td><label>角色描述:</label></td>
				<td colspan="4" align="left"><textarea name="meno" style="height:60px;" class="easyui-validatebox" ></textarea></td>
			</tr>
			<tr>
				<td><label>启用/禁用:</label></td>
				<td align="left">
				     <select id="status1" name="status" class="easyui-combobox" editable="false" style="width: 100px" data-options="panelHeight:'auto'">   
		    			<option value="1" selected="selected">启用</option>   
		    			<option value="0">禁用</option> 		    			    			    	
					</select>
				</td>							
			</tr>
		</table>
		</form>
	</div>
	<div id="dlg-buttons" style="text-align:center;width:380px; ">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRole()">提交</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>	
</body>
</html>