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
	$("#workDate").val(dateUtil.getNowMonth());
	$("#statisticDimension").val("0");
    $('#monthStatistic_List').datagrid({
        url: '${ctx}/admin/statistic/monthList',
        fit: true,
        toolbar: "#tb",
        rownumbers: true,
        singleSelect: true,
        showFooter: true,
        pageSize:15,
        pageList: [15,30, 45, 60],
        view: myview,
       queryParams: {
    	   workDate: $("#workDate").val(),
    	   statisticDimension: $("#statisticDimension").val()
        }, 
        emptyMsg: '暂无数据',
        columns: [[
            {field: 'id', width: '60', title: '',hidden: 'hidden'},
            {field: 'objName', width: '100', align: 'center', title: '对象'},
            {field: 'amountGarbageForShow', width: '90', align: 'center', title: '垃圾量合计',formatter:formatValue},
            {field: 'fwater', width: '90', align: 'center', title: '生活污水',formatter:formatValue},
            {field: 'flife', width: '90', align: 'center', title: '生活垃圾',formatter:formatValue},
            {field: 'fsweeping', width: '90', align: 'center', title: '扫舱垃圾',formatter:formatValue},
            {field: 'ffood', width: '90', align: 'center', title: '食品废弃物',formatter:formatValue},
            {field: 'fburnCinder', width: '90', align: 'center', title: '焚烧炉灰渣',formatter:formatValue},
            {field: 'fplastic', width: '90', align: 'center', title: '塑料',formatter:formatValue},
            {field: 'fgungo', width: '90', align: 'center', title: '油污量',formatter:formatValue},
        ]],
        pagination: true
    });	
	
	$('#monthStatistic_SearchBt').click(function() {
	    var queryParams = $('#monthStatistic_List').datagrid('options').queryParams;
	    queryParams={};

	
		queryParams.statisticDimension = $('#statisticDimension').combobox('getValue');
		queryParams.workDate = $('#workDate').val();

		
		$('#monthStatistic_List').datagrid('options').queryParams = queryParams;
		$('#monthStatistic_List').datagrid("options").url="${ctx}/admin/statistic/monthList";
		$("#monthStatistic_List").datagrid('load');
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




</script>

<body data-options="region:'center'" id="main" width="100%">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<form id="querymonthStatistic" method="post">
				<table border="0" width="50%" style="margin-left: 20px;">
					<tr>
						<td>
							统计维度： <select id="statisticDimension" name="statisticDimension" class="easyui-combobox" editable="false" style="width: 100px"  data-options="panelHeight:'auto'">
								<option value="0">客户</option>
								<option value="1">联络员</option>
								<option value="2">作业船</option>
						</select>
						</td>
						<td>
							月份：<input id="workDate" name="workDate" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM'})"/>
						</td>
						<td>
							<a href="#" id="monthStatistic_SearchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<table id="monthStatistic_List"></table>
	
</body>

