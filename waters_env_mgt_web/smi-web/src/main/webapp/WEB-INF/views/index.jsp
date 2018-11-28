<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>城投水务</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<link href="${ctx}/static/css/page_nav.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.min.js"></script>

<script type="text/javascript">
	var taskCode;
	function prompt(){				
		document.getElementById("fc_div01").style.display="block";	
	}
	function cancleOrder(row){	
		taskCode = row;
		document.getElementById("fc_div02").style.display="block";	
	}
	function conf(){
		document.getElementById("fc_div02").style.display="none";		
	    window.location = encodeURI("${ctx}/orders/cancelOrder?taskCode="+taskCode);		
	}
	function fc_02(){
	      document.getElementById("fc_div01").style.display="none";
	 }
		  function fc_01(){
		      document.getElementById("fc_div").style.display="block";
		  }
		  function fc_05(){
		      document.getElementById("fc_grzx").style.display="block";
		  }
		  function fc_06(){
		      document.getElementById("fc_grzx").style.display="none";
		  }
		  function cz_add_01(){
		      document.getElementById("cz_add01").style.display="";
		  }
		  function cz_add_02(){
		      document.getElementById("cz_add01").style.display="none";
		  }
		  function cz_add_03(){
		      document.getElementById("cz_add02").style.display="none";
		  }
		  function cz_add_04(){
		      document.getElementById("cz_add03").style.display="none";
		  }
