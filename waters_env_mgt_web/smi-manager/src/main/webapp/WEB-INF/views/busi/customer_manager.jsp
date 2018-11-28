<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<%@ include file="/static/common/doing.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(function(){
		$('#customerShip_list').datagrid({
			url: '${ctx}/admin/customer/getCustomerManager',
	        fit: true,
	        toolbar: "#tb",
	        rownumbers: true,
	        singleSelect: true,
	        showFooter: true,
	        pageSize:15,
	        pageList: [15,30, 45],
	        view: myview,
	        queryParams: {
	        },
	        emptyMsg: '暂无数据',
	        columns: [[
	            {field: 'id', width: '10', hidden: 'hidden', title: ''},
	            {field: 'cusId', width: '30', hidden: 'hidden', title: ''},
	            {field: 'shipId', width: '30', hidden: 'hidden', title: ''},
	           /*  {field: 'ck', width: '30',checkbox:true, formatter:controllIfSelected, title: '选择'}, */
	            {field: 'mmsiCode', width: '200', align: 'center', title: 'MMSI码'},
	            {field: 'userName', width: '200', align: 'center', title: '用户名'},
	            {field: 'sname', width: '200', align: 'center', title: '船名/单位'},
	            {field: 'address', width: '200', align: 'center', title: '单位地址'},
	            {field: 'snature', width: '200', align: 'center',title: '客户性质',
	            	formatter : function(value,rec) {
	            	var dataName='';
	            	$.ajax({
	          		  	type: 'POST',
	          		  	async:false,
	          		  	url: '${ctx}/admin/dictionary/findByDataTypeAndValue?dataType=CUS_NATURE&dataValue='+value,
	          		  	success: function(row){
	          		  		if(row && row.length > 0 ){
		          		  		dataName = row[0].dataName;        		  		
	          		  		} 	
	              		},
	          			dataType: 'json'
	          		}); 
	            	return dataName;
	            }},
	            {field: 'cusName', width: '160', align: 'center', title: '联络人'},
	            {field: 'phone', width: '160', align: 'center', title: '联络人电话'},
	            {field: 'status', width: '5', align: 'center',hidden: 'hidden', title: '状态'},
	            {field: 'option', width: '140', align: 'center', title: '操作',formatter:builderOperationLinks},
	        ]],
	        pagination: true
		});
		
	});
	$(function(){
		$('#oraderSelect').click(function(){
			
			var queryParams = $('#customerShip_list').datagrid('options').queryParams;
			queryParams.sName = $('#shipname').val();
			queryParams.sNature = $('#s_nature').combobox('getValue');
			$('#customerShip_list').datagrid('options').queryParams = queryParams;
			$('#customerShip_list').datagrid("options").url="${ctx}/admin/customer/getCustomerManager";
			$("#customerShip_list").datagrid('load');
		});
		$('#yes').bind("click",submitMes);
	})


	//构建操作链接
function builderOperationLinks(value,rec,index){
	/* var id = rec.id,links; */
	var applicationDesc = rec.applicationDesc,links;
		links = '<a href="#" id="down" onclick="lookDetail('+index+');">查看</a>'; 
	return links;
}
function lookDetail(index){
	 $('#customerShip_list').datagrid('selectRow',index);// 关键在这里  
	 var data = $('#customerShip_list').datagrid('getSelected');  
	if(data){
		$('#looktable').dialog({
			title: '详情',
			modal: true
		});
	$('#looktable').dialog('open');
	$('#input01').val(data.mmsiCode);
	$('#input02').val(data.sname);
	$('#input03').val(data.address);
	if(data.snature==1){
		$('#input04').val("邮轮");
	}else if(data.snature==2){
		$('#input04').val("货轮");
	}else{
		$('#input04').val("单位");
	}
	$('#input05').val(data.cusName);
	$('#input06').val(data.phone);
	}
}
	
/**
 * 记录勾选框
 */
