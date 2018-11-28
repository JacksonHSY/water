<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>

<html>
<head>
<title>添加权限</title>
<link rel="stylesheet" href="${ctx}/static/js/jquery-easyui/themes/cus/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/jquery-easyui/themes/default/tooltip.css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/jquery.tooltip.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-easyui/extension/common.js"></script>
<script type="text/javascript" >
$(function (){
	select_function();
	$('#saveButton').linkbutton('enable');
});
var loading;
function select_function(){
	$("#tts").tree({
		url:'${ctx}/admin/role/getRoleMenu?rolId='+${rolId},
		cascadeCheck:false,
		 onBeforeLoad:function(node, param){ 
		        loading=true; 
		    }, 
		    onLoadSuccess:function(node, data){ 
		        loading=false; 
		    }, 
		    onCheck :function (node,checked){
		    	 if(loading){ 
		    	        return; 
		    	    }else{
		    			parentHasSelectedChildren(node,checked);
		    	 }
		    }
	});
}

function parentHasSelectedChildren(node,checked){
    var _childrens = $('#'+node.id).tree('getChildren', node.target);
    for(var i=0;i<_childrens.length;i++){
			if(checked==true){
    			$(_childrens[i].target).find(".tree-checkbox").removeClass("tree-checkbox0").addClass("tree-checkbox1");
    		}
    		else {
    			$(_childrens[i].target).find(".tree-checkbox").removeClass("tree-checkbox1").addClass("tree-checkbox0");
    			$(node.target).find(".tree-checkbox").removeClass("tree-checkbox1").addClass("tree-checkbox0");
    		}
	}
    if(checked==true){
    	dealParent(node);
    }else{
    	dealParent2(node);
    }
}

function dealParent(node){
	var _parent = $('#'+node.id).tree('getParent', node.target);
	if(_parent){
		$(_parent.target).find(".tree-checkbox").removeClass("tree-checkbox0").addClass("tree-checkbox1");
		if(_parent.attributes.type!='3'){
			dealParent(_parent);
		}
	}
}

function dealParent2(node){
	if(node.attributes.type!='4' && node.attributes.name!='导出'){
		var _parent = $('#'+node.id).tree('getParent', node.target);
		if(_parent){
			 var _childrens = $('#'+node.id).tree('getChildren', _parent.target);
			 var allunchecked=true;
			    for(var i=0;i<_childrens.length;i++){
			    	var checked=_childrens[i].checked;
						if(checked==true){
							allunchecked=false;
							break;
			    		}
				}
			if(allunchecked){
				$(_parent.target).find(".tree-checkbox").removeClass("tree-checkbox1").addClass("tree-checkbox0");
				if(_parent.attributes.type!='3' && _parent.attributes.name!='导出'){
					dealParent2(_parent);
				}
			}
			
			
		}
	}
}

function funSave(){
	var functionIds = '';
	var nodes = $('#tts').tree('getChecked');
	for(var i=0;i<nodes.length;i++){
		functionIds =functionIds + nodes[i].id+';';
	}

	$("#functionIds").val(functionIds);
	var url = "${ctx}/admin/role/save_role_function";
	$('#fm').form('submit', {
		url : url,
		onSubmit : function() {				
				$('#saveButton').linkbutton({
					disabled : true
				});	
				return true;
		},
		success : function(result) {
			var result = eval('('+result+')');
			if (result.status ){
				$.messager.show({
					title: '提示',
					msg: result.data
				});
			} else {
				$.messager.show({
					title: '提示',
					msg: result.data
				});
			}
			$('#saveButton').linkbutton({
					disabled : false
			});	
		}
	});
}

</script>

</head>
<body>
	<div id="tt"  style="width:auto;height:auto;">
		<div style="padding:20px;">
			<form id="fm" method="post">
			<input name="rolId" type="hidden" id="rolId" value="${rolId}">
			<input name="functionIds" type="hidden" id="functionIds">
			<ul id="tts" class="easyui-tree" checkbox="true"></ul> 
			<div style="width:800px;text-align:right;">
				<a href="#" id="saveButton" class="easyui-linkbutton" iconCls="icon-ok" onclick="funSave()">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="closeCurPage('添加权限')">返回</a>
			</div>
			</form>
		</div>
	</div>
</body>
</html>