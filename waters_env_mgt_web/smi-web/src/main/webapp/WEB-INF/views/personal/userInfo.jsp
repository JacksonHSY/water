<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>个人信息</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$("table[id^='showUnit']").hide();
	$('#ship').click(function() {
		$("table[id^='showUnit']").hide();
		$("table[id^='showShip']").show();
	});
	$('#unit').click(function() {
		$("table[id^='showUnit']").show();
		$("table[id^='showShip']").hide();
	});
}); 
</script>
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
            	<div class="order_tit" style="margin-top:0px;">个人信息</div>
                <table width="520" border="0" cellspacing="0" cellpadding="10" align="center" style="table-layout:fixed;">
                					<tr>
                                    <td align="right" width="100">联系人姓名：</td>
                                    <td><input class="login_input" name="" type="text" readonly="readonly" value="${msg.infos.cusName}"/></td>
                                   </tr>
                                    <tr>
										<td align="right">客户性质：</td>
											<td align="left">
			            						<input name="ship" type="radio" value="male"  id="ship" checked="checked" />船只&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                                    <input name="ship" type="radio" value="female"  id="unit"/> 单位
			                             </td>
			                        </tr>
			    </table>
                					<c:forEach var="shipList" items="${msg.infos.shipList }" varStatus="s">
                						<c:choose>
                							<c:when test="${shipList.sNature==3}">
                								<table id="showUnit_'+s.index+'"  width="520" border="0" cellspacing="0" cellpadding="10"  align="center"  >
				                                  <tr>
				                                    <td align="right" width="100">单位名称：</td>
				                                    <td><input class="login_input" name="" type="text" readonly="readonly" value="${shipList.sName}" /></td>
				                                  </tr>
				                                  <tr>
				                                    <td align="right">单位地址：</td>
				                                    <td><input class="login_input" name="" type="text" readonly="readonly" value="${shipList.address}"/></td>
				                                  </tr>
			                                    </table>
					                         </c:when> 
					                         <c:otherwise>
					                           <table id="showShip_'+s.index+'"  width="520" border="0" cellspacing="0" cellpadding="10" align="center" >
						                          <tr>
						                            <td align="right" width="100">所属船只：</td>
						                            <td>
						                            			<input class="login_input" name="" type="text" readonly="readonly"  style="width:140px;" value="${shipList.sName}" >
						                            	<c:choose>
						                            		<c:when test="${shipList.sNature==1}">
						                            			<input class="" name="" style="width:130px; padding:9px;" readonly="readonly"  value="邮轮" >
						                            		</c:when>
						                            		<c:otherwise>
						                            			<input class="" name="" style="width:130px; padding:9px;" readonly="readonly"   value="货轮" >
						                           			</c:otherwise>
						                           		</c:choose>
						                            </td>
						                          </tr>
					                          </table>
			                                 </c:otherwise>
			                        	</c:choose>
                                  </c:forEach>
                <table width="520" border="0" cellspacing="0" cellpadding="10" align="center" style="table-layout:fixed;">
                                  <tr>
                                    <td width="30%">
                                    	<input class="login_input16" style="float:right" name="" type="button" onclick="javascript:top.location='${ctx}/index/userInfoEdit';" value="编  辑" />
                                    </td>
                                    <td width="70%">
                                    	<input   class="login_input15" style="float:left" name="" type="button" onclick="javascript:top.location='${ctx}/index';" value="返回首页" />
                                    </td>
                                  </tr>
                </table>
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