var ids = [];
//列表勾选框事件
function controllIfSelected(value, rec) {
	return "<input type=\"checkbox\" onchange='opSelected(" + value
			+ ",this)' />";
}


	//修改事件
	$(function(){
		$('#updateTable').click(function(){
			var data = $('#customerShip_list').datagrid('getSelected');
			if(data){
				
			$('#dia').dialog('open');
			$('#id').val(data.id);
			$('#shipId').val(data.shipId);
			$('#mmsiCode').val(data.mmsiCode);
			$('#sName').val(data.sname);
			$('#address').val(data.address);
			$('#sNature').val(data.snature);
			$('#cusName').val(data.cusName);
			$('#phone').val(data.phone);
			$('#cusId').val(data.cusId);
			
			}else{
				$.messager.alert('温馨提示','请选择一条数据','info');


			}
		});
	})
	//重置密码事件
	$(function(){
		$('#restPass').click(function(){
			var data = $('#customerShip_list').datagrid('getSelected');
			if(data){
				$('#dll').dialog({
					title: '重置密码',
					modal: true
				});
				$('#dll').dialog('open');
				var id = data.id;
				var status = data.status;
				$('#shipUsername').val(data.userName);
				$('#shipName').val(data.sname);
				$('#restPaswd').val("888888");
				$('#customerID').val(data.id);
			}else{
				$.messager.alert('温馨提示','请选择一条数据','info');
			}
		});
	})
	
	$(function(){
		$('#sure').click(function(){
			
			$('#fmcancal').form('submit', {
			    url:'${ctx}/admin/customer/cancleCustomerShip',
			    onSubmit: function(){
			    	return $(this).form('validate');
			    },
			    success:function(result){
			    	var result = eval('('+result+')');
			    	if(result.status){
			    		$('#dll').dialog('close');
			    		$.messager.show({
							title: '提示',
							msg: result.data
						});
			    		$('#customerShip_list').datagrid('reload');
			    	}else{
			    		$.messager.show({
							title: '提示',
							msg: result.data
						});
			    	}
					
			    }
			});
		});
	});
	
	$(function(){
		
		$('#nosure').click(function(){
			$('#id').val('');
			$('#shipUsername').val('');
			$('#shipName').val('');
			$('#restPaswd').val('');
			$('#dll').dialog('close');
		});
	})
	
	//注销事件
	$(function(){
		$('#cancleShip').click(function(){
			var data = $('#customerShip_list').datagrid('getSelected');
			
			if(data){
				$.messager.confirm('确认','您确认想要注销记录吗？',function(r){    
				    if (r){    
				    	var cusId = data.cusId;
						var id = data.id;
						var shipId = data.shipId;
						var status = data.status;
						$.post('${ctx}/admin/customer/updateCustomerShip', {
							id : id,
							cusId : cusId,
							shipId:shipId,
							status : status
						}, function(result) {
							
							if (result.status){
								$('#customerShip_list').datagrid('reload');
								$.messager.show({
									title: '提示',
									msg: result.data
								});
							}else{
								$.messager.show({
									title: '提示',
									msg: result.data
								});
							}
						}, 'json');    
				    }    
				}); 
				
			}else{
				$.messager.alert('温馨提示','请选择一条数据','info');
			}
		});
	})
	
	//打开表单
