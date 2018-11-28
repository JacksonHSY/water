<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>

<script type="text/javascript">
var roleList;
postAjax(false, '${ctx}/admin/role/getRoleList',function success(data){roleList = data;}, 'json');
$(function() {
    $('#adminUser_List').datagrid({
        url: '${ctx}/admin/adminUser/list',
        fit: true,
        toolbar: "#tb",
        rownumbers: true,
        singleSelect: true,
        showFooter: true,
        pageSize:15,
        pageList: [15,30, 45, 60],
        view: myview,
       queryParams: {
            
        }, 
        emptyMsg: '暂无数据',
        columns: [[
            {field: 'id', width: '60', title: '',hidden: 'hidden'},
            {field: 'jno', width: '200', align: 'center', title: '工号'},
            {field: 'name', width: '240', align: 'center', title: '姓名'},
            {field: 'etype', width: '240', align: 'center', title: '员工类型',
            	formatter : function(value,rec) {
            	var dataName='';
            	$.ajax({
          		  	type: 'POST',
          		  	async:false,
          		  	url: '${ctx}/admin/dictionary/findByDataTypeAndValue?dataType=STAFF_TYPE&dataValue='+value,
          		  	success: function(row){
          		  		if(row && row.length > 0 ){
	          		  		dataName = row[0].dataName;        		  		
          		  		} 	
              		},
          			dataType: 'json'
          		}); 
            	return dataName;
            }},
            {field: 'phone', width: '240', align: 'center', title: '手机号码'},
            {field: 'status', width: '140', align: 'center', title: '状态',formatter: formatStatus},
            {field: 'roleName', width: '240', align: 'center', title: '角色'},
            {field: 'rolId', hidden: 'hidden'},
            {field: 'memo', width: '340', align: 'center', title: '备注'},
        ]],
        pagination: true
    });	
	
	$('#adminUser_SearchBt').click(function() {
	    var queryParams = $('#adminUser_List').datagrid('options').queryParams;
	    queryParams={};

		queryParams.jno= $('#jno').val();
		queryParams.name= $('#name').val();
		queryParams.status = $('#status').combobox('getValue'); 
		
		$('#adminUser_List').datagrid('options').queryParams = queryParams;
		$('#adminUser_List').datagrid("options").url="${ctx}/admin/adminUser/list";
		$("#adminUser_List").datagrid('load');
	});
	
	//密码重置
	$('#restPass').click(function(){
		var row = $('#adminUser_List').datagrid('getSelected');
		if (row){
	    	$.messager.confirm('确认', '您确定要重置密码吗?', function(r) {
				if (r) {
					$.ajax({ 
						url: "${ctx}/admin/adminUser/resetPwd",
						data: {id:row.id},
						success: function(v){
							    	if(v.code == '0000'){
							    		$.messager.show({
					    		             title:'提示', 
					    		             msg:'重置成功,密码为"123456"'
					    		        });
							    		$("#adminUser_List").datagrid('reload'); 
							    	}else{
					    		    	$.messager.show({
					    		             title:'提示', 
					    		             msg:v.msg
					    		        });
							    	}
							     }
					});
					
				}
			});
	    } else {
	        $.messager.show({
	            title:'提示', 
	            msg:'未选中行!'
	        });
	    }
		
	});
});
 

/* function formatterdate(val, row) {
	if (val != null && val != "") {
		var date = new Date(val);
		var dateStr = date.format("yyyy-MM-dd");
		return dateStr
		//return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	} else {
		return "";
	}
} */
//新建用户
function _new() {
	$('#dlg').dialog({
		title: '新建用户',
		modal: true
	});
	$('#dlg').dialog('open');
	$('#fm').form('clear');
	$("#jno1").removeAttr("disabled");
	$("#status1").combobox({disabled:true});  
	$('#status1').combobox('setValue','1');
	url = '${ctx}/admin/adminUser/save';
}


//--------------------------------------------光标移开校验角色编号唯一性--------------------------------------------
function checkJno(){
	var jno =$("#jno1").val();
	if(jno==""){
		return;
	}
	$.post('${ctx}/admin/adminUser/checkJno', {
		jno : jno
	}, function(result) {
		if (!result.status){
			$.messager.show({
				title: '提示',
				msg: result.data
			});
		}
	}, 'json');
}

//--------------------------------------------光标移开校验角色姓名唯一性--------------------------------------------
/* function checkName(){
	var name =$("#name1").val();
	if(name==""){
		return;
	}
	$.post('${ctx}/admin/adminUser/checkName', {
		name : name
	}, function(result) {
		if (!result.status){
			$.messager.show({
				title: '提示',
				msg: result.data
			});
		}
	}, 'json');
} */

//--------------------------------------------编辑角色页面--------------------------------------------
function edit() {
	$("#jno1").removeAttr("onblur"); 
	var row = $('#adminUser_List').datagrid('getSelected');
	if (row) {
		$('#fm').form('load', row);
		$('#dlg').dialog({
			title: '编辑角色',
			modal: true
		});
		$('#dlg').dialog('open');
		/* 	if(row.systemCode=='0'||row.systemCode=='2'){
			$('#systemCode_a').attr('checked','checked');				
		}
		if(row.systemCode=='1'||row.systemCode=='2'){
			$('#systemCode_w').attr('checked','checked');				
		}*/
		$("#jno1").attr("disabled","disabled"); 
		$("#status1").combobox({disabled:false}); 
		url = '${ctx}/admin/adminUser/save?id=' + row.id;
	}else{
		$.messager.alert('温馨提示','请选定一行记录再操作!','info');
		return ;
	}
}

