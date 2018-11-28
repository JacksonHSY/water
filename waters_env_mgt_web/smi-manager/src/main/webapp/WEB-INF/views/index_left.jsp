<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<c:set var="namespacePath" value="${ctx}" />
<script type="text/javascript">
function executeMenuURL(node){
	var url =  node.attributes.url;
	var text = node.text;
	if(url!=null&&url!=""){
		if(url.indexOf("?") != -1){
			url += "&random=" + parseInt(100000*Math.random());
		}else{
			url += "?random=" + parseInt(100000*Math.random());
		}
		createTabsIframe('tabs',text,'${namespacePath}'+url);
	}
}
</script>
<div class="easyui-accordion" fit="true" border="false" style="background: url(${ctx}/static/js/jquery-easyui/ui/images/left_bg.jpg)">
	<c:forEach var="menu" items="${menus}">
		<div title="${menu.FName}" headerCls="accordion-font-color" iconCls="icon-accordion-node" split="true">
			<ul id="lt${menu.id}"></ul>
			<script type="text/javascript">
				$(function(){ 
					$('#lt${menu.id}').tree({
						url: '${ctx}/admin/showLeftSubMenus?parentNode=${menu.id}',
						onClick:function(node){
							clearDom();
							//document.getElementById("main").innerHTML="";
						  	executeMenuURL(node);	
						},
						onBeforeLoad:function(){//此事件是为了使当鼠标移到节点上时的背景颜色足够长
							$(this).width($(this).parent().get(0).scrollWidth);
						},
						onLoadSuccess:function(node,data){ 
							/* alert('${menu.id}' );
							//validationRuleFlag == "1" &&
							if( '${menu.id}' == '3'){
								var validationRuleNode = $('#lt3').tree('find','1');
								if(validationRuleNode){
									$('#lt3').tree("collapseAll");	
									executeMenuURL(validationRuleNode);						
									$('#lt3').tree('select',validationRuleNode.target);
								}
							} */
						}
					});
				});
			</script>
		</div>
	</c:forEach>
</div>