$(function(){
	$('#creatTable').click(function(){
		$('#dia').dialog({
			title: '客户信息',
			modal: true
		});
		$('#dia').dialog('open');
	});
})
//提交新增表单
/* $(function(){
	$('#yes').click(function(){
		
	});
}) */
//关闭窗口
$(function(){
	$('#no').click(function(){
	
		$('#id').val('');
		$('#cusId').val('');
		$('#mmsiCode').val('');
		$('#sName').val('');
		$('#address').val('');
		$('#sNature').val('');
		$('#cusName').val('');
		$('#phone').val('');
		$('#dia').dialog('close');
	});
})

		
//提交新增船信息表单
/* $(function(){
	$('#yes').click(function(){
		$('#yes').unbind("click");
		$('#fm').form('submit', {
		    url:'${ctx}/admin/customerShip/creatCustomerShip',
		    onSubmit: function(){
		    	return $(this).form('validate');
		    },
		    success:function(result){
		    	var result = eval('('+result+')');
		    	if(result.status){
		    		$('#id').val('');
		    		$('#cusId').val('');
		    		$('#mmsiCode').val('');
		    		$('#sName').val('');
		    		$('#address').val('');
		    		$('#sNature').val('');
		    		$('#cusName').val('');
		    		$('#phone').val('');
		    		$('#dia').dialog('close');
		    		$.messager.show({
						title: '提示',
						msg: result.data
					});
		    		$('#customerShip_list').datagrid('reload');
		    	}else{
		    		$.messager.show({
						title: '提示',
						msg: result.data
					});
		    	}
				
		    }
		});
		setTimeout("$('#yes').bind('click',save);",2000)
	})
}) */
	
	function submitMes(){
		$('#yes').unbind("click");
		$('#fm').form('submit', {
		    url:'${ctx}/admin/customer/creatCustomerShip',
		    onSubmit: function(){
		    	return $(this).form('validate');
		    },
		    success:function(result){
		    	var result = eval('('+result+')');
		    	if(result.status){
		    		 $('#id').val('');
		    		$('#cusId').val('');
		    		$('#shipId').val('');
		    		$('#mmsiCode').val('');
		    		$('#sName').val('');
		    		$('#address').val('');
		    		$('#sNature').val('');
		    		$('#cusName').val('');
		    		$('#phone').val('');
		    		$('#dia').dialog('close'); 
		    		$.messager.show({
						title: '提示',
						msg: result.data
					});
		    		$('#customerShip_list').datagrid('reload');
		    	}else{
		    		$.messager.show({
						title: '提示',
						msg: result.data
					});
		    	}
				
		    }
		});
		setTimeout("$('#yes').bind('click',submitMes);",5000);
	}
	
	
	
	
	
	
	
	
	
	function lookclose(){
		$('#looktable').dialog('close');
	}
	function export_win(){
	    $('#export_param_form').form('submit', {
		    url:'${ctx}/admin/customerShip/customerShipXlsDownload',
		    onSubmit: function(){
		    },
		    success:function(data){
		    	/* var v = eval('(' + data + ')'); */
		    	if(data.code != '0000'){
		    		$.messager.show({
			             title:'提示', 
			             msg:data.msg
			        });
		    	}
		    }
		});
	}
</script>
<style type="text/css">
.datagrid-body {
	overflow-x:auto;
	overflow-y:auto;
}
.datagrid-btn-separator {
    float: none;
}
</style>
</head>

<body  data-options="region:'center'" id="main">

<div id="customer_list">
	<div id="tb" style="padding: 10px; width:auto; height: auto">
		<form id="export_param_form" method="get">
		<table>
		<tr>
		
			<td>船名/单位：<input id="shipname" name="sName"></input></td>
			<td>客户性质：<!-- <select id="s_nature" class="easyui-combobox" name="shiplist" >
				<option value="">全部</option>
			    <option value="001">邮轮</option>
			    <option value="002">货轮</option>
			    <option value="003">单位</option>
			</select> -->
				<select id="s_nature" name="sNature" class="easyui-combobox" editable="false" style="width: 150px"  data-options="panelHeight:'auto'">
                		<option value="">全部</option>
                		 <dictionary:options type="CUS_NATURE" status="1" />
            		</select>
			</td>
			<td>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="oraderSelect">查询</a>
			<span class="datagrid-btn-separator"></span>
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="exal" onclick="export_win();">导出</a>
			
		</tr>		
		<tr>
		
			
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="creatTable">新增</a>
			<span class="datagrid-btn-separator"></span>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateTable">修改</a>
			<span class="datagrid-btn-separator"></span>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="cancleShip">注销</a>
			<span class="datagrid-btn-separator"></span>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="restPass">重置密码</a>
		</tr>
		</table>
		</form>
	</div>
</div>
	<table id="customerShip_list"></table>
<div id="dia" title="客户信息" class="easyui-dialog" style="text-align:center;width:600px;heigth:auto; " closed="true" buttons="#dialog-button">
	<form id="fm" method="post" >
	<table style="font-size:12px; width:100%; text-align:right;">
			<tr >
				<td><lable>MMSI码:</lable></td>
				<td align="left"><input type="text"   id="mmsiCode" name="mmsiCode" required="true"/></td>
				<td><lable>船名/单位:</lable></td>
				<td align="left"><input type="text"  class="easyui-validatebox" id="sName" name="sName" required="true"/></td>
			</tr>
			<tr >
				<td><lable>单位地址:</lable></td>
				<td align="left"><input type="text"   id="address" name="address" required="true"/></td>
				<td><lable>客户性质:</lable></td>
				<td align="left"><!-- <select id="sNature" class="easyui-combobox" name="sNature" >
					    <option value="001">邮轮</option>
					    <option value="002">货轮</option>
					    <option value="003">单位</option>
					</select> -->
					<select id="sNature" name="sNature" class="easyui-combobox"  editable="false" style="width: 150px"  data-options="panelHeight:'auto'">
                		 
                		 <dictionary:options type="CUS_NATURE" status="1" />
            		</select>
				</td>
			</tr>
			<tr>
				<td><lable>联络人:</lable></td>
				<td align="left"><input name="cusName" id="cusName" type="text" class="easyui-validatebox" required="true"  /></td>
				<td><lable>联络人电话:</lable></td>
				<td align="left"><input name="phone" id="phone" class="easyui-validatebox" type="text"  required="true"/></td>
				 <td><input type="text"  id="id" name="id" hidden="true" required="true"/></td>
				 <td><input type="text"  id="shipId" name="shipId" hidden="true" required="true"/></td>
				 <td><input type="text"  id="cusId" name="cusId" hidden="true" required="true"/></td>
			</tr>
           	   
			</table>
		</form>
