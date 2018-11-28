<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>填写个人信息</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.tooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/default/tooltip.css" />
<script type="text/javascript">
var count = 50 ;
var ids = [];
//取消单位名称新增
function delUnit(obj,id){
  	/* if($("#password").val().length<1){  
    	 $.messager.show({
				title: '提示',
				msg: '请输入登录密码'
			});
         return;
     } */
	ids.push(id);
	$(obj).parent().parent().next('tr').remove(); 
    $(obj).parent().parent().remove();
}
function delShip(index,id){
	 /*  if($("#password").val().length<1){  
    	 $.messager.show({
				title: '提示',
				msg: '请输入登录密码'
			});
         return;
     } */
	ids.push(id);
	$(index).parent().parent().remove();
}
$(function(){
	$('#cz_add_01').click(function(){
		 /*  if($("#password").val().length<1){  
		    	 $.messager.show({
						title: '提示',
						msg: '请输入登录密码'
					});
		         return;
		   } */
		if($("input[name='ship'][value='0']").is(":checked")==true){ //船
			 var row = $("table tr").length-5;
			 var trHTML = "<tr id='ship_"+count+"'>";
			 trHTML += "<td align='right'>所属船只：</td>";
			 trHTML += "<td><input class='login_input'id='ship_"+count+"' name='shipList["+count+"].name' type='text' required='required' style='width:140px;' /> ";
			 trHTML += "<select name='shipList["+count+"].nature' id='shipnature_"+count+"' class='login_input' style='width:130px; padding:9px;'>";
			 trHTML += "<option value='2'>货轮</option><option value='1'>邮轮</option></select>";
			 trHTML += "&nbsp<img src='${ctx}/static/image/icon_j.png' onclick='delShip(this)'  style='width:32' align='center'/></td>";
			 trHTML += "</tr>";
			 $("#table tr:eq("+row+")").after(trHTML); 
			 count++;	
		}
		if($("input[name='ship'][value='1']").is(":checked")==true){ //单位
			var row = $("table tr").length-5;
			 var trHTML = "<tr id='unit_"+count+"'>";
			 trHTML += "<td align='right'>单位名称：</td>";
			 trHTML += "<td><input class='login_input'id='sName_"+count+"' name='shipList["+count+"].name' required='required' type='text' />";
			 trHTML += "&nbsp<img src='${ctx}/static/image/icon_j.png' onclick='delUnit(this)'  style='width:32' align='center'/></td>";
			 trHTML += "</tr><tr id='unit_ads_"+count+"'>";
			 trHTML += "<td align='right'>单位地址：</td>";
			 trHTML += "<td><input class='login_input' id='address_"+count+"' name='shipList["+count+"].address' required='required' type='text' /></td>";
			 trHTML += "<td><input class='login_input' id='nature_"+count+"' name='shipList["+count+"].nature'  type='hidden' value='3'/></td>";
			 trHTML += "</tr>";
			 $("#table tr:eq("+row+")").after(trHTML); 
			 count++;
		}
	});
	
	$('#userupdateEdit').click(function(){
	  /*  if($("#password").val().length<1){  
        	 $.messager.show({
					title: '提示',
					msg: '请输入登录密码'
				});
             return;
         } */
		$('#fm').form('submit', {
			 url:"${ctx}/index/userupdateEdit?ids="+ids,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('('+result+')'); 
				if (result.code=='0'){
					 window.location = encodeURI('${ctx}/index/userInfo');
				} else {
				 	$.messager.alert("操作提示", result.msg, "info", function () {
				 		location.reload();
			        });
				} 
			
			}
		});
	});
})
$(function(){
	  $(":radio").click(function(){
		  if($("input[name='ship'][value='0']").is(":checked")){ //船
			  $("tr[id^='unit']").hide();
			  $("tr[id^='ship']").show();
		  }
		  else if($("input[name='ship'][value='1']").is(":checked")){
			  $("tr[id^='unit']").show();
			  $("tr[id^='ship']").hide();
		  }
		});
});
$(function(){
	$("input[name='ship'][value='0']").attr("checked",true);
	$("tr[id^='unit']").hide();
});
$(function(){
	  $('#fm input').each(function () {
        if ($(this).attr('validtype'))
            $(this).validatebox();
    })
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
            	<div class="order_tit" style="margin-top:0px;">修改个人信息</div>
            	<form id="fm" method="post" >
                <table width="520" border="0" cellspacing="0" id="table" cellpadding="10" align="center">
                                  <!-- <tr>
                                    <td align="right">请输入登录密码：</td>
                                    <td><input class="login_input" id="password"name="password" required="required"	type="password" /></td>
                                  </tr> -->
                                  <tr>
                                    <td align="right">联系人姓名：</td>
                                    <td><input class="login_input"id="cusName_" name="cusName" value="${msg.infos.cusName}" required="required" type="text" /></td>
                                  </tr>
                                  <tr>
                                    <td align="right" width="100">客户性质：</td>
                                    <td align="left"><input name="ship" id="sNature_" type="radio" value="0" /> 船只&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="ship" type="radio" value="1" /> 单位</td>
                                  </tr>
                                  <c:forEach var="shipList" items="${msg.infos.shipList}" varStatus="s">
                						<c:choose>
                							<c:when test="${shipList.sNature==3}">
			                                     <tr id="unit_'+${s.index}+'" >
			                                     	<input  name="shipList[${s.index}].id" value="${shipList.id}"  type="hidden" />
				                                    <td align="right" >单位名称：</td>
				                                    <td><input class="login_input" id="sName_" name="shipList[${s.index}].name" value="${shipList.sName}"  type="text" />
				                                    	<img src="${ctx}/static/image/icon_j.png" id="cz_del_03"  onclick="delUnit(this,${shipList.id})" width="32" align="center" />
				                                    </td>
				                                  </tr>
			                                     <tr id="unit_'+${s.index}+'"  >
				                                    <td align="right">单位地址：</td>
				                                    <td><input class="login_input" id="address_" name="shipList[${s.index}].address"  type="text" value="${shipList.address}"/></td>
				                                  </tr>	
				                                  <input name="shipList[${s.index}].nature" value="3"  type="hidden" />
					                         </c:when> 
					                         <c:otherwise>
						                          <tr id="ship_'+${s.index}+'" >
						                          <input  name="shipList[${s.index}].id" value="${shipList.id}"  type="hidden" />
						                            <td align="right" width="100">所属船只：</td>
						                            <td>
						                            	<input class="login_input" name="shipList[${s.index}].name" type="text"  style="width:140px;" value="${shipList.sName}" >
						                            	<c:choose>
						                            		<c:when test="${shipList.sNature==1}">
					                            			   <select class="" name="shipList[${s.index}].nature" id="shipnature_" class="login_input" style="width:130px; padding:9px;">
							                                      <option value="1">邮轮</option>
							                                      <option value="2">货轮</option>
							                                    </select>
						                            		</c:when>
						                            		<c:otherwise>
						                            			<select class="" name="shipList[${s.index}].nature" id="shipnature_" class="login_input" style="width:130px; padding:9px;">
							                                      <option value="2">货轮</option>
							                                      <option value="1">邮轮</option>
							                                    </select>
						                           			</c:otherwise>
						                           		</c:choose>
						                           		 <img src="${ctx}/static/image/icon_j.png" id="cz_del_04" onclick="delShip(this,${shipList.id})"  width="32" align="center" />
						                            </td>
						                          </tr>
			                                 </c:otherwise>
			                        	</c:choose>
                                  </c:forEach>
                                  <tr>
                                    <td align="right"></td>
                                    <td>
                                    <img src="${ctx}/static/image/icon_add.png" id="cz_add_01" width="32" align="center" />
                                    </td>
                                  </tr>
                                  <tr>
                                    <td colspan="2" height="30"></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2">
                                    <input class="login_input14" name="" type="button" id="userupdateEdit"  value="确  认" />
                                    <input class="login_input15" name="" type="button" onclick="javascript:top.location='userInfo';" value="取  消" />
                                    </td>
                                  </tr>
                                </table>
                                </form>
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
            <div class="fc_close" id="fc_01">
            <img src="${ctx}/static/image/icon_close.png" width="30" />
            </div>
        </div>
    </div>
    <div class="fc" id="fc_div1">
    	<div class="fc_q"></div>
    	<div class="fc_div" style="top:500px;">
   	    船名不能为空
            <div class="fc_close" id="fc_02">
            <img src="${ctx}/static/image/icon_close.png" width="30" />
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">

$(function(){
	$('#forgetPwd').click(function(){
		document.getElementById("fc_div").style.display="block";
	})
	$('#fc_02').click(function(){
		document.getElementById("fc_div1").style.display="none";
	})
})
</script>
</html>


