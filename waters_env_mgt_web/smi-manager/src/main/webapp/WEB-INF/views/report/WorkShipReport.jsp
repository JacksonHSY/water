<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<html>
<head>
<title>作业船工作量统计</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/default/tooltip.css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.tooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/highcharts/highcharts.js"></script>

<script>
$(document).ready(function(){ 
	highchartsColumn();
}); 
function setChart(name, categories, data, color) {
    chart.xAxis[0].setCategories(categories);
    chart.series[0].remove();
    chart.addSeries({
        name: name,
        data: data,
        color: color || 'white'
    });
}
//柱状图 ----------------------------------------  
function highchartsColumn(){  
	var workDate = $('#workDate').val();
    var chart;
    $.ajax({  
        async : false,  
        type : 'POST',  
        url : '${ctx}/admin/WorkShipReport/getOrdersForAmountGarbageTop',
        dataType : 'json',  
        data: {"workDate": workDate}, 
        success : function(data) {  
    	  chart = new Highcharts.Chart({
          chart: {
              renderTo: 'container',
              type: 'column'
          },
          title: {
              text: data.title
          },
          subtitle: {
              text: data.subTitle
          },
          xAxis: {
              categories: data.workSnames
          },
          yAxis: {
              title: {
                  text: data.text
              }
          },
          plotOptions: {
              column: {
                  cursor: 'pointer',
                  point: {
                      events: {
                          click: function() {
                              var drilldown = this.drilldown;
                              if (drilldown) { // drill down
                                  setChart(drilldown.name, drilldown.categories, drilldown.data, drilldown.color);
                              } else { // restore
                                  setChart(name, categories, data);
                              }
                          }
                      }
                  },
                  dataLabels: {
                      enabled: false,
                      color: '#60c1e8',
                      style: {
                          fontWeight: 'bold'
                      },
                      formatter: function() {
                          return ''+  this.x +': '+ this.y +'立方';
                      }
                  }
              }
          },
          tooltip: {
        	  formatter: function() {
        		  return ''+  this.x +': '+ this.y +'立方';
              }
          },
          series: [{
              name: "月度排名",
              data: data.amountGarbageForShow,
              color: '#60c1e8'
          }],
          exporting: {
              enabled: false
          }
      });
        },  
        error : function(XMLHttpRequest, textStatus, errorThrown) {  
        }  
    });  
}  
</script>
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
.div_tit{
	width:100%;
	float:left;
	border-bottom:1px solid #cfcfcf;
	padding-bottom:5px;
	margin-bottom:10px;
}
.div_02{
	width:31%;
	float:left;
	min-height:218px;
	margin-top:10px;
	padding:0.8%;
	background-color:#ffffff;
}
.div_02 th{
	text-align:center;
	font-size:14px;
	background-color:#7fc8ea;
}
.div_02 td{
	text-align:center;
	font-size:14px;
}
.div_03 th{
	text-align:center;
	font-size:14px;
	background-color:#7fc8ea;
}
.div_03 td{
	text-align:center;
	font-size:14px;
}
.div_03{
	width:31%;
	float:left;
	margin-top:10px;
	padding:0.8%;
	margin-left:0.7%;
	min-height:218px;
	background-color:#ffffff;
}
.div_04{
	width:31%;
	float:right;
	padding:0.8%;
	margin-top:10px;
	margin-right:0.7%;
	min-height:218px;
	background-color:#ffffff;
}
.div_04 th{
	text-align:center;
	font-size:14px;
	background-color:#7fc8ea;
}
.div_04 td{
	text-align:center;
	font-size:14px;
}

</style>
</head>
<body style="background-color:#f0f5f6;">
    <div class="div_01">
    	 <div id="query" style="width: 500px; height: 20px;style="background-color:#60c1e8;">
			<label> 月 份 : </label><input id="workDate" name="workDate" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM'})"/>
			<button id="myButton" type="submit" onClick="highchartsColumn()">查询</button>
		</div> 
        <div class="div_01" style="background-color:#ffffff;">
            <div class="div_tit" id="container"  style="min-width: 300px; height: 300px; margin: 0 auto">月度作业船收集统计</div>
        </div>
        <div class="div_02">
            <div class="div_tit">月度之星</div>
            <table width="100%" border="0" cellspacing="1" cellpadding="5">
              <tr>
                <th>排名</th>
                <th>作业船</th>
                <th>月回收总量</th>
              </tr>
              <tr bgcolor="#e8f5fb">
               <c:choose>
					<c:when test="${not empty glist}">
						<c:forEach var="ws" items="${glist}" varStatus="s">
							<c:choose>
									<c:when test="${s.index==1||s.index==3}">
											<tr bgcolor="#e8f5fb">
												<td width="50">${s.index+1}</td>
												<td >${ws.workShipName}</td>
												<td width="100">${ws.amountGarbageForShow}(立方)</td>
											</tr>
									</c:when>
									<c:otherwise>
										<tr >
												<td >${s.index+1}</td>
												<td >${ws.workShipName}</td>
												<td >${ws.amountGarbageForShow}(立方)</td>
											</tr>
									</c:otherwise>
								</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
						<td colspan="3">没有相关数据</td>
						</tr>
					</c:otherwise>
				 </c:choose>
       		 </tr>
            </table>
        </div>
        <div class="div_03">
            <div class="div_tit">出船标兵</div>
            <table width="100%" border="0" cellspacing="1" cellpadding="5">
              <tr>
                <th>排名</th>
                <th>作业船</th>
                <th>出船次数</th>
              </tr>
                <tr bgcolor="#e8f5fb">
              <c:choose>
					<c:when test="${not empty oList}">
						<c:forEach var="js" items="${oList}" varStatus="s">
							<c:choose>
								<c:when test="${s.index==1||s.index==3}">
										<tr bgcolor="#e8f5fb">
											<td width="50">${s.index+1}</td>
											<td >${js.workShipName}</td>
											<td width="100">${js.memo}</td>
										</tr>
								</c:when>
								<c:otherwise>
									<tr >
											<td >${s.index+1}</td>
											<td >${js.workShipName}</td>
											<td >${js.memo}</td>
										</tr>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
						<td colspan="3">没有相关数据</td>
						</tr>
					</c:otherwise>
				 </c:choose>
       		 </tr>
            </table>
        </div>
         <div class="div_04">
            <div class="div_tit">服务标兵</div>
            <table width="100%" border="0" cellspacing="1" cellpadding="5">
              <tr>
                <th>排名</th>
                <th>作业船</th>
                <th>好评率</th>
              </tr>
             <tr bgcolor="#e8f5fb">
               <c:choose>
					<c:when test="${not empty jlist}">
						<c:forEach var="js" items="${jlist}" varStatus="s">
							<c:choose>
								<c:when test="${s.index==1||s.index==3}">
										<tr bgcolor="#e8f5fb">
											<td width="50">${s.index+1}</td>
											<td >${js.workShipName}</td>
											<td width="100">${js.judgeRate}</td>
										</tr>
								</c:when>
								<c:otherwise>
									<tr >
											<td >${s.index+1}</td>
											<td >${js.workShipName}</td>
											<td >${js.judgeRate}</td>
										</tr>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
						<td colspan="3">没有相关数据</td>
						</tr>
					</c:otherwise>
				 </c:choose>
       		 </tr>
            </table>
        </div>
        
    </div>
</body>
</html>