//--------------------------------------------保存角色页面--------------------------------------------
function saveAdminUser() {
	debugger
	this.disabled = true;
	var phone =  $.trim($('#phone1').val());
	var eType = $('#eType1').combobox('getValue');
	$("#jno1").removeAttr("onblur");
	$('#fm').form('submit', {
		url : '${ctx}/admin/adminUser/save',
		onSubmit : function() {
			if( !phone ==''&&!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
				$.messager.alert('温馨提示','请输入正确的手机号!','info');
				return false;
			}
			if( eType == '2'&&phone ==''){
				$.messager.alert('温馨提示','联络员的手机号为必填!','info');
				return false;
			}
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
				$('#adminUser_List').datagrid('reload');	
			} else {
				$.messager.show({
					title: '提示',
					msg: result.data
				});
			}
		}
	});
}


/*  //构建操作链接
function builderOperationLinks(value,rec,index){
	alert(rec);
	var id = rec.id,links;
	links = '<a href="#" onclick="showEditDia('+index+');">查看/修改</a>';
	return links;
}   */
 
/* function showEditDia(index){
	$('#adminUser_List').datagrid('selectRow', index);	
	var row = $('#adminUser_List').datagrid('getSelected'); 
        
	if(row){
		createTabsInframePage('添加权限','${ctx}/admin/adminUser/adminUser_role?adminUserId='+row.id);
	} else{
		$.messager.alert('温馨提示','请选定一行记录再操作!','info');
		return ;
	}
} */

function formatEType(val,row){
	var eType = "";
	if(val == "1"){
		eType = "正式员工";
	}else if(val == "2"){
		eType = "其它";
	}
	return eType;
}

function formatStatus(val,row){
	var status = "";
	if(val == "1"){
		status = "启用";
	}else if(val == "0"){
		status = "禁用";
	}
	return status;
}




</script>

<body data-options="region:'center'" id="main" width="100%">
	<div id="tb" style="padding: 5px; height: auto">
		
		<div style="padding: 5px; border-bottom:1px solid #f2f3f5">
		<span class="datagrid-btn-separator"></span>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="_new()">新建</a>
		<span class="datagrid-btn-separator"></span>
			<a href="#" id="editBt" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="edit()">编辑</a>
		<span class="datagrid-btn-separator"></span>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="restPass">重置密码</a>
			<!-- <a href="#" id="removeBt" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeRole()">删除</a>
		<span class="datagrid-btn-separator"></span> -->	
		</div>
		<div>
			<form id="queryAdminUser" method="post">
				<table border="0" width="100%" style="margin-left: 20px;">
					<tr>
						<td>
							工号：<input id="jno" name="jno"/> 
						</td>
						<td>
							姓名：<input id="name" name = "name"/>						
						</td>
						<td>
							状态： <select id="status" class="easyui-combobox" editable="false" style="width: 100px"  data-options="panelHeight:'auto'">
							<option value="">全部</option>
							<option value="1">启用</option>
							<option value="0">禁用</option>
							</select>
						</td>
						<td>
							<a href="#" id="adminUser_SearchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<table id="adminUser_List"></table>
	
	<div id="dlg" class="easyui-dialog" style="text-align:center;width:480px; " closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
		<table style="font-size:12px; width:100%; text-align:right;">
			<tr>
				<td></td>
				<td align="left"><input id="id" name="id" class="easyui-validatebox" hidden="hidden"></td>
			</tr>		
			<tr>
				<td><label>工&nbsp;&nbsp;号：</label></td>
				<td align="left"><input id="jno1" name="jno" class="easyui-validatebox" required="true" maxlength="15" onblur="checkJno()"></td>	
			</tr>
			 <tr>
				<td><label>名&nbsp;&nbsp;称：</label></td>
				<td align="left"><input id="name1" name="name" class="easyui-validatebox" required="true" maxlength="15"></td>							
			</tr>	
			<tr>
				<td><label>员工类型：</label></td>
				<td align="left">
					<select id="eType1" name="etype" class="easyui-combobox" editable="false" style="width: 150px"  data-options="panelHeight:'auto'">
                		 <dictionary:options type="STAFF_TYPE" status="1" />
            		</select>					
				</td>
			</tr>	
			<tr>
				<td><label>手机号码：</label></td>
				<td align="left"><input id="phone1" name="phone" class="easyui-validatebox"  maxlength="15"></td>							
			</tr>
			<tr>
				<td><label>状&nbsp;&nbsp;态：</label></td>
				<td align="left">
				     <select id="status1" name="status" class="easyui-combobox" editable="false" style="width: 100px" data-options="panelHeight:'auto'">   
		    			<option value="1">启用</option>   
		    			<option value="0">禁用</option> 		    			    			    	
					</select>
				</td>							
			</tr>
			<tr>
				<td><label>角&nbsp;&nbsp;色：</label></td>
				<td align="left">
					<input class="easyui-combobox" id="roleComb" name="rolId" data-options="valueField:'id',textField:'roleName',data : roleList"/>
				</td>							
			</tr>
			
			<tr>
				<td><label>备&nbsp;&nbsp;注：</label></td>
				<td colspan="4" align="left"><textarea name="memo" style="height:60px;" class="easyui-validatebox" ></textarea></td>
			</tr>
		</table>
		</form>
	</div>
	<div id="dlg-buttons" style="text-align:center;width:380px; ">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAdminUser()">提交</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	
</body>

