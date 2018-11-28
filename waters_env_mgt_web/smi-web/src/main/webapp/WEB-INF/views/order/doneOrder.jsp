<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>查看任务-已完成</title>
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
    	<img src="${ctx}/static/image/nbg.jpg" width="100%" />
    </div>
    <!-- pic over -->
    
    <div class="dbg" style="background-color:#fafafa; padding-bottom:30px;">
		<div id="warp">
        <!-- order --> 
        	<div class="order">
            	<div class="order_tit" style="margin-top:0px;">上海港船舶垃圾/生活污水接收单</div>
                <table width="100%" border="0" cellspacing="0" cellpadding="5">
                  <tr>
                    <td width="18%" align="right">船名：</td>
                    <td width="28%" align="left">${msg.infos.customerShipName}</td>
                    <td width="18%" align="right"></td>
                    <td align="left"></td>
                  </tr>
                  <tr>
                    <td align="right"><span class="red">*</span> 作业地点：</td>
                    <td align="left">
                    		${msg.infos.adress}
                    </td>
                    <td align="right"><span class="red">*</span> 作业时间：</td>
                    <td align="left">
                    ${msg.infos.workDate}
                    </td>
                  </tr>
                  <tr>
                    <td align="right"></td>
                    <td align="left"></td>
                    <td></td>
                    <td><span class="red">国家海事局规定，废物回收作业需提前2小时申报并审核</span></td>
                  </tr>
                </table>
                
				<div class="order_tit"><span>垃圾重量</span></div>
                
                <table width="100%" border="0" cellspacing="0" cellpadding="5">
                  <tr>
                    <td width="18%" align="right">是否已分类：</td>
                    <td width="28%" align="left">${msg.infos.ifType}</td>
                    <td width="18%" align="right"></td>
                    <td align="left"></td>
                  </tr>
                  <tr>
                    <td align="right">A.生活垃圾（立方）：</td>
                    <td align="left">${msg.infos.fLife}</td>
                    <td align="right">B.生活污水（立方）：</td>
                    <td align="left">${msg.infos.fWater}</td>
                  </tr>
                  <tr>
                    <td align="right">C.扫舱垃圾（立方）：</td>
                    <td align="left">${msg.infos.fSweeping}</td>
                    <td align="right" style="white-space: nowrap;">D.食品废弃物（立方）：</td>
                    <td align="left">${msg.infos.fFood}</td>
                  </tr>
                  <tr>
                    <td align="right">E.焚烧炉灰渣（立方）：</td>
                    <td align="left">${msg.infos.fBurnCinder}</td>
                    <td align="right">F.塑料（立方）：</td>
                    <td align="left">${msg.infos.fPlastic}</td>
                  </tr>
                  <tr>
                    <td align="right">G.油污量（立方）：</td>
                    <td align="left">${msg.infos.fGungo}</td>
                  </tr>
                </table>
                
				<div class="order_tit"><span>订单状态</span></div>
                
                <table width="100%" border="0" cellspacing="0" cellpadding="5">
                  <tr>
                    <td class="green_s">已完成</td>
                  </tr>
                </table>
                
				<div class="order_tit"><span>其他</span></div>
                <div>
                    ${msg.infos.memo} 
                </div>  
                <table width="100%" border="0" cellspacing="0" cellpadding="5">
                  
                  <div class="order_tit"><span>满意度评价</span></div>
                  <div>
                  	<c:forEach var="i" begin="1" end="${msg.infos.judge}" step="1">   
						<img src="${ctx}/static/image/icon_stars01.png" width="15" />
					</c:forEach> 
					<c:forEach var="i" begin="1" end="${5-msg.infos.judge}" step="1">   
						<img src="${ctx}/static/image/icon_stars02.png" width="15" />
					</c:forEach> 
                  </div>
                  <tr>
                    <td align="right">
                    <input type="button" class="login_btn06_b" value="返  回" onclick="javascript:history.back(-1);" />
                    </td>
                  </tr>
                </table>
            </div>
            <div class="zs">
            	注：1.生活垃圾包括被磨碎的纸制品、破布、玻璃、金属、瓶子、陶器等以及货物残余物、纸制品、破布、玻璃、金属、瓶子、陶器等。<br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.扫舱牢记包括垫舱物料、材料或包装材料。
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
