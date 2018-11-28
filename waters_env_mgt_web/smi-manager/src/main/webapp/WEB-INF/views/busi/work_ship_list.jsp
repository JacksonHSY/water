<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<script type="text/javascript">
$(function() {

    $('#work_ship_grid').datagrid({
        url: '${ctx}/admin/workShip/getWorkShipPage',
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
            {field: 'id', width: '60', hidden: 'hidden', title: ''},
            {field: 'name', width: '200', align: 'center', title: '船名'},
            {field: 'shipNature', width: '140', align: 'center', title: '船舶性质',formatter:formatNature},
            {field: 'captain', width: '140', align: 'center', title: '船长'},
            {field: '_operate', width: '100', align: 'center', title: '操作',formatter:formatOper},
        ]],
        pagination: true
    });
	
	$('#searchBt').click(function() {
	    var queryParams = $('#work_ship_grid').datagrid('options').queryParams;
	    queryParams={};
	    var name = $('#ship_name').val();
	    if(null != name && "" !=name){
		    queryParams.name=name; 
	    }
		/* var captain = $('#captain_box').combobox('getValue');
		if(null != captain && "" !=captain){
		    queryParams.captain=captain; 
	    } */
		$('#work_ship_grid').datagrid('options').queryParams = queryParams;
		$('#work_ship_grid').datagrid("options").url="${ctx}/admin/workShip/getWorkShipPage";
		$("#work_ship_grid").datagrid('load');
	});
	
	$('#clearBt').click(function() {
	    $('#ship_name').val("");
	    $('#captain_box').combobox('clear');
	});
	
	$("#saveBtn").bind("click",save);
	
});

function save(){
	$("#saveBtn").unbind();
	$('#save_form').form('submit', {
	    url:'${ctx}/admin/workShip/saveOrUpdate',
	    onSubmit:function(){
	    	var isValid = $(this).form('validate');
	    	if (!isValid){
	    		$.messager.progress('close');	// hide progress bar while the form is invalid
	    	}
	    	return isValid;
		},
	    success:function(data){
	    	var v = eval('(' + data + ')');
	    	if(v.code == '0000'){
	    		$('#save_ship_dlg').window('close');
	    		$("#work_ship_grid").datagrid('reload');
	    	}else{
		    	$.messager.show({
		             title:'提示', 
		             msg:v.msg
		        });
	    	}
	    }
	});
	setTimeout("$('#saveBtn').bind('click',save);",2000)
} 

function formatOper(val,row,index){  
    return '<a href="#" onclick="getInfo('+index+')">查看</a>';  
}  

function formatNature(value, row, index){  
    if(row.shipNature=='1'){  
        return "多功能";  
    }else if(row.shipNature=='2'){  
        return "油污";  
    }else if(row.shipNature=='3'){
    	return "普通";
    }  
}  

function getInfo(index){  
    $('#work_ship_grid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#work_ship_grid').datagrid('getSelected');  
    if (row){
    	$('#show_ship_dlg').dialog({title:'信息'});
        $('#show_ship_dlg').window('open');
        $('#show_form').form('load', row);
    } else {
        $.messager.show({
            title:'提示', 
            msg:'未选中行!'
        });
    }
}  

//弹出窗口
function save_win(result){
    $('#save_ship_dlg').dialog({title:'新增'});
    $('#dlg-buttons').css("display",'block');
    $('#save_ship_dlg').window('open');
    $('#save_form').form('clear');
}

function edit_win(result){
	var row = $('#work_ship_grid').datagrid('getSelected');
    if (row){
    	$('#save_ship_dlg').dialog({title:'修改'});
    	$('#dlg-buttons').css("display",'block');
    	$("#shipNature option[value='"+row.shipNature+"']").attr("selected","selected");
        $('#save_ship_dlg').window('open');
        $('#save_form').form('clear');
        $('#save_form').form('load', row);
    } else {
        $.messager.show({
            title:'提示', 
            msg:'未选中行!'
        });
    }
}

