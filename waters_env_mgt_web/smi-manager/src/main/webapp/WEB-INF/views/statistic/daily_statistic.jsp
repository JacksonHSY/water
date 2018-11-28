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

<script type="text/javascript">
$(function() {
	$("#workDate").val(dateUtil.getNow());
    $('#dailyStatistic_List').datagrid({
        url: '${ctx}/admin/statistic/dailyList',
        fit: true,
        toolbar: "#tb",
        rownumbers: true,
        singleSelect: true,
        showFooter: true,
        pageSize:15,
        pageList: [15,30, 45, 60],
        view: myview,
       queryParams: {
    	   workDate: $("#workDate").val()
        }, 
        emptyMsg: '暂无数据',
        columns: [[
            {field: 'id', width: '60', title: '',hidden: 'hidden'},
            {field: 'workShipName', width: '100', align: 'center', title: '作业船'},
            {field: 'customerShipName', width: '100', align: 'center', title: '客户船名/单位'},
            {field: 'adress', width: '100', align: 'center', title: '地点'},
            {field: 'ifType', width: '100', align: 'center', title: '是否已分类',formatter:formatSortStatus},
            {field: 'charge', width: '100', align: 'center', title: '收费金额/方式'},
            /* {field: 'opt',width: '80',align: 'center',formatter : builderOperationLinks,title: '角色列表'},  */
            {field: 'amountGarbageForShow', width: '90', align: 'center', title: '垃圾量合计',formatter:formatValue},
            {field: 'flife', width: '90', align: 'center', title: '生活垃圾',formatter:formatValue},
            {field: 'fsweeping', width: '90', align: 'center', title: '扫舱垃圾',formatter:formatValue},
            {field: 'ffood', width: '90', align: 'center', title: '食品废弃物',formatter:formatValue},
            {field: 'fburnCinder', width: '90', align: 'center', title: '焚烧炉灰渣',formatter:formatValue},
            {field: 'fplastic', width: '90', align: 'center', title: '塑料',formatter:formatValue},
            {field: 'fwater', width: '90', align: 'center', title: '生活污水',formatter:formatValue},
            {field: 'fgungo', width: '90', align: 'center', title: '油污量',formatter:formatValue},
            {field: 'maritimeArea', width: '100', align: 'center', title: '所属海事辖区'},
            {field: 'workDateStr', width: '100', align: 'center', title: '服务时间'},
            {field: 'reson', width: '100', align: 'center', title: '拒绝原因'},
            {field: 'tollCollectorName', width: '100', align: 'center', title: '联络员'},
            {field: 'memo', width: '140', align: 'center', title: '备注'},
        ]],
        pagination: true
    });	
	
	$('#dailyStatistic_SearchBt').click(function() {
	    var queryParams = $('#dailyStatistic_List').datagrid('options').queryParams;
	    queryParams={};

	    queryParams.worId = $('#worId').combobox('getValue'); 
		queryParams.customerShipId = $('#customerShipId').combobox('getValue'); 
		queryParams.tollCollector = $('#tollCollector').combobox('getValue'); 
		queryParams.workDate = $('#workDate').val();
		queryParams.adress= $('#adress').val();
		/* queryParams.area= $('#area').val(); */
		
		$('#dailyStatistic_List').datagrid('options').queryParams = queryParams;
		$('#dailyStatistic_List').datagrid("options").url="${ctx}/admin/statistic/dailyList";
		$("#dailyStatistic_List").datagrid('load');
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


function formatSortStatus(val, row) {
	var flowStatus = "";
	if(val == "0"){
		flowStatus = "已分类";
	}else{
		flowStatus = "未分类";
	}
	return flowStatus;
}

function formatValue(val, row) {
	if(val == ""){
		val = "-";
	}
	return val;
}

function export_win(){
    $('#queryDailyStatistic').form('submit', {
	    url:'${ctx}/admin/statistic/dailyListXlsDownload',
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

<body data-options="region:'center'" id="main" width="100%">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<form id="queryDailyStatistic" method="get">
				<table border="0" width="100%" style="margin-left: 20px;">
					<tr>
						<td>
							作业船：<input id="worId" class="easyui-combobox" data-options="width:150, valueField: 'id', textField: 'workSname', url: '${ctx}/admin/orders/getWorkShipForOption'"/>
						</td>
						<td>
							客户船名/单位：<input id="customerShipId" class="easyui-combobox" data-options="width:150, valueField: 'id', textField: 'customerShipName', url: '${ctx}/admin/orders/getCustomerShipForOption'"/>
						</td>
						<td>
							地点：<input id="adress" name="adress"/> 
						</td>
					</tr>
					<tr>
						<td>
							日期：<input id="workDate" name="workDate" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td>
							联络员：<input id="tollCollector" class="easyui-combobox" data-options="width:150, valueField: 'id', textField: 'tollCollector', url: '${ctx}/admin/orders/getCashierForOption'"/>
						</td>
						<td>
							所属海事辖区：<input id="area" name="area"/> 
						</td>
						<td>
							<a href="#" id="dailyStatistic_SearchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
							<a href="#" id="" class="easyui-linkbutton" iconCls="icon-save" onclick="export_win();">导出</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<table id="dailyStatistic_List"></table>
	
</body>

