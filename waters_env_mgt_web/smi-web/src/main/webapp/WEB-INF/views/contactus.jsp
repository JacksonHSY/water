<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>联系我们</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript">
	
  function fc_01(){
      document.getElementById("fc_div").style.display="block";
  }
  function fc_02(){
      document.getElementById("fc_div").style.display="none";
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
                    <li class="normal" onclick="nTabs(this,0);"><a href="${ctx}/index" class="font_b">首页</a></li> 
                    <li class="normal" onclick="nTabs(this,1);"><a href="${ctx}/index/aboutus" class="font_b">关于我们</a></li>
                    <li class="normal" onclick="nTabs(this,2);"><a href="${ctx}/index/ourservices" class="font_b">我们的服务</a></li>
                    <li class="active" onclick="nTabs(this,3);"><a href="${ctx}/index/contactus" class="font_b">联系我们</a></li>
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
    	<img src="${ctx}/static/image/kv_Contact-us.png" width="100%" />
    </div>
    <!-- pic over -->
    
    <div class="dbg" style="background-color:#fafafa; padding-bottom:30px;">
		<div id="warp">
        <!-- order --> 
        	<div class="order" style="padding-bottom:80px;">
            	<div class="order_tit" style="margin-top:0px;">联系我们</div>
                <table width="920" border="0" cellspacing="0" cellpadding="10" align="center">
                                  <tr>
                                    <td>热线电话：<span style="color: #2396f3;font-size: 18px;font-weight: bold;">021-65518079</span></td>
                                  </tr>
                                  <tr>
                                    <td>业务/市场专线：021-65480880</td>
                                  </tr>
                                </table>
                
				
            </div>
			
        <!-- order over -->
  		</div>
	</div>
    
    <!-- footer over -->
    	<%@ include file="bottom.jsp" %> 
    <!-- footer over -->
</body>
</html>