function cancel_win(result){
    var row = $('#work_ship_grid').datagrid('getSelected');
    if (row){
    	$.messager.confirm('确认', '您确定要注销该作业船吗?', function(r) {
			if (r) {
				$('#save_form').form('clear');
				$('#save_form').form('load', row);
		    	$('#save_form').form('submit', {
				    url:'${ctx}/admin/workShip/cancel',
				    onSubmit: function(){
				    },
				    success:function(data){
				    	var v = eval('(' + data + ')');
				    	if(v.code == '0000'){
				    		$.messager.show({
		    		             title:'提示', 
		    		             msg:'注销成功'
		    		        });
				    		$("#work_ship_grid").datagrid('reload'); 
				    		$('#cancel_dialog').dialog('close');
				    		
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
}

function export_win(){
    $('#export_param_form').form('submit', {
	    url:'${ctx}/admin/workShip/workShipXlsDownload',
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
<body data-options="region:'center'" id="main" >
	<div id="tb" style="padding: 5px; height: auto">
		<div>
		<form id="export_param_form" method="get">
		<table width="50%" style="margin-left: 20px;" border="0">
			<tr>
				<td>
					船名：
					<input id="ship_name" name="name" placeholder="请输入查询内容">
				</td>
				<%-- <td>
					船长：
					<input id="captain_box" class="easyui-combobox" name="captain" placeholder="请选择船长" editable="false" 
    					data-options="valueField:'captain',textField:'captain',editable:'false',url:'${ctx}/admin/workShip/getCaptainGroupList'">
				</td> --%>
			</tr>
			<tr><td colspan="4">
			<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<!-- <a href="#" id="clearBt" class="easyui-linkbutton" iconCls="icon-search">重置</a> -->
			<a href="#"  class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="export_win();">导出</a>
			<a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="save_win();">新增</a>
			<a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_win();">修改</a>
			<a href="#"  class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancel_win();">注销</a>
			</td></tr>
			</table>
		    <div style="text-align:left; width:100%; float:left;padding:0px 0px;"><span id="msg" style=" padding:0px 0px; margin-left:65px; color:#2c759a;font-weight:bold;"></span></div>
			</form>
		</div>
	</div>
	<table id="work_ship_grid"></table>
	<!-- 授权信息校验对话框 -->
	<div id="save_ship_dlg" class="easyui-dialog" style="text-align:center;width:400px; " title="新增作业船信息" closed="true" buttons="#dlg-buttons">
		<form id="save_form" method="post" class="easyui-form" data-options="novalidate:true">
			<input type="text" hidden="true" name="id"/>
			<div style="padding-top:25px;padding-bottom:15px;">
				船名：<input type="text"  class="easyui-validatebox"  name="name" required="true"/>
			</div>
			<div style="padding-top:15px;padding-bottom:15px;">
				<div style="float:left;margin-left:65px">船舶性质：</div>
				<select name="shipNature" id="shipNature" style="width:173px;margin-left:-89px;" class="easyui-validatebox" editable="false"  required="true">
	                <dictionary:options type="WS_TYPE" status="1" selectedValue="1"/>
            	</select>
				<!-- <input type="WS_TYPE" status="1"  name="shipNature" class="easyui-validatebox" type="text" style="margin-left:-86px;"  required="true"/> -->
			</div>
			<div style="padding-top:15px;padding-bottom:30px;">
				船长：&nbsp;<input name="captain"  class="easyui-validatebox" type="text"  />
			</div>
		</form>
	</div>
	<div id="dlg-buttons"  style="text-align:center;width:400px; ">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id="saveBtn">确认</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#save_ship_dlg').dialog('close')">取消</a>
	</div>
	<div id="show_ship_dlg" class="easyui-dialog" style="text-align:center;width:400px; " title="查看作业船信息" closed="true">
		<form id="show_form" method="post" class="easyui-form" data-options="novalidate:true">
			<input type="text" hidden="true" name="id"/>
			<div style="padding-top:25px;padding-bottom:15px;">
				船名：<input type="text"  class="easyui-validatebox"  name="name" required="true" disabled="false"/>
			</div>
			<div style="padding-top:15px;padding-bottom:15px;">
				<div style="float:left;margin-left:65px">船舶性质：</div>
				<select name="shipNature" id="shipNature" disabled="false" style="width:173px;margin-left:-89px;" class="easyui-validatebox" editable="false"  required="true">
	                <dictionary:options type="WS_TYPE" status="1" selectedValue="1"/>
            	</select>
				<!-- <input type="WS_TYPE" status="1"  name="shipNature" class="easyui-validatebox" type="text" style="margin-left:-86px;"  required="true"/> -->
			</div>
			<div style="padding-top:15px;padding-bottom:30px;" >
				船长：&nbsp;<input name="captain"  class="easyui-validatebox" type="text" disabled="false" />
			</div>
		</form>
	</div>
</body>

