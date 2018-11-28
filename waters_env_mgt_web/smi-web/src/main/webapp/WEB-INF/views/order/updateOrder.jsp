<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>修改订单</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/cupertino/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/default/tooltip.css" />
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/date/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.tooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

$(document).ready(function(){ 
	$("#memo").val("${msg.infos.memo}");
 }); 
 
$(function(){
	var ifType = ${msg.infos.ifType};
	if(ifType==0){
		
		$("input[name='ifType'][value=0]").attr("checked",true); 
	}else{
		$("input[name='ifType'][value=1]").attr("checked",true);
	}
})
function add(id){
	var num = parseFloat($(id).val());
	$(id).val(parseFloat(num+0.5));
}
function sub(id){
	var num = parseFloat($(id).val());
	if(num<=0){
		$(id).val(0);
	}else{
		$(id).val(parseFloat(num-0.5));
	}
}
$(function(){
	  $('#fm input').each(function () {
        if ($(this).attr('validtype'))
            $(this).validatebox();
    })
});
//扩展easyui表单的验证  
$.extend($.fn.validatebox.defaults.rules, {  
	  intOrFloat: {// 验证整数或小数
        validator: function (value) {
            return /^\d+(\.\d{1})?$/i.test(value);
        },
        message: '请输入数字，并确保格式正确'
    }
}) 

function saveOrder() {
	//this.disabled = true;
	var workDate = $('#workDate').val(); 
	var eLife = $('#eLife').val();
	var eWater = $('#eWater').val();
	var eSweeping = $('#eSweeping').val();
	var eFood = $('#eFood').val();
	var eBurnCinder = $('#eBurnCinder').val();
	var ePlastic = $('#ePlastic').val();
	var eGungo = $('#eGungo').val();
	 var ifType=$('input:radio[name="ifType"]:checked').val();
	$('#fm').form('submit', {
		url : '${ctx}/orders/updateOrders',
		onSubmit : function() {
			if( workDate ==''){
				$.messager.alert('温馨提示','作业时间为必填!','info');
				return false;
			}
			if( ifType == null){
				$.messager.alert('温馨提示','是否已分类为必填!','info');
				return false;
			}
			if( ifType == '0'&&eLife == '0'&&eWater == '0'&&eSweeping == '0'&&eFood == '0'&&eBurnCinder == '0'&&ePlastic == '0'&&eGungo == '0'){
				$.messager.alert('温馨提示','垃圾总量不能为0!','info');
				return false;
			}
			$('#sbm').attr('disabled','disabled');
			return $(this).form('validate');
		
			/* if(!$('#yes').is(':checked') && !$('#no').is(':checked')){
				$.messager.alert('温馨提示','是否已分类为必填!','info');
				return false;
			} */
		},
		success : function(result) {
			var result = eval('('+result+')');
			if (result.code=='0000'){
				 window.location = encodeURI('${ctx}/index');
					
			} else {
				$.messager.show({
					title: '提示',
					msg: '系统异常'
				});
			}
		}
	});

   
}

