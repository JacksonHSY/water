<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>垃圾信息统计</title>
<style>
body{
	padding:0px;
	margin:0px;
	font-size:14px;
	font-family:"微软雅黑";
}

.div_01{
	padding:10px;
}
.div_q{
	width:98%;
	float:left;
	padding:1%;
	background-color:#ffffff;
}
.div_tit{
	width:100%;
	float:left;
	border-bottom:1px solid #cfcfcf;
	padding-bottom:5px;
	margin-bottom:10px;
}
.div_02{
	width:47%;
	float:left;
	margin-top:10px;
	padding:10px;
	background-color:#ffffff;
}
.div_02 th{
	text-align:center;
	background-color:#7fc8ea;
}
.div_02 td{
	text-align:center;
}
.div_03 th{
	text-align:center;
	background-color:#7fc8ea;
}
.div_03 td{
	text-align:center;
}
.div_03{
	width:47%;
	float:right;
	margin-top:10px;
	padding:10px;
	background-color:#ffffff;
}
.div_04{
	width:12%;
	float:left;
	height:210px;
	margin-bottom:10px;
	margin-right:0.8%;
	padding:0.8%;
	background-color:#ffffff;
	position:relative;
}
.div_05{
	width:12%;
	float:right;
	height:210px;
	margin-bottom:10px;
	padding:0.8%;
	background-color:#ffffff;
	position:relative;
}
.div_06{
	width:31.1%;
	float:left;
	height:370px;
	margin-right:0.9%;
	padding:0.8%;
	background-color:#ffffff;
	position:relative;
}
.div_07{
	width:64.8%;
	float:right;
	height:370px;
	padding:0.8%;
	background-color:#ffffff;
	position:relative;
}
.div_pic{
	width:80%;
	position:absolute;
	top:50px;
	left:10%;
}
.div_z{
	width:80%;
	position:absolute;
	top:100px;
	left:10%;
	text-align:center;
}
.div_z01{
	width:100%;
	text-align:center;
	color:#80bec4;
	float:left;
	font-size:18px;
	font-weight:bold;
}
.div_z02{
	width:100%;
	float:left;
	text-align:center;
	color:#80bec4;
	font-size:14px;
}
.div_z03{
	width:100%;
	text-align:center;
	color:#6cd89e;
	float:left;
	font-size:18px;
	font-weight:bold;
}
.div_z04{
	width:100%;
	float:left;
	text-align:center;
	color:#6cd89e;
	font-size:14px;
}
.div_z05{
	width:100%;
	text-align:center;
	color:#f7c34d;
	float:left;
	font-size:18px;
	font-weight:bold;
}
.div_z06{
	width:100%;
	float:left;
	text-align:center;
	color:#f7c34d;
	font-size:14px;
}
.div_z07{
	width:100%;
	text-align:center;
	color:#6471e0;
	float:left;
	font-size:18px;
	font-weight:bold;
}
.div_z08{
	width:100%;
	float:left;
	text-align:center;
	color:#6471e0;
	font-size:14px;
}
.div_z09{
	width:100%;
	text-align:center;
	color:#c183c0;
	float:left;
	font-size:18px;
	font-weight:bold;
}
.div_z10{
	width:100%;
	float:left;
	text-align:center;
	color:#c183c0;
	font-size:14px;
}
.div_z11{
	width:100%;
	text-align:center;
	color:#eda056;
	float:left;
	font-size:18px;
	font-weight:bold;
}
.div_z12{
	width:100%;
	float:left;
	text-align:center;
	color:#eda056;
	font-size:14px;
}
.div_z13{
	width:100%;
	text-align:center;
	color:#c1b583;
	float:left;
	font-size:18px;
	font-weight:bold;
}
.div_z14{
	width:100%;
	float:left;
	text-align:center;
	color:#c1b583;
	font-size:14px;
}
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/default/tooltip.css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.tooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/highcharts/highcharts.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/js/circle_progress/circle-progress.js"></script> --%>
<script>
$(function () {
    var columnChart;//柱状图
    var gradientChart;//饼状图
    
 	// Build the pie
    $.post("${ctx}/admin/report/garbage/getGarbageStatisticTotalForPie",{},function(result){
    	Highcharts.getOptions().colors = $.map(Highcharts.getOptions().colors, function(color) {
		    return {
		        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		        stops: [
		            [0, color],
		            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		        ]
		    };
		});
		
        gradientChart = new Highcharts.Chart({
            chart: {
                renderTo: 'proportion_div',
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            colors: ['#80bec4','#6cd89e','#f7c34d', '#6471e0', '#c183c0','#eda056'], 
            title: {
                text: result.title
            },
            tooltip: {
        	    pointFormat: '<b style="color:{series.color}">{series.name}</b>: <b>{point.percentage}%</b>',
            	percentageDecimals: 1
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        formatter: function() {
                            return '<b style="color:{series.color}">'+ this.point.name +'</b>';
                        }
                    }
                }
            },
            series: result.series
        });
    });
    
    //build the column
    $.post("${ctx}/admin/report/garbage/getGarbageStatisticTotalForColumn",{},function(result){
    	console.log(result.xAxis);
    	 columnChart = new Highcharts.Chart({
    	    	chart: {
    	    		renderTo: 'histogram_div',
    	    		type: 'column'
    	        },
    	        title: {
    	            text: result.title
    	        },
    	        subtitle: {
    	            text: ''
    	        },
    	        colors: ['#80bec4','#6cd89e','#f7c34d', '#6471e0', '#c183c0','#eda056'], 
    	        xAxis: {
    	            categories: result.xaxis,
    	            crosshair: true
    	        },
    	        yAxis: {
    	            min: 0,
    	            title: {
    	                text: '总量 (m³)'
    	            }
    	        },
    	        tooltip: {
    	            headerFormat: '<span style="color:{series.color};font-size:10px">{point.key}</span><table>',
    	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
    	            '<td style="color:{series.color};padding:0"><b>{point.y} m³</b></td></tr>',
    	            footerFormat: '</table>',
    	            shared: true,
    	            useHTML: true
    	        },
    	        plotOptions: {
    	            column: {
    	                pointPadding: 0.2,
    	                borderWidth: 0
    	            }
    	        },
    	        series: result.series
    	    });
    });
    
});
</script>
<style>
</style>
</head>
<body style="background-color:#f0f5f6;">
    <div class="div_01">
    	<div style="float:left; width:100%; margin-bottom:10px;">
          	<c:forEach var="item" items="${circleValues}" varStatus="status"> 
          		<c:choose>
          			<c:when test="${!status.last}">
          				<div class="div_04">
				          		<div class="div_tit">${item.name}(立方)</div>
				                <div class="div_pic"><img src="${ctx}/static/images/circle/y_0${status.index+1}.png" width="100%" /></div>
				                <div class="div_z">
				                	<span class="div_z<c:if test="${2*status.index+1 <10}">0</c:if>${2*status.index+1}">${item.momRate}%</span>
				                    <span class="div_z<c:if test="${2*(status.index+1) <10}">0</c:if>${2*(status.index+1)}">月增长率</span>
				                    <span class="div_z<c:if test="${2*(status.index+1) <10}">0</c:if>${2*(status.index+1)}">${item.y}</span>
				                </div>
				          </div>
          			</c:when>
          			<c:otherwise>
          				<div class="div_05">
				          		<div class="div_tit">${item.name}(立方)</div>
				                <div class="div_pic"><img src="${ctx}/static/images/circle/y_0${status.index+1}.png" width="100%" /></div>
				                <div class="div_z">
				                	<span class="div_z${2*status.index+1}">${item.momRate}%</span>
				                    <span class="div_z${2*(status.index+1)}">月增长率</span>
				                    <span class="div_z${2*(status.index+1)}">${item.y}</span>
				                </div>
				          </div>
          			</c:otherwise>
          		</c:choose>  
		          
			</c:forEach> 
        </div>
        <div class="div_06">
            <div class="div_tit">当月月各类废物收集量比重</div>
             <div id="proportion_div"></div>
        </div>
        <div class="div_07">
            <div class="div_tit">各类废料的季度收集量（单位：立方）</div>
            <div id="histogram_div"></div>
        </div>
    </div>
</body>
</html>