</div>
		<div style="text-align:center;width:600px; " id="dialog-button" class="dialog-button">
			<td>&nbsp<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="yes" >确认</a>&nbsp&nbsp&nbsp&nbsp&nbsp
        	   <a href="#"class="easyui-linkbutton" id="no" iconCls="icon-cancel" >取消</a></td>
        	  
		</div>
<div id="dll" title="重置密码" class="easyui-dialog" style="text-align:center;width:300px;heigth:auto; " closed="true" buttons="#dll-button">
	<form id="fmcancal" method="post">
		<table style=" width:100%; text-align:center;">
			<tr>
			<td ><lable>用户名：</lable></td>
			<td align="left"><input type="text" readonly="true" class="easyui-validatebox" id="shipUsername" name="userName" required="true"/></td>
			</tr>
			<tr>
			<td><lable>船名/单位：</lable></td>
			<td align="left"><input type="text" readonly="true" class="easyui-validatebox" id="shipName" name="sName" required="true"/></td>
			</tr>
			<tr>
			<td><lable>密码重置为：</lable></td>
			<td align="left"><input type="text" readonly="true" class="easyui-validatebox" id="restPaswd" name="password" required="true"/></td>
			<td><input type="text"  id="customerID" name="id" hidden="true" required="true"/></td>
			</tr>
			
			
		</table>
	</form>
</div>
<div style="text-align:center;width:200px; " id="dll-button" class="dll-button">
	<tr>
	<td><a href="#" class="easyui-linkbutton"  id="sure" iconCls="icon-ok" value="确认">确认</a>
        	   <a href="#"class="easyui-linkbutton" id="nosure" iconCls="icon-cancel" value="取消">取消</a></td>
        	   
        	 </tr>
</div>
	<div id="looktable" title="详情" class="easyui-dialog" style="text-align:center;width:300px;heigth:auto; " closed="true" buttons="#looktable-buttons">
	<form id="fwm">
		<table style="font-size:12px; width:100%; text-align:center;">
			<tr >
			<td><lable>MMSI码:</lable></td>
			<td align="left"><input type="text" id="input01" readonly="true"style="text-align:center;width:150px;heigth:600px;"></td>
			</tr>
			<tr>
			<td><lable>船名/单位:</lable></td>
			<td align="left"><input type="text" id="input02" readonly="true"style="text-align:center;width:150px;heigth:600px;"></td>
			</tr>
			<tr>
			<td><lable>单位地址:</lable></td>
			<td align="left"><input type="text" id="input03" readonly="true"style="text-align:center;width:150px;heigth:600px;"></td>
			</tr>
			<tr>
			<td><lable>客户性质:</lable></td>
			<td align="left"><input type="text" id="input04" readonly="true"style="text-align:center;width:150px;heigth:600px;"></td>
			</tr>
			<tr>
			<td><lable>联络人:</lable></td>
			<td align="left"><input type="text" id="input05" readonly="true"style="text-align:center;width:150px;heigth:600px;"></td>
			</tr>
			<tr>
			<td><lable>联络人电话:</lable></td>
			<td align="left"><input type="text" id="input06" readonly="true"style="text-align:center;width:150px;heigth:600px;"></td>
			</tr>
		</table>
	</form>
</div>
	<div style="text-align:center;width:260px; " id="looktable-buttons" class="dialog-button">
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="lookclose();">关闭</a>
	</div>
</body>
</html>
