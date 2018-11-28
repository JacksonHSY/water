<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>忘记密码</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/default/tooltip.css" />
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.tooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
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
        		<form id="fm" method="post">
            	<div class="order_tit" style="margin-top:0px;">修改密码</div>
                <table width="500" border="0" cellspacing="0" cellpadding="10" align="center" style="table-layout:fixed;">
                                  <tr >
                                    <td colspan="2"><input class="login_input02" id="oldPassword" name="oldpassword" type="password"  placeholder="请输入原始密码"   /></td>
                                 	<td  style="white-space: nowrap;text-align: center;" id="tip1"></td>  
                                  </tr>
                                  <tr>
                                    <td colspan="2"><input class="login_input02" id="newPassword1" name="newPassword1" type="password" placeholder="请输入新密码"   /></td>
                                  	<td style="white-space: nowrap;text-align: center;" id="tip2"></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2"><input class="login_input02" id="newPassword2"name="password" type="password" placeholder="请再次输入"  /></td>
                                  	<td style="white-space: nowrap;text-align: center;" id="tip3"></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2" align="left" id="tip4" class="red"></td>
                                 <tr>
                                    <td colspan="0">
                                    <input  id ="btn" class="login_input14"  type="button"  value="确  认"/>
                                    </td>
                                  	<td >
                                    <input id="cancel" class="login_input15"  type="button" value="取  消" />
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
            <div class="fc_close" onclick="fc_02()">
            <img src="${ctx}/static/image/icon_close.png" width="30" />
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">

$(document).ready(function(){ 
	 $("#newPassword1").click(function(){ 
           var oldP=$("#oldPassword").val().length;
           if(oldP<1){  
               $("#tip1").html("<font color=\"red\" size=\"2\">请先输入原始密码</font>");  
               return;
           }else{
        	   $("#tip1").html("");  
           }
       }) ; 
    $("#newPassword2").click(function(){  
           var oldP=$("#oldPassword").val().length;
           var num=$("#newPassword1").val().length; 
           var tmp=$("#newPassword1").val();
           if(oldP<1){  
               $("#tip1").html("<font color=\"red\" size=\"2\">请先输入原始密码</font>");  
               return;
           }
           if(num<6){  
               $("#tip2").html("<font color=\"red\" size=\"2\">请输入超过6位的密码</font>");   
               return;
          }else if(num>18){  
               $("#tip2").html("<font color=\"red\" size=\"2\">请输入小于18位的密码</font>");  
               return;
          }else{  
              $("#tip2").html("<font color=\"green\" size=\"2\"></font>");                  
          }   
       }); 
	 $("#btn").click(function(){  
         var flag=true;  
         var old=$("#oldPassword").val();  
         var pass1=$("#newPassword1").val();  
         var pass2=$("#newPassword2").val(); 
         var oldP1=$("#oldPassword").val().length;
         var oldP2=$("#newPassword1").val().length;
         var oldP3=$("#newPassword2").val().length;
         if(oldP1<1){  
             $("#tip1").html("<font color=\"red\" size=\"2\">请先输入原始密码</font>");  
             return;
         }else{
      	   $("#tip1").html("");  
         }
         if(oldP2<1){  
             $("#tip2").html("<font color=\"red\" size=\"2\">新密码不能为空</font>");  
             return;
         }else{
      	   $("#tip2").html("");  
         }
         if($("#newPassword2").val()!=pass1){  
             $("#tip3").html("<font color=\"red\" size=\"2\">两次密码不一致</font>");                   
         }  
       /*   if(oldP<1){  
             $("#tip1").html("<font color=\"red\" size=\"2\">请先输入原始密码</font>");  
             return;
         }else{
      	   $("#tip1").html("");  
         } */
         if(pass1!=pass2){  
             flag=false;  
         }  
         else{   
             flag=true;  
         }  
         if(flag){   
        		$('#fm').form('submit', {
        			url:"${ctx}/auth/modifyPassword", 
        			onSubmit : function() {
        			},
        			success : function(result) {
        				var result = eval('('+result+')');
        				if (result.data.status!=0){
        					$.messager.show({
        						title: '提示',
        						msg: result.data.respDesc
        					});
        				} else {
        					 window.location = encodeURI('${ctx}/auth/login');
        				}
        			}
        		});
         } 
     else{  
         $("#tip4").show().html("<font color=\"red\" size=\"3\"> 请按照提示!</font>");  
     }  
     }); 
	 $("#cancel").click(function(){  
		 window.location = encodeURI('${ctx}/index');
     }); 
}); 
</script>
</html>