</script>
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
    			<form id="fm" method="post">
                <table width="100%" border="0" cellspacing="0" cellpadding="5">
                	<tr>
                		<td align="left"><input id="taskCode" name="taskCode" value="${msg.infos.taskCode}" class="easyui-validatebox" hidden="hidden"></td>
                	</tr>
                  <tr>
                    <td width="18%" align="right">船名：</td>
                    <td width="28%" align="left">${msg.infos.customerShipName}</td>
                    <td width="18%" align="right"></td>
                    <td align="left"></td>
                  </tr>
                  <tr>
                    <td align="right"><span class="red">*</span> 作业地点：</td>
                    <td align="left">
                    <input type="text" name="adress" value="${msg.infos.adress}" readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:1px "/>
                    </td>
                    <td align="right"><span class="red">*</span> 作业时间：</td>
                    <td align="left">
                	 <input required="required" id="workDate" name="workDate" type="text" value="${msg.infos.workDate}" class="query Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d {%H+2}'})"/>
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
                    <td width="28%" align="left"><input name="ifType" type="radio" value="0" /> 是&nbsp;&nbsp;&nbsp;&nbsp;<input name="ifType" type="radio" value="1" /> 否</td>
                    <td width="18%" align="right"></td>
                    <td align="left"></td>
                  </tr>
                  <tr>
                    <td align="right">A.生活垃圾（立方）：</td>
                    <td align="left">
                    <input type="button" class="login_btn10" value="-" onclick="sub(eLife)"/><input id="eLife" name="eLife" validtype='intOrFloat'  invalidMessage="只能是整数或精确度为一位的小数" onkeyup="value=value.replace(/[^\d.]/g,'')" class="style_input_s" type="text" value="${msg.infos.eLife}" style="ime-mode:disabled;" onpaste="return false;"  /><input type="button" class="login_btn11" value="+" onclick="add(eLife)"/>
                    </td>
                    <td align="right">B.生活污水（立方）：</td>
                    <td align="left">
                    <input type="button" class="login_btn10" value="-" onclick="sub(eWater)"/><input id="eWater" name="eWater" validtype='intOrFloat'  invalidMessage="只能是整数或精确度为一位的小数" onkeyup="value=value.replace(/[^\d.]/g,'')" class="style_input_s" type="text" value="${msg.infos.eWater}" style="ime-mode:disabled;" onpaste="return false;" /><input type="button" class="login_btn11" value="+" onclick="add(eWater)"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">C.扫舱垃圾（立方）：</td>
                    <td align="left">
                    <input type="button" class="login_btn10" value="-" onclick="sub(eSweeping)"/><input id="eSweeping" name="eSweeping" validtype='intOrFloat'  invalidMessage="只能是整数或精确度为一位的小数" onkeyup="value=value.replace(/[^\d.]/g,'')" class="style_input_s" type="text" value="${msg.infos.eSweeping}" style="ime-mode:disabled;" onpaste="return false;"  /><input type="button" class="login_btn11" value="+" onclick="add(eSweeping)"/>
                    </td>
                    <td align="right" style="white-space: nowrap;">D.食品废弃物（立方）：</td>
                    <td align="left">
                    <input type="button" class="login_btn10" value="-" onclick="sub(eFood)"/><input id="eFood" name="eFood" validtype='intOrFloat'  invalidMessage="只能是整数或精确度为一位的小数" onkeyup="value=value.replace(/[^\d.]/g,'')" class="style_input_s" type="text" value="${msg.infos.eFood}" style="ime-mode:disabled;" onpaste="return false;"  /><input type="button" class="login_btn11" value="+" onclick="add(eFood)"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">E.焚烧炉灰渣（立方）：</td>
                    <td align="left">
                    <input type="button" class="login_btn10" value="-" onclick="sub(eBurnCinder)"/><input id="eBurnCinder" name="eBurnCinder" validtype='intOrFloat'  invalidMessage="只能是整数或精确度为一位的小数" onkeyup="value=value.replace(/[^\d.]/g,'')" class="style_input_s" type="text" value="${msg.infos.eBurnCinder}" style="ime-mode:disabled;" onpaste="return false;" /><input type="button" class="login_btn11" value="+" onclick="add(eBurnCinder)"/>
                    </td>
                    <td align="right">F.塑料（立方）：</td>
                    <td align="left">
                   <input type="button" class="login_btn10" value="-" onclick="sub(ePlastic)"/><input id="ePlastic" name="ePlastic" validtype='intOrFloat'  invalidMessage="只能是整数或精确度为一位的小数" onkeyup="value=value.replace(/[^\d.]/g,'')" class="style_input_s" type="text" value="${msg.infos.ePlastic}" style="ime-mode:disabled;" onpaste="return false;" /><input type="button" class="login_btn11" value="+" onclick="add(ePlastic)"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">G.油污量（立方）：</td>
                    <td align="left">
                    <input type="button" class="login_btn10" value="-" onclick="sub(eGungo)"/><input id="eGungo" name="eGungo" validtype='intOrFloat'  invalidMessage="只能是整数或精确度为一位的小数" onkeyup="value=value.replace(/[^\d.]/g,'')" class="style_input_s" type="text" value="${msg.infos.eGungo}" style="ime-mode:disabled;" onpaste="return false;" /><input type="button" class="login_btn11" value="+" onclick="add(eGungo)"/>
                    </td>
                    
                  </tr>
                </table>
                
				<div class="order_tit"><span>订单状态</span></div>
                
                <table width="100%" border="0" cellspacing="0" cellpadding="5">
                  <tr>
                    <td class="blue_s">待确认</td>
                  </tr>
                </table>
                
				<div class="order_tit"><span>其他</span></div>
                
                <table width="100%" border="0" cellspacing="0" cellpadding="5">
                  <tr>
                    <td>
                      <textarea class="style_textarea" name="memo" id="memo" cols="" rows=""></textarea>
                    </td>
                  </tr>
                  <tr>
                    <td>
                    </td>
                  </tr>
                </table>
                <div align="right">
                    <input id="sbm" type="button" class="login_btn05_b" value="提  交" onclick="saveOrder();"/>
                    <input type="button" class="login_btn06_b" value="返  回" onclick="javascript:history.back(-1);" />
                </div>                  
            </form>                 
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
