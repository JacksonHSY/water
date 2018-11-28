<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>关于我们</title>
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
                    <li class="active" onclick="nTabs(this,1);"><a href="${ctx}/index/aboutus" class="font_b">关于我们</a></li>
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
    	<img src="${ctx}/static/image/kv_About-us.png" width="100%" />
    </div>
    <!-- pic over -->
    
    <div class="dbg" style="background-color:#fafafa; padding-bottom:30px;">
		<div id="warp">
        <!-- order --> 
        	<div class="order" style="padding-bottom:80px;">
            	<div class="order_tit" style="margin-top:0px; line-height:200%;">关于我们</div>
                <table width="920" border="0" cellspacing="0" cellpadding="10" align="center">
                                  <tr>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上海水域环境发展有限公司成立于2005年4月，注册资金4600万元。隶属于上海城投（集团）有限公司下属的上海环境实业有限公司。现有职工约500人，党员82人。公司是上海市“文明单位”，已通过质量、环境与职业安全健康管理体系认证。曾荣获国家建设部评审的全国城市市容环卫系统十佳标兵，并被授予全国环卫“时传祥”奖、全国“安康杯”竞赛优胜单位、上海市五一劳动奖状、上海市工人先锋号、上海市平安单位、上海世博工作先进集体等称号。
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-weight:bold">组织构架：</span>公司下辖中芬合资上海东安水上污染防治中心有限公司、收运服务分公司、保洁服务分公司3家子分公司，以及闵奉保洁、青浦和综合开发3家项目公司。
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-weight:bold">经营规模：</span>公司总资产1.27亿元人民币。有适应沿海、长江、内河航区各类船舶近100艘，停泊基地42处。是上海市水域环卫作业一级资质的国有企业，是上海港船舶废弃物收集、水域保洁、河道疏浚及防污清污领域特许经营的公共服务型国有骨干企业，履行保障城市公共运营安全重要职责。公司作业服务区域覆盖上海内河水系、苏州河、黄浦江、长江上海段、崇明三岛水域以及杭州湾北岸、洋山深水港区、绿华山锚地等沿海水域，已实现上海港水域全覆盖。
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-weight:bold">目标定位：</span>公司是政府主管下的一支“政府子弟兵”队伍，是上海乃至国内水上环境保障和应急处置行业的领军企业。公司秉承“诚信服务铸品牌，开拓创新谋发展”的企业精神，围绕上海国际航运中心和自贸区建设，以水域环境综合运营服务为主业，通过做大城市水域环境保洁、做强东安水上多种应急抢险处置、做精水上船废收运服务、做优政府重大活动突击保障，立足上海，巩固上海港水域综合保障的领先地位，努力建成国内领先、具有一定国际知名度、保障城市水域公共运营安全的公益性现代服务型企业。
                                    </td>
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
