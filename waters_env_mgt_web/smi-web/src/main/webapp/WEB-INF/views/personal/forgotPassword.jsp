<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>忘记密码</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
<script type="text/javascript">

	function findCode(){
		$("#msg").html("&nbsp;");
		var phone = document.getElementById("telephone").value;
		var vaildatecode = document.getElementById("vaildatecode").value;
		var pwd1 = document.getElementById("password1").value;
		var pwd2 = document.getElementById("password2").value;
		if(phone == null|| phone=="请输入手机号"||phone==""){
			//document.getElementById("msg").innerHTML("dsad")
			$('#msg').html("手机号不能为空");
			//alert("请输入用户名");
			return false;
		}else if(vaildatecode=="请输入验证码"||vaildatecode==null||vaildatecode==""){
			//alert("请输入密码");
			$('#msg').html("验证码不能为空");
			return false;
		}
		if(pwd1=="请输入新密码"||pwd1==""){
			$('#msg').html("请输入新密码");
			return false;
		}
		if(pwd2=="请再次输入新密码"||pwd2==""){
			$('#msg').html("请再次输入新密码");
			return false;
		}
		 if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
		     $("#msg").html("请输入正确的手机号!");
		     return false;
		 }
		 if(pwd1!=pwd2){
			 $("#msg").html("两次输入密码不一致，请重新输入");
		     return false;
		 }
		 $.ajax({  
             type: "POST",  
             url:"${ctx}/auth/forgotPwd",  
             data:{
            	 phone:phone,
            	 vaildatecode:vaildatecode,
            	 type:"1",
            	 password:pwd1
             },// 序列化表单值  
             async: false,  
             error: function(request) {  
                 alert("Connection error");  
             },  
             success: function(data) {  
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
	//回车发送提交事件
	$(function(){
		$(document).keyup(function(event){
			 if(event.keyCode ==13){
			  $("#loginbutton").trigger("click");
			 }
		});
	})
	//发送验证码
	function sendVFCode() {
		var f_mobile=$('#telephone').val();
		if ( !(/^(13|15|18|14|17)\d{9}$/i.test(f_mobile))) {
			document.getElementById("msg").innerHTML = '请输入正确的手机号';
			return false;
		}
		$.post('${ctx}/auth/sendCode', {f_mobile : f_mobile,type : "1"}, function(result) {
			if(result.code=="9999"){
				
				 $('#msg').html(result.msg);
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
            	<div class="order_tit" style="margin-top:0px;">忘记密码</div>
                <table width="320" border="0" cellspacing="0" cellpadding="10" align="center">
                                  <tr>
                                    <td colspan="2"><input class="login_input01" id="telephone" name="" type="text" placeholder="请输入手机号" /></td>
                                  </tr>
                                  <tr>
                                    <td align="right"><input class="login_input12" id="vaildatecode" name="" type="text" placeholder="请输入验证码"/></td>
                                    <td align="left"><input class="login_input13" id="sendvfc" name="" type="button" onclick="sendVFCode(this);" value="发送验证码"/></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2"><input class="login_input02" id="password1" name="password" type="password" placeholder="请输入新密码" /></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2"><input class="login_input02" id="password2" name="password" type="password" placeholder="请再次输入新密码" /></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2" align="center" id="msg" class="red"></td>
                                  </tr>
                                  <tr>
                                    <td colspan="2">
                                    <input class="login_input14" id="loginbutton" name="loginbutton" type="button" onclick="findCode()" value="确  认" />
                                    <input class="login_input15" name="" type="button" onclick="javascript:top.location='index.html';" value="取  消" />
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


