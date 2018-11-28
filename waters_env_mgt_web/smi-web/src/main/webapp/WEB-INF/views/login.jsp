<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
<script type="text/javascript">
var msg;
if(msg != "" && msg != "null")
{
	$("msg").html(msg);
}
function authLogin() {
	$("#msg").html("&nbsp;");
		var val = document.getElementById("username").value;
		var valPwd = document.getElementById("password").value;
		var valid = document.getElementById("valid").value;
		if(val == null|| val=="请输入手机号"||val==""){
			//document.getElementById("msg").innerHTML("dsad")
			$('#msg').html("手机号不能为空");
			//alert("请输入用户名");
			return false;
		}else if(!(/^1(3|4|5|7|8)\d{9}$/.test(val))){
			   $("#msg").html("请输入正确的手机号!");
			     return false;
		}else if(valPwd=="请输入密码"||valPwd==null||valPwd==""){
			//alert("请输入密码");
			$('#msg').html("密码不能为空");
			return false;
		}
		if(valid=="请输入验证码"||valid==""){
			$('#msg').html("验证码不能为空");
			return false;
		}
		// var reg = new RegExp("^[0-9]{11}$");
		 $.ajax({  
             type: "POST",  
             url:"${ctx}/auth/doLogin",  
             data:{
            	 username:val,
            	 password:valPwd,
            	 valid:valid
             },// 序列化表单值  
             async: false,  
             error: function(request) {  
                 alert("Connection error");  
             },  
             success: function(data) {  
            	 document.getElementById('validImg').src='${ctx}/auth/verifyCodeImage?ran='+Math.random();
                 if(data.code=="9999"){
            	 $('#msg').html(data.msg);
                 }else if(data.code=="0000"&&data.data.status == "-1"){
                	 $('#msg').html(data.data.respDesc);
                 }else{
                	 window.location.href="${ctx}/index" ;
                	 
                 }
             }  
         }); 
}

//验证码刷新
$(function(){
	 $("#validImg").click(function(){
		
		document.getElementById('validImg').src='${ctx}/auth/verifyCodeImage?ran='+Math.random();
	}); 
})
</script>
</head>


<body>
    <!-- head --> 
    <%@ include file="top.jsp"%>
    <!-- head over -->
    
    <!-- pic --> 
    <div class="dbg">
    	<img src="${ctx}/static/image/dbg.jpg" width="100%" />
    </div>
    <!-- pic over -->
    
    <div class="dbg">
		<div id="warp">
        <!-- login --> 
			<div class="login">
            	<div class="nTabl">
                    <!-- nTab --> 
                    <div class="TabTitle"> 
                        <ul id="myTab3"> 
                        <li class="active" onclick="nTabs(this,0);">登录</li> 
                        <li class="normal" onclick="nTabs(this,1);">注册</li>
                        </ul> 
                    </div>
                    <!-- TabContent --> 
                    <div class="TabContent"> 
                        <div id="myTab3_Content0">
                        	<div class="login_k">
                                <table width="340" border="0" cellspacing="0" cellpadding="10" align="center">
                                  <tr>
                                    <td colspan="2"><input class="login_input01" id="username" name="username" type="text" placeholder="请输入手机号" /></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2"><input class="login_input02" id="password" name="password" type="password" placeholder="请输入密码" /></td>
                                  </tr>
                                  <tr>
                                    <td align="left"><input class="login_input03" id="valid" name="valid" type="text" placeholder="请输入验证码" /></td>
                                    <td align="left"><img src="${ctx }/auth/verifyCodeImage" height="37" id="validImg"/></td>
                                  </tr>
                                  <tr>
                                    <td align="left" id="msg" class="red"></td>
                                    <td align="right"><a id="forgetPwd" >忘记密码</a></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2"><input class="login_btn04" name="loginbutton" id="loginbutton" type="button" onclick="authLogin();" /></td>
                                  </tr>
                                </table>
                            </div>    
                        </div>
                        <div id="myTab3_Content1" class="none">
                        	<div class="login_k">
                                <table width="340" border="0" cellspacing="0" cellpadding="10" align="center">
                                  <tr>
                                    <td colspan="2"><input class="login_input01" name="username01" id="username01" type="text" value="请输入手机号" /></td>
                                  </tr>
                                    <tr>
                                    <td colspan="2"><input class="login_input02" id="password01" name="password" type="password" placeholder="请输入密码" /></td>
                                    <td style="white-space: nowrap;text-align: center;" id="tip2"></td>
                                  </tr>
                                   <tr>
                                    <td colspan="2"><input class="login_input02" id="password02" name="password" type="password" placeholder="请再次输入密码" /></td>
                                    <td style="white-space: nowrap;text-align: center;" id="tip3"></td>
                                  </tr>
                                  <tr>
                                    <td align="left"><input class="login_input12" name="valid01" id="valid01" type="text" value="请输入验证码"/></td>
                                    <td align="left"><input class="login_input13" name="sendvfc" type="button" id="sendvfc" onclick="sendVFCode(this);" value="发送验证码"/></td>
                                  </tr>
                                  <tr>
                                    <td align="left" id="msg1" class="red"></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2"><input class="login_btn04" id="authRegiest" name="" onclick="authRegiest();" type="button"  /></td>
                                  </tr>
                                </table>
                            </div>   
                        </div> 
                    </div>
                </div>				        	
        	</div>
        <!-- login over -->
        
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
	<div class="fc" id="fc_div">
    	<div class="fc_q"></div>
    	<div class="fc_div_w" style="top:800px;">
   	    请联系管理员重置密码
            <div class="fc_close" onclick="fc_02()" id="fc02">
            <img src="${ctx}/static/image/icon_close.png" width="30" />
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
//注册的信息提示
var msg1;
if(msg1 != "" && msg1 != "null")
{
	$("msg1").html(msg1);
}