</script>
</head>
<body>
    <!-- head --> 
      <div class="dbg">
   	  <div id="warp">
        <div class="logo"><a href="${ctx}/index"><img src="${ctx}/static/image/logo.png" width="100%" /></a></div>
       	<div class="nTab">
            <!-- nTab --> 
                <div class="TabTitle"> 
                    <ul id="myTab2"> 
                    <li class="active" onclick="nTabs(this,0);"><a href="${ctx}/index" class="font_b">首页</a></li> 
                    <li class="normal" onclick="nTabs(this,1);"><a href="${ctx}/index/aboutus" class="font_b">关于我们</a></li>
                    <li class="normal" onclick="nTabs(this,2);"><a href="${ctx}/index/ourservices" class="font_b">我们的服务</a></li>
                    <li class="normal" onclick="nTabs(this,3);"><a href="${ctx}/index/contactus" class="font_b">联系我们</a></li>
                    <li class="normal" onclick="nTabs(this,4);"><a href="${ctx}/index/download" class="font_b">下载手机APP</a></li>
                    <c:if test="${null != customer}">
                    	<li class="normal" style="color:#666" onmouseover="fc_05()"><img src="${ctx}/static/image/icon_user.png" width="20" align="center" /> ${customer.userName}<img src="${ctx}/static/image/icon_sj.png" width="12" align="center" /></li>
                    </c:if>
                    </ul> 
                </div>
                <div class="grzx" id="fc_grzx" onmouseover="fc_05()" onmouseout="fc_06()">
                  	<ul> 
                     <li><a href="${ctx}/index/userInfo">个人信息</a></li> 
                      <li><a href="${ctx}/index/modifyPassword">修改密码</a></li>
                      <li style="border:0px;"><a href="${ctx}/index/logout">退出登录</a></li>
                      </ul>
                  </div> 
            <!-- TabContent --> 
            <div class="TabContent"> 
                <div id="myTab2_Content0">
                </div>
                <div id="myTab2_Content1" class="none">
                </div> 
                <div id="myTab2_Content2" class="none">
                </div>
                <div id="myTab2_Content3" class="none">
                </div>
                <div id="myTab2_Content4" class="none">
                </div>
            </div>
        </div>
        </div>    	
    </div>
    <!-- head over -->
    
    <!-- pic --> 
    <div class="dbg">
    	<img src="${ctx}/static/image/dbg.jpg" width="100%" />
    </div>
    <!-- pic over -->
    
    <div class="dbg">
		<div id="warp">
        <!-- order --> 
			<div class="login">
				<div class="login_tit" style="margin-bottom:11px;"><span>订单</span></div>
				<div class="login_btn"><input type="button" class="login_btn08" value="创建回收任务" onclick="javascript:top.location='${ctx}/orders/createOrder';" /></div>
			<div class="div_02">
            <div class="div_tit"></div>
        </div>
        		<c:forEach var="ws" items="${resp.infos.results}" varStatus="s">
        		<c:choose>
                    <c:when test="${ws.taskStatus==0}">
        			<div class="login_k_q">
                	<div class="login_k_l02">
                    	<span class="login_k_l_tit01" title="${ws.customerShipName}">${ws.customerShipName}</span>
                    	<span class="login_k_l_tit02">&nbsp</span>
                    	<span class="login_k_l_tit03"><a href="${ctx}/orders/orderDeal?taskCode=${ws.taskCode}" class="font_w">查看详情 ></a></span>						
                    </div>
                	<div class="login_k_r">
                    	<div class="login_k_r_img"><img src="${ctx}/static/image/icon_sj02.png" width="100%" /></div>
                   	  <table width="100%" border="0" cellspacing="0" cellpadding="12">
                          <tr>
                            <td class="strong" style="white-space: nowrap;">${ws.adress}</td>
                            <td class="strong" style="white-space: nowrap;">${ws.workDate}</td>                            
                            <td class="blue" width="100">待确认</td>
                            <td rowspan="2" width="105" align="center" style="border-left:1px solid #cfcfcf;">
                            <input type="button" class="login_btn05" onclick="javascript:top.location='${ctx}/orders/updateOrder?taskCode=${ws.taskCode}';" value="修  改" />
                            <input type="button" class="login_btn06" onclick="cancleOrder('${ws.taskCode}');" value="取  消" />
                            </td>                          
                          </tr>
                          <tr>
                            <td>所在码头</td>
                            <td>到港时间</td>
                            <td>状态</td>
                          </tr>
                        </table>
                    </div>
                </div> 
                </c:when>
                
                <c:when test="${ws.taskStatus==1}">
        			<div class="login_k_q">
                	<div class="login_k_l01">
                    	<span class="login_k_l_tit01" title="${ws.customerShipName}">${ws.customerShipName}</span>
                    	<span class="login_k_l_tit02">&nbsp</span>
                    	<span class="login_k_l_tit03"><a href="${ctx}/orders/orderDeal?taskCode=${ws.taskCode}" class="font_w">查看详情 ></a></span>						
                    </div>
                	<div class="login_k_r">
                    	<div class="login_k_r_img"><img src="${ctx}/static/image/icon_sj01.png" width="100%" /></div>
                   	  <table width="100%" border="0" cellspacing="0" cellpadding="12">
                          <tr>
                            <td class="strong" style="white-space: nowrap;">${ws.adress}</td>
                            <td class="strong" style="white-space: nowrap;">${ws.workDate}</td>                            
                            <td class="green" width="100">已受理</td>
                            <td rowspan="2" width="105" align="center" style="border-left:1px solid #cfcfcf;">
                            <input type="button" class="login_btn05" onclick="prompt();" value="修  改" />
                            <input type="button" class="login_btn06" onclick="prompt();" value="取  消" />
                            </td>                          
                          </tr>
                          <tr>
                            <td>所在码头</td>
                            <td>到港时间</td>
                            <td>状态</td>
                          </tr>
                        </table>
                    </div>
                </div> 
                </c:when>
                
                 <c:when test="${ws.taskStatus==2}">
        			<div class="login_k_q">
                	<div class="login_k_l01">
                    	<span class="login_k_l_tit01" title="${ws.customerShipName}">${ws.customerShipName}</span>
                    	<span class="login_k_l_tit02">&nbsp</span>
                    	<span class="login_k_l_tit03"><a href="${ctx}/orders/orderDeal?taskCode=${ws.taskCode}" class="font_w">查看详情 ></a></span>						
                    </div>
                	<div class="login_k_r">
                    	<div class="login_k_r_img"><img src="${ctx}/static/image/icon_sj01.png" width="100%" /></div>
                   	  <table width="100%" border="0" cellspacing="0" cellpadding="12">
                          <tr>
                            <td class="strong" style="white-space: nowrap;">${ws.adress}</td>
                            <td class="strong" style="white-space: nowrap;">${ws.workDate}</td>                            
                            <td class="green" width="100">处理中</td>
                            <td rowspan="2" width="105" align="center" style="border-left:1px solid #cfcfcf;">
                            </td>                          
                          </tr>
                          <tr>
                            <td>所在码头</td>
                            <td>到港时间</td>
                            <td>状态</td>
                          </tr>
                        </table>
                    </div>
                </div> 
                </c:when>
                
                <c:when test="${ws.taskStatus==3}">
        			<div class="login_k_q">
                	<div class="login_k_l03">
                    	<span class="login_k_l_tit01" title="${ws.customerShipName}">${ws.customerShipName}</span>
                    	<span class="login_k_l_tit02">&nbsp</span>
                    	<span class="login_k_l_tit03"><a href="${ctx}/orders/orderDeal?taskCode=${ws.taskCode}" class="font_w">查看详情 ></a></span>						
                    </div>
                	<div class="login_k_r">
                    	<div class="login_k_r_img"><img src="${ctx}/static/image/icon_sj03.png" width="100%" /></div>
                   	  <table width="100%" border="0" cellspacing="0" cellpadding="12">
                          <tr>
                            <td class="strong" style="white-space: nowrap;">${ws.adress}</td>
                            <td class="strong" style="white-space: nowrap;">${ws.updateTime}</td>                            
                            <td class="glay" width="100">已取消</td>
                            <td rowspan="2" width="105" align="center" style="border-left:1px solid #cfcfcf;">
                            </td>                         
                          </tr>
                          <tr>
                            <td>所在码头</td>
                            <td>到港时间</td>
                            <td>状态</td>
                          </tr>
                        </table>
                    </div>
                </div> 
                </c:when>
                
                <c:when test="${ws.taskStatus==4}">
        			<div class="login_k_q">
                	<div class="login_k_l03">
                    	<span class="login_k_l_tit01" title="${ws.customerShipName}">${ws.customerShipName}</span>
                    	<span class="login_k_l_tit02">&nbsp</span>
                    	<span class="login_k_l_tit03"><a href="${ctx}/orders/judgeOrder?taskCode=${ws.taskCode}" class="font_w">查看详情 ></a></span>						
                    </div>
                	<div class="login_k_r">
                    	<div class="login_k_r_img"><img src="${ctx}/static/image/icon_sj03.png" width="100%" /></div>
                   	  <table width="100%" border="0" cellspacing="0" cellpadding="12">
                          <tr>
                            <td class="strong" style="white-space: nowrap;">${ws.adress}</td>
                            <td class="strong" style="white-space: nowrap;">${ws.updateTime}</td>                            
                            <td class="glay" width="100">已完成</td>
                            <td rowspan="2" width="105" align="center" style="border-left:1px solid #cfcfcf;">
                            <input type="button" class="login_btn07" value="评  价" onclick="javascript:top.location='${ctx}/orders/judgeOrder?taskCode=${ws.taskCode}';" />
                            </td>                         
                          </tr>
                          <tr>
                            <td>所在码头</td>
                            <td>到港时间</td>
                            <td>状态</td>
                          </tr>
                        </table>
                    </div>
                </div> 
                </c:when>
                <c:when test="${ws.taskStatus==5}">
        			<div class="login_k_q">
                	<div class="login_k_l03">
                    	<span class="login_k_l_tit01" title="${ws.customerShipName}">${ws.customerShipName}</span>
                    	<span class="login_k_l_tit02">&nbsp</span>
                    	<span class="login_k_l_tit03"><a href="${ctx}/orders/doneOrder?taskCode=${ws.taskCode}" class="font_w">查看详情 ></a></span>						
                    </div>
                	<div class="login_k_r">
                    	<div class="login_k_r_img"><img src="${ctx}/static/image/icon_sj03.png" width="100%" /></div>
                   	  <table width="100%" border="0" cellspacing="0" cellpadding="12">
                          <tr>
                            <td class="strong" style="white-space: nowrap;">${ws.adress}</td>
                            <td class="strong" style="white-space: nowrap;">${ws.updateTime}</td>                            
                            <td class="glay" width="100">已完成</td>
                            <td rowspan="2" width="105" align="center" style="border-left:1px solid #cfcfcf;">
                           	 满意度评价 <br /><br />
								<c:forEach var="i" begin="1" end="${ws.judge}" step="1">   
									 <img src="${ctx}/static/image/icon_stars01.png" width="15" />
								</c:forEach> 
								<c:forEach var="i" begin="1" end="${5-ws.judge}" step="1">   
									<img src="${ctx}/static/image/icon_stars02.png" width="15" />
								</c:forEach> 
                            </td>                        
                          </tr>
                          <tr>
                            <td>所在码头</td>
                            <td>到港时间</td>
                            <td>状态</td>
                          </tr>
                        </table>
                    </div>
                </div> 
                </c:when>
                </c:choose>
                </c:forEach>
        	<div class="flickr">    
        		<table class="page" cellpadding="0" cellspacing="5">
	                   <div id="pageNav" class="scott" align="center">
	                         <tr>   
		                      	 <div class="page-link" >
		                      	     <td align="center">
										<font color="#88af3f">共${totalCount} 条数据,共${totalPage} 页${pageNav}</font>
									  </td>
								 </div>
							  </tr>
						</div>	
     
            	</table>
            </div>
        	</div>
        <!-- order over -->
        <!-- fengcai --> 
			<div class="fengcai">
				<div class="fengcai_tit"><span>城投风采</span></div>
				<div class="fengcai_k">
                	<img src="${ctx}/static/image/fengcai_bg.png" width="100%" />
                </div>
				<div class="fengcai_tit" style="margin-top:0px;">
                	
                </div>
				<div class="fengcai">
                	
                </div>
                <%@ include file="announcement/announcement.jsp" %> 
        <!-- fengcai over -->
  		</div>
	</div>
    
    <!-- footer over -->
     	<%@ include file="bottom.jsp" %> 
    <!-- footer over -->
    
	<div class="fc" id="fc_div01">
    	<div class="fc_q"></div>
    	<div class="fc_div" style="top:800px;">
        	请致电客服电话，进行修改，谢谢！<br /><br />
            <img src="${ctx}/static/image/icon_tep.png" align="center"  /> <span class="blue">13817484102</span>
            <div class="fc_close" onclick="fc_02()">
            <img src="${ctx}/static/image/icon_close.png" width="30" />
            </div>
        </div>
    </div>
    
	<div class="fc" id="fc_div02">
    	<div class="fc_q"></div>
    	<div class="fc_div" style="top:800px; padding:0px; height:150px;">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2" height="100" align="center" style="border-bottom:1px solid #2396f3">是否删除订单？</td>
          </tr>
          <tr>
            <td height="50" style="border-right:1px solid #2396f3" width="50%">
            <input type="button" class="login_btn09" value="确  定" onclick="conf();" />
            </td>
            <td>
            <input type="button" class="login_btn09" value="取  消" onclick="fc_04()" />
            </td>
          </tr>
        </table>
            <div class="fc_close" onclick="fc_04()">
            <img src="${ctx}/static/image/icon_close.png" width="30" />
            </div>
        </div>
    </div>
</body>
</html>
