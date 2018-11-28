<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<html>
<head>
<title>任务列表</title>
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
	//--------------------------------------------初始化页面--------------------------------------------
 	$(function() {
  		$('#searchBt').click(function() {
			var queryParams = $('#tt').datagrid('options').queryParams;
		    queryParams.taskStatus = $('#taskStatus_s').combobox('getValue'); //设置值
			queryParams.taskType = $('#taskType_s').combobox('getValue'); //设置值
			queryParams.worId = $('#worId_s').combobox('getValue'); //设置值
			queryParams.customerShipId = $('#customerShipId_s').combobox('getValue'); //设置值
			queryParams.tollCollector = $('#tollCollector_s').combobox('getValue'); //设置值 */
			$('#tt').datagrid('options').queryParams = queryParams;
			$("#tt").datagrid('load');
		});
  		$('#clearQry').click(function() {
  			$('#queryForm').form('clear');
		});
  		$("#chargeType").combobox({
  			onChange: function (n,o) {
  				if(n=='收费'){
  					$("#chargeAmount").removeAttr("disabled");
  				}else{
  					$("#chargeAmount").attr("disabled","disabled");
  				}
  			}
  		});
	}); 
	

	$(function(){
		  $('#fm input').each(function () {
	            if ($(this).attr('required') || $(this).attr('validtype'))
	                $(this).validatebox();
	        })
	});
	//--------------------------------------------收费方式金额联动--------------------------------------------
	//--------------------------------------------格式化日期字段--------------------------------------------
	function formatterdate(val, row) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
	//--------------------------------------------新建角色页面--------------------------------------------	
	function _new() {
		$('#create_dlg').dialog({
			title: '创建任务',
			modal: true
		});
		$('#create_dlg').dialog('open');
		$('#fm').form('clear');
	}

	//--------------------------------------------分派页面--------------------------------------------
	function feipai() {
		var row = $('#tt').datagrid('getSelected');		
		if (row) {
			if(row.taskStatus!='0'){
				$.messager.alert('温馨提示','请选择待分派的任务!','info');
				return ;
			}
			$('#fm_fp').form('load', row);
			$('#fenpai_dlg').dialog({
				title: '任务分派',
				modal: true
			});
			 $('#fenpai_dlg').dialog('open');
			 if(row.taskType==0){
					$("#taskType_fp").val("废弃物接收");
			 }else if(row.taskType==1){
				 $("#taskType_fp").val("水面保洁");
			 }else if(row.taskType==2){
				 $("#taskType_fp").val("河滩保洁");
			 }else{
				 $("#taskType_fp").val("紧急情况处理");
			 }
		}else{
			$.messager.alert('温馨提示','请选定一行记录再操作!','info');
			return ;
		}
	}
	//--------------------------------------------改派页面--------------------------------------------
	function gaipai() {
		var row = $('#tt').datagrid('getSelected');		
		if (row) {
			if(row.taskStatus!='1'){
				$.messager.alert('温馨提示','只能改派待处理的任务!','info');
				return ;
			}
			$('#fm_gp').form('load', row);
			$('#gaipai_dlg').dialog({
				title: '任务改派',
				modal: true
			});
			 $('#gaipai_dlg').dialog('open');
			 if(row.taskType==0){
					$("#taskType_gp").val("废弃物接收");
			 }else if(row.taskType==1){
				 $("#taskType_gp").val("水面保洁");
			 }else if(row.taskType==2){
				 $("#taskType_gp").val("河滩保洁");
			 }else{
				 $("#taskType_gp").val("紧急情况处理");
			 }
		}else{
			$.messager.alert('温馨提示','请选定一行记录再操作!','info');
			return ;
		}
	}
	//--------------------------------------------取消任务--------------------------------------------
	function cancelOrder() {
		var row = $('#tt').datagrid('getSelected');
		if (row) {
			if(row.taskStatus=='0' || row.taskStatus=='1'){
				$.messager.confirm('确认', '确定要取消该任务吗?', function(r) {
					if (r) {
						$.post('${ctx}/admin/orders/cancelOrder', {
							id : row.id
						}, function(result) {
							$('#tt').datagrid('reload'); 
							msg(result);
						}, 'json');
					}
				});
			}else{
				$.messager.alert('温馨提示','只能取消待分派或待处理的任务!','info');
				return ;
			}
		}else{
			$.messager.alert('温馨提示','请选定一行记录再操作!','info');
			return ;
		}
	}
	//--------------------------------------------保存任务页面--------------------------------------------
	function saveOrder() {
		var workDate = $('#workDate').combobox('getValue');
		this.disabled = true;
		$('#fm').form('submit', {
			url : '${ctx}/admin/orders/saveOrder',
			onSubmit : function() {
				if( workDate ==''){
					$.messager.alert('温馨提示','作业日期为必填!','info');
					return false;
				}
				if(!$('#yes').is(':checked') && !$('#no').is(':checked')){
					$.messager.alert('温馨提示','是否已分类为必填!','info');
					return false;
				}
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('('+result+')');
				if (result.status){
					$('#create_dlg').dialog('close');
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
	//保存分派任务
	function FpsaveOrder() {
		var worIdfp = $('#worId_fp').combobox('getValue');
		var tollCollectorfp = $('#tollCollector_fp').combobox('getValue');
		var fp_address = $('#fp_address').val();
		this.disabled = true;
		$('#fm_fp').form('submit', {
			url : '${ctx}/admin/orders/saveFpOrder',
			onSubmit : function() {
				if( worIdfp ==''){
					$.messager.alert('温馨提示','作业船为必填!','info');
					return false;
				}
				if(tollCollectorfp==''){
					$.messager.alert('温馨提示','联络人为必填!','info');
					return false;
				}
				if( fp_address ==''){
					$.messager.alert('温馨提示','地点为必填!','info');
					return false;
				}
			},
			success : function(result) {
				var result = eval('('+result+')');
				if (result.status){
					$('#fenpai_dlg').dialog('close');
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
	//保存改派任务
	function GpsaveOrder() {
		var worIdfp = $('#worId_gp').combobox('getValue');
		var tollCollectorfp = $('#tollCollector_gp').combobox('getValue');
		var gp_address = $('#gp_address').val();
		this.disabled = true;
		$('#fm_gp').form('submit', {
			url : '${ctx}/admin/orders/saveGpOrder',
			onSubmit : function() {
				if( worIdfp ==''){
					$.messager.alert('温馨提示','现作业船为必填!','info');
					return false;
				}
				if(tollCollectorfp==''){
					$.messager.alert('温馨提示','联络人为必填!','info');
					return false;
				}
				if( gp_address ==''){
					$.messager.alert('温馨提示','地点为必填!','info');
					return false;
				}
			},
			success : function(result) {
				var result = eval('('+result+')');
				if (result.status){
					$('#gaipai_dlg').dialog('close');
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
			$('#tt').datagrid('reload');	
		} else {
			$.messager.show({
				title: '提示',
				msg: result.data
			});
		}
	}
	/*数值计算*/
	  $(function(){
        $(".easyui-numberbox").blur(function(event) {
            var sum = 0;
            $(".easyui-numberbox").each(function() {
           		 v = parseFloat($(this).val());
                    v = isNaN(v)?0:v;
                    sum += v;
            });
            $(".sum").val(sum);
        });
    })
    //格式化
    function orderStatus(val, row) {
		var flowStatus = "";
		if(val == "0"){
			flowStatus = "待分派";
		}else if(val == "1"){
			flowStatus = "待处理";
		}else if(val == "2"){
			flowStatus = "处理中";
		}else if(val == "3"){
			flowStatus = "已取消";
		}else if(val == "4"){
			flowStatus = "已处理";
		}else{
			flowStatus = "已评价";
		}
		return flowStatus;
	}
	function sortStatus(val, row) {
			var flowStatus = "";
			if(val == "0"){
				flowStatus = "已分类";
			}else{
				flowStatus = "未分类";
			}
			return flowStatus;
	 }
	function orderType(val, row) {
		var flowStatus = "";
		if(val == "0"){
			flowStatus = "废弃物接收";
		}else if(val == "1"){
			flowStatus = "水面保洁";
		}else if(val == "2"){
			flowStatus = "河滩保洁";
		}else{
			flowStatus = "紧急情况处理";
		}
		return flowStatus;
 	}
	
	function isContains(str, substr) {
	     return new RegExp(substr).test(str);
	 }
	function showFormatter(val, row) {
		if(val!=''){
			if(!isContains(val, "/")){
				return "--/"+val;
			}else{
				return val;
			}
		}else{
			return "--/--";
		}
		
 	}
	//扩展easyui表单的验证  
	$.extend($.fn.validatebox.defaults.rules, {  
		  intOrFloat: {// 验证整数或小数
              validator: function (value) {
                  return /^\d+(\.\d+)?$/i.test(value);
              },
              message: '请输入数字，并确保格式正确'
          }
	})
	function export_win(){
    $("#queryForm").form('submit', {
	    url:'${ctx}/admin/orders/orderXlsDownload',
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
	function orderCancel(){
		$('#create_dlg').dialog('close');
	}
	function fenpCancel(){
		$('#fenpai_dlg').dialog('close');
	
	}
	function gaipCancel(){
		$('#gaipai_dlg').dialog('close');
	}
</script>
<style type="text/css">
.datagrid-body {
	overflow-x:scroll;
	overflow-y:scroll;
}
.datagrid-btn-separator {
    float: none;
}
td {
    font-size:12px
}
</style>
</head>
<body>
	<table id="tt" class="easyui-datagrid" striped="true"  toolbar="#tb" rownumbers="true" pagination="true" fitColumns="false" singleSelect="true"  fit="true" 
	pageSize="15"  pageList=[15,30,45,60] 
	 data-options="url :'${ctx}/admin/orders/orderList',
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
				<th field="taskCode" width ="150" align="center">任务编号</th>
				<th field="taskStatus"  width ="50" formatter="orderStatus" align="center">任务状态</th>
				<th field="taskType"  width ="80" formatter="orderType" align="center">任务类型</th>
				<th field="workShipName"  width ="100" align="center">作业船</th>
				<th field="customerShipName"  width ="100" align="center">客户船名/单位</th>
				<th field="adress"  width ="80" align="center">地点</th>
				<th field="ifType"  width ="50" align="center" formatter="sortStatus">是否已分类</th>
				<th field="charge"  width ="50" align="center">收费金额/方式</th>
				<th field="amountGarbageForShow"  width ="80" align="center">垃圾量合计</th>
				<th field="flife"  width ="120" align="center" formatter="showFormatter">生活垃圾预计/实际</th>
				<th field="fsweeping"  width ="120" align="center" formatter="showFormatter">扫舱垃圾预计/实际</th>
				<th field="ffood"  width ="120" align="center" formatter="showFormatter">食品废弃物预计/实际</th>
				<th field="fburnCinder"  width ="120" align="center" formatter="showFormatter">焚烧炉灰渣预计/实际</th>
				<th field="fplastic"  width ="120" align="center" formatter="showFormatter">塑料预计/实际</th>
				<th field="fwater"  width ="120" align="center" formatter="showFormatter">生活污水预计/实际</th>
				<th field="fgungo"  width ="120" align="center" formatter="showFormatter">油污预计/实际</th>
				<th field="workDateStr"  width ="150" align="center">作业时间</th>
				<th field="reson"  width ="80" align="center">拒绝原因</th>
				<th field="tollCollectorName"  width ="70" align="center">联络员</th>
				<th field="memo"  width ="70" align="center">备注</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style=" height: auto">	<div style="padding: 2px;" border-bottom:1px solid #f2f3f5">
	<form id="queryForm" method="get">
	<table border="0" width="100%" style="margin-left: 0px;">
		<tr>
		<td >任务状态:
			<select align="left" id="taskStatus_s" class="easyui-combobox" name="taskStatus" editable="false" style="width: 150px"  data-options="panelHeight:'auto'">
                <option value=""></option>
                <dictionary:options type="TASK_STATUS" status="1" />
            </select>
		<td>
			任务类型:
			<select id="taskType_s" class="easyui-combobox" editable="false" name="taskType" style="width: 150px"  data-options="panelHeight:'auto'">
               	<option value=""></option>
                <dictionary:options type="TASK_TYPE" status="1" />
            </select>
		</td>
		<td>
			作业船:<input id="worId_s"  class="easyui-combobox" name="worId" data-options="width:150, valueField: 'id', textField: 'workSname', url: '${ctx}/admin/orders/getWorkShipForOption'"/>
		</td>
		<td>
			客户船名/单位:<input id="customerShipId_s" class="easyui-combobox" name="customerShipId" data-options="width:150, valueField: 'id', textField: 'customerShipName', url: '${ctx}/admin/orders/getCustomerShipForOption'"/>
		</td>
		<td>
			联络员:<input id="tollCollector_s" class="easyui-combobox"  name="tollCollector" data-options="width:150, valueField: 'id', textField: 'tollCollector', url: '${ctx}/admin/orders/getCashierForOption'"/>
		</td>
		</tr>
	  </table>	
	  </form>
		</div>
		<div style="padding: 5px; border-bottom:1px solid #f2f3f5">
			&nbsp&nbsp<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search" >查询</a>
			<span class="datagrid-btn-separator"></span>
			<a href="#" id="clearQry" class="easyui-linkbutton" iconCls="icon-reset" >重置</a>
			<span class="datagrid-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="export_win();">导出</a>
			<span class="datagrid-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="_new()">创建任务</a>
			<span class="datagrid-btn-separator"></span>	
				<a href="#" id="feipai" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onClick="feipai()">分派</a> 
			<span class="datagrid-btn-separator"></span>	
				<a href="#" id="gaipai" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onClick="gaipai()">改派</a> 
			<span class="datagrid-btn-separator"></span>	
				<a href="#" id="removeBt" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="cancelOrder()">取消任务</a>
			<span class="datagrid-btn-separator"></span>	
		</div>
	</div>
	<div id="create_dlg" class="easyui-dialog" style="text-align:center;width:880px;height: 400px " closed="true" buttons="#create_dlg-buttons">
		<form id="fm" method="post">
		<span  style="font-size:18px" >任务信息</span>
		<table style="font-size:12px; width:100%; text-align:right; border="2" bordercolor="#F5FFE8">
			<tr>
				<td align="left"><label>任务类型    :</label>
					 <select id="taskType" name="taskType" class="easyui-combobox" editable="false" style="width: 150px"  data-options=" required:true, panelHeight:'auto'">
		                <dictionary:options type="TASK_TYPE" status="1" />
		            </select>
					<label>作业日期:</label><input id="workDate" name="workDate" type="text" class="easyui-datetimebox">	
					<label>是否已分类:</label>
						<input type="radio" id="yes" name="ifType" value="0" >是</input> 
						<input type="radio" id="no" name="ifType" value="1"  checked="checked" >否</input> 
					</td>							
			</tr>
			 <tr>
				 <td align="left">
				 	<label>作业船名    :</label>
						<input id="worId" name="worId" class="easyui-combobox" data-options="required:true, valueField: 'id', textField: 'workSname', url: '${ctx}/admin/orders/getWorkShipForOption'"/>
					<label>联 络 员:</label>
						<input id="tollCollector" name="tollCollector" class="easyui-combobox" data-options="required:true, valueField: 'id', textField: 'tollCollector', url: '${ctx}/admin/orders/getCashierForOption'"/>
				</td>
			</tr>
		</table>
		<span style="font-size:18px">详细信息</span>
		<table style="font-size:12px; width:100%; text-align:right; border="2" bordercolor="#F5FFE8">
			<tr>
			  <td align="left">
				<label>船名/单位 :</label>
				<input id="customerShipId" name="customerShipId" class="easyui-combobox" data-options="required:true, valueField: 'id', textField: 'customerShipName', url: '${ctx}/admin/orders/getCustomerShipForOption'"/>
			  	<label>&nbsp地       点 &nbsp&nbsp&nbsp&nbsp:</label>
			  		<input id="adress" name="adress" type="text" style="width: 175px" required="required">	
			 <%-- 	 <select id="adress" name="adress" class="easyui-combobox" editable="false" style="width: 175px"  data-options="required:true,panelHeight:'auto'">
		                <dictionary:options type="WS_ADDRESS" status="1" />
		            </select> --%>
			  	<label>服务时间:</label><input id="serviceDate" name="serviceDate" type="text" class="easyui-datetimebox">	
			  </td>
			</tr>
			<tr>
			  <td align="left">
			  	<label>收 费方式 :</label>
			 	<select id="chargeType" onchange="budgetTypeSelect(this.value)"	name="chargeType" class="easyui-combobox" editable="false" style="width: 150px"  data-options="required:true,panelHeight:'auto'  ">
		                <dictionary:options type="TOLL_TYPE" status="1" />
		         </select>
				<label>收费金额  :</label>
					<input id="chargeAmount" name="chargeAmount" type="text" validtype='intOrFloat'  invalidMessage="金额只能是整数或小数" disabled="disabled"/>
			 	<label>&nbsp&nbsp油&nbsp&nbsp&nbsp&nbsp污:</label>
					<input id="eGungo" name="eGungo" type="text" class="easyui-numberbox" data-options="min:0,precision:0"/>
				</td>
			  </td>
			</tr>
			<tr>
			  <td align="left">
			  	<label> 生 活 垃圾 :</label>
					<input id="fLife" name="eLife" style="width:145px; type="text" class="easyui-numberbox" data-options="min:0,precision:0"/>
				<label>扫仓垃圾&nbsp:</label>
					<input id="fSweeping" name="eSweeping" type="text" class="easyui-numberbox" data-options="min:0,precision:0"/>
				<label>食品废弃物:</label>
					<input id="fFood" name="eFood" type="text" style="width:160px; class="easyui-numberbox" data-options="min:0,precision:0"/>
			  </td>
			</tr>
			<tr>
			  <td align="left">
			  	<label>焚烧炉灰渣:</label>
					<input id="fBurnCinder" name="eBurnCinder" style="width:145px; type="text" class="easyui-numberbox" data-options="min:0,precision:0"/>
				<label>&nbsp&nbsp塑&nbsp&nbsp&nbsp&nbsp&nbsp料:</label>
					<input id="fPlastic" name="ePlastic" type="text" class="easyui-numberbox" data-options="min:0,precision:0"/>
				<label>生活污水:</label>
					<input id="fWater" name="eWater" type="text" class="easyui-numberbox" data-options="min:0,precision:0"/>
				</td>
			</tr>
			<tr>
				<td align="left">
					<label>垃圾量合计:</label>
					<input id="amountGarbage"  name="amountGarbage" style="width:145px; type="text" readonly="readonly" class="sum" />
				</td>
			</tr>
		 	<tr>
		 		<td align="left">
		 			<label> 备  &nbsp&nbsp&nbsp&nbsp注&nbsp&nbsp  :</label>
					<input id="memo" name="memo" class="easyui-textbox"  data-options="multiline:true"  style="width:700px;height:50px">
				</td>
			</tr>
		</table>
		<div id="create_dlg-buttons" style="text-align:center;width:380px; ">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOrder()">提交</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="orderCancel()">取消</a>
		</div>	
		</form>
	</div>
	<!-- 任务分派 -->
	<div id="fenpai_dlg" class="easyui-dialog" style="text-align:center;width:750px;height: 300px " closed="true" buttons="#fp_dlg-buttons">
		<form id="fm_fp" method="post">
		<table>
			<tr>
				<td></td>
				<td align="left"><input id="id" name="id" class="easyui-validatebox" hidden="hidden"></td>
			</tr>
			<tr>
				 <td align="left">
				 	<label>任务编号   :</label>
				 		<input id="taskCode"  name="taskCode" type="text" readonly="readonly"/>
				 	<label>作业船名    :</label>
						<input id="worId_fp" name="worId" class="easyui-combobox" data-options="valueField: 'id', textField: 'workSname', url: '${ctx}/admin/orders/getWorkShipForOption'"/>
				<label>联 络 人    :</label>
						<input id="tollCollector_fp" name="tollCollector" class="easyui-combobox" data-options=" valueField: 'id', textField: 'tollCollector', url: '${ctx}/admin/orders/getCashierForOption'"/>
				</td>
			</tr>
			<tr>
				<td align="left"><label>任务类型    :</label>
		            <input  name="taskType" id="taskType_fp" class="easyui-validatebox" type="text" readonly="readonly"/>
					<label>客户船名 :</label>
					<input  name="customerShipName" type="text"  style="width:150px;" readonly="readonly"/>
					<label>地&nbsp&nbsp点 :</label>
						<input  name="adress" type="text" id="fp_address" style="width:150px;" required="required"/>
					</td>							
			</tr>
			<tr>
				<td align="left">
					<label>是否已分类:</label>
							<input type="radio" id="yes" name="ifType" value="0" disabled>是</input> 
							<input type="radio" id="no" name="ifType" value="1"  checked="checked" disabled>否</input> 
				</td>							
			</tr>
			<tr>
				<td align="left">
					<label>收费方式 :</label>
		       	  		 <input  name="chargeType" id="chargeType_fp" style="width:150px;" class="easyui-validatebox" type="text" readonly="readonly"/>
						<label>作业时间 :</label>
						<input  name="workDateStr" type="text"  style="width:145px;"  class="easyui-datetimebox" />
				</td>							
			</tr>
			</table>
			<div id="fp_dlg-buttons" style="text-align:center;width:580px; ">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="FpsaveOrder()">提交</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="fenpCancel()">取消</a>
			</div>	
		</form>
	
	</div>
	<!-- 任务改派 -->
	<div id="gaipai_dlg" class="easyui-dialog" style="text-align:center;width:750px;height: 300px " closed="true" buttons="#gaipai_dlg-buttons">
		<form id="fm_gp" method="post">
		<table cellspacing="10">
			<tr>
				<td></td>
				<td align="left"><input id="id" name="id" class="easyui-validatebox" hidden="hidden"></td>
			</tr>
			<tr>
				 <td align="left">
				 	<label>任务编号   :</label>
				 		<input id="taskCode"  name="taskCode" type="text" style="width:165px;" readonly="readonly"/>
				 	 <label>&nbsp原作业船   :</label>
				 		<input name="workShipName" type="text" style="width:165px;text-align:center" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				 <td align="left">
				 	<label>现作业船    :</label>
						<input id="worId_gp" name="worId" class="easyui-combobox"  style="width:165px;" data-options="valueField: 'id', textField: 'workSname', url: '${ctx}/admin/orders/getWorkShipForOption'"/>
					<label>&nbsp联 络 人 :</label>
						<input id="tollCollector_gp" name="tollCollector" class="easyui-combobox" style="width:165px;" data-options=" valueField: 'id', textField: 'tollCollector', url: '${ctx}/admin/orders/getCashierForOption'"/>
				</td>
			</tr>
			<tr>
				<td align="left"><label>任务类型    :</label>
					   <input  name="taskType" id="taskType_gp" class="easyui-validatebox" type="text" style="width:120px;text-align:center" readonly="readonly"/>
					<label>&nbsp客户船名 :</label>
					<input  name="customerShipName" type="text"  style="width:120px;text-align:center" readonly="readonly"/>
					</td>							
			</tr>
			<tr>
				<td align="left">
					<label>地&nbsp&nbsp点 :</label>
						<input  name="adress" type="text" id="gp_address" style="width:120px;text-align:center" required="required"/>
					<label>&nbsp是否已分类:</label>
							<input type="radio" id="yes" name="ifType" value="0" style="width:35px;" disabled >是</input> 
							<input type="radio" id="no" name="ifType" value="1"  style="width:35px;" checked="checked" disabled>否</input> 
				</td>							
			</tr>
			<tr>
				<td align="left">
					<label>收费方式  :</label>
				 		<input  name="chargeType" id="chargeType_gp" class="easyui-validatebox" style="width:165px;" type="text" readonly="readonly"/>
					<label>&nbsp作业时间 :</label>
						<input  name="workDateStr" type="text"  style="width:165px;"  class="easyui-datetimebox"/>
				</td>							
			</tr>
			</table>
			<div id="gaipai_dlg-buttons" style="text-align:center;width:380px; ">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="GpsaveOrder()">提交</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="gaipCancel()">取消</a>
			</div>	
		</form>
		
	</div>
</body>
</html>