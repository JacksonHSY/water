<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>

<html>
<head>
<c:set var="namespacePath" value="${ctx}/index" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上海城投后台管理系统</title>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/default/tooltip.css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.tooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/extension/validate.js"></script>
<link rel="stylesheet" href="${ctx}/static/styles/login.css" type="text/css" />
<script>
<%
String msg = (String)request.getAttribute("msg");
if(null==msg){
	msg="";
}
%>

$(function(){
	$("#password").hide();
	$('#password_show').focus(function(){
		$("#password").show().focus();
        $(this).hide();
	})
	$("#password").blur(function(){
		 if($(this).val()==""){
			 $('#password_show').show();  
			 $(this).hide(); 
        }
	});
})

$(function(){
	var defau = "请输入工号";
	
	$('#username').blur(function(){
		if($(this).val()==""){
			$(this).val(defau);
		}
	})
	$('#username').focus(function(){
		if($(this).val()==defau){
			$(this).val("");
		}
	})
})
function fc_01(){
	$.messager.alert('温馨提示','请致电客服进行修改，谢谢！电话：13817484102','info');
	
}
function lookclose(){
	$('#tipmessage').dialog('close');
}
function toVaild(){
	
	var val = document.getElementById("username").value;
	var valPwd = document.getElementById("password").value;
	if(val == null|| val=="请输入工号"){
		//document.getElementById("msg").innerHTML("dsad")
		$('#msg').html("请输入工号");
		//alert("请输入用户名");
		return false;
	}else if(valPwd=="请输入密码"||valPwd==null||valPwd==""){
		//alert("请输入密码");
		$('#msg').html("请输入密码");
		return false;
	}else{
		return true;
	}
}

function loadTopWindow(){   
    if (window.top!=null && window.top.document.URL!=document.URL){   
        window.top.location= document.URL; //这样就可以让登陆窗口显示在整个窗口了   
    }   
}
</script>
</head>
<body class="login" onload="loadTopWindow()">
	<div class="fc" id="fc_div">
    	<div class="fc_q"></div>
    	<div class="fc_div">
   	    <img src="${ctx}/static/images/tlp.png" width="300" />
            <div class="fc_close" onclick="fc_02()">
            <img src="${ctx}/static/images/icon_close.png" width="30" />
            </div>
        </div>
    </div>
	<div class="login_k">
       <form id="loginform" action="${ctx}/admin/auth/login" onsubmit="return toVaild()" method="POST">                
      	<table width="100%" border="0" cellspacing="20" cellpadding="0">
            <tr>
              <td class="login_error"align="left" height="25" id="msg"><%=msg %></td>
            </tr>
            <tr>
              <td align="left" style="position:relative">
              <div class="login_icon"><img src="${ctx}/static/images/icon_01.png" /></div>
              <input class="login_input" id="username" name="jno" type="text" value="请输入工号" /></td>
            </tr>
            <tr>
              <td align="left" style="position:relative">
              <div class="login_icon"><img src="${ctx}/static/images/icon_02.png" /></div>
              <input type="text" class="login_input02" value="请输入密码" id="password_show">	                
	                	<input class="login_input02" name="password" type="password" id="password"/>
             <!--  <input class="login_input02" name="password" type="password"  /> --></td>
            </tr>
            <tr>
              <td><input class="login_btnk" name="" type="submit" value="登    录"  /></td>
            </tr>
            <tr>
              <td style="padding-left:100px;"><a href="#" onclick="fc_01()" class="login_font">忘记密码？</a></td>
            </tr>
          </table>
         </form> 
	</div>

	
	<div class="login_b">
    建议使用谷歌浏览器浏览本站
    </div>
</body>
</html>