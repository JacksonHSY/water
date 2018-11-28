<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>公告详情</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
</head>

<body>
    <!-- head --> 
       <%@ include file="../top.jsp"%>
    <!-- head over -->
    
    <!-- pic --> 
    <div class="dbg">
    	<img src="${ctx}/static/image/grzx.jpg" width="100%" />
    </div>
    <!-- pic over -->
    
    <div class="dbg" style="background-color:#fafafa; padding-bottom:30px;">
		<div id="warp">
        <!-- order -->  
        	<div class="order" style="padding-bottom:80px;">
        			<div class="order_tit" style="margin-top:0px; text-align:center"></div>
        					<p class="order_tit" style="margin-top:0px;text-align:center;">&nbsp;&nbsp;${msg.infos.title} </p>
							<div class="order_ms" style="margin-top:0px; text-align:center">作者：${msg.infos.issue} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间：${msg.infos.createTimeStr} </div>			
							<div class="order_ms" style="margin-top:0px;text-indent:2em;">${msg.infos.content}</div>
            </div>
        <!-- order over -->
  		</div>
	</div>
   
    <!-- footer over -->
  		<%@ include file="../bottom.jsp" %> 
    <!-- footer over -->
	<div class="fc" id="fc_div">
    	<div class="fc_q"></div>
    	<div class="fc_div" style="top:800px;">
   	    请联系管理员重置密码
            <div class="fc_close" onclick="fc_02()">
            <img src="${ctx}/static/image/icon_close.png" width="30" />
            </div>
        </div>
    </div>
</body>
</html>