//忘记密码的提示
	 $(function(){
		$('#forgetPwd').click(function(){
			//document.getElementById("fc_div").style.display="block";
			window.location.href="${ctx}/index/forgotPassword" ;
		})
		$('#fc02').click(function(){
			document.getElementById("fc_div").style.display="none";
		})
	}) 
	//回车发送提交事件
	$(function(){
		$(document).keyup(function(event){
			 if(event.keyCode ==13){
			  $("#loginbutton").trigger("click");
			 }
		});
	})
	//失焦聚焦事件
	$(function(){
	var defau = "请输入手机号";
	
	$('#username01').blur(function(){
		if($(this).val()==""){
			$(this).val(defau);
		}
	})
	$('#username01').focus(function(){
		if($(this).val()==defau){
			$(this).val("");
		}
	})
})
$(function(){
	var defau = "请输入验证码";
	
	$('#valid01').blur(function(){
		if($(this).val()==""){
			$(this).val(defau);
		}
	})
	$('#valid01').focus(function(){
		if($(this).val()==defau){
			$(this).val("");
		}
	})
   $("#password02").click(function(){  
         var num=$("#password01").val().length; 
         if($("#password01").val()=='' || num<6 || num>18 ){  
             $("#tip2").html("<font color=\"red\" size=\"2\">请输入超过6位小于18的密码</font>");   
             return;
        }else{  
            $("#tip2").html("<font color=\"green\" size=\"2\"></font>");                  
        }   
     }); 
})
//确定提交注册
function authRegiest() {
	$("#msg1").html("&nbsp;");
		var phone = $('#username01').val();
		var valid01 = $('#valid01').val();
		var password = $('#password01').val();
		if(phone == null|| phone=="请输入手机号"){
			//document.getElementById("msg").innerHTML("dsad")
			$('#msg1').html("手机号不能为空");
			//alert("请输入用户名");
			return false;
		}
		 if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
		     $("#msg1").html("请输入正确的手机号!");
		     return false;
		 }
		 var num0=$("#password01").val().length;
		 var num=$("#password02").val().length; 
		 if(num0=='' || num0<6 || num0>18 ){  
             $("#tip2").html("<font color=\"red\" size=\"2\">请输入超过6位小于18的密码</font>");   
             return;
		 }
         if($("#password02").val()=='' || num<6 || num>18 ){  
             $("#tip3").html("<font color=\"red\" size=\"2\">请输入超过6位小于18的密码</font>");   
             return;
        }else if($("#password01").val()!=$("#password02").val()){
        	  $("#tip3").html("<font color=\"red\" size=\"2\">两次密码输入不一致</font>");   
	             return;
        }else{  
            $("#tip3").html("<font color=\"green\" size=\"2\"></font>");                  
        } 
		if(valid01=="请输入验证码"||valid01==""){
			$('#msg1').html("验证码不能为空");
			return false;
		}
		 $.ajax({  
             type: "POST",  
             url:"${ctx}/auth/doRegiest",  
             data:{
            	 phone:phone,
            	 password:password,
            	 type:"0",
            	 valid01:valid01
             },// 序列化表单值  
             async: false,  
             error: function(request) {  
                 alert("Connection error");  
             },  
             success: function(data) { 
            	 
            	 if(data.code=="9999"){
                	 $('#msg1').html(data.data.respDesc);
                     }else if(data.code=="0000"){
                    	 window.location.href="${ctx}/index/userInfoEdit" ;
                     }else{
                    	 $('#msg1').html(data.data.respDesc);
                     }
             }  
         }); 
          
       
}
	//发送验证码
	function sendVFCode() {
	   var num=$("#password02").val().length; 
         if($("#password02").val()=='' || num<6 || num>18 ){  
             $("#tip3").html("<font color=\"red\" size=\"2\">请输入超过6位小于18的密码</font>");   
             return;
        }else if($("#password01").val()!=$("#password02").val()){
        	  $("#tip3").html("<font color=\"red\" size=\"2\">两次密码输入不一致</font>");   
	             return;
        }else{  
            $("#tip3").html("<font color=\"green\" size=\"2\"></font>");                  
        } 
		var f_mobile=$('#username01').val();
		if ( !(/^(13|15|18|14|17)\d{9}$/i.test(f_mobile))) {
			document.getElementById("msg1").innerHTML = '请输入正确的手机号';
			return false;
		}
		$.post('${ctx}/auth/sendvfcode', {f_mobile : f_mobile,type : "0"}, function(result) {
			
			if(result.code=="9999"){
				 $('#msg1').html(result.msg);
			}
		}, 'json');
		
		settime();
	}
	var countdown=60; 
	function settime(val) {
		var val=document.getElementById("sendvfc");
	if (countdown == 0) { 
	val.removeAttribute("disabled"); 
	
	val.value="发送验证码"; 
	countdown = 60; 
	return false;
	} else { 
		
	val.setAttribute("disabled", true); 
	val.value="重新发送(" + countdown + ")"; 
	countdown--; 
	} 
	setTimeout(function() { 
	settime(val) 
	},1000) 
	}
</script>
</html>

