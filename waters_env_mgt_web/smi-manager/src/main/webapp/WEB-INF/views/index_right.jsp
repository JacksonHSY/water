<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>

<style type="text/css">
.m-style .panel-header {
	background:#f8f9fb;
	border:1px solid #ccd6e0;
	color:#505050;
}

.m-style-table-title{
	background-color: #f3f3f3;
	color:#4b66d3;
}

.m-style .m_ul {
	margin: 10px 0;
}

.m-style .m_ul li {
	height: 30px;
	line-height: 30px;
	margin: 0 15px;
	padding: 0 10px;
	border-bottom: 1px dashed #ccc;
}

.m-style .m_ul2 li {
	margin: 0 25px;
}

.m-style .m_ul li.m_li {
	margin: 15px 15px 5px;
	background: #f3f3f3 url(${ctx}/static/images/lamp.png) 8px center no-repeat;
	border: 0px solid #E4E4E4;
	color: #2e83c4;
	padding: 0 30px;
	font-size: 14px;
}
.m-style .m_ul li.m_li a:link {
	COLOR: #4b66d3; TEXT-DECORATION: none;
}
.m-style .m_ul li.m_li a:visited {
	COLOR: #4b66d3; TEXT-DECORATION: none;
}
.m-style .m_ul li.m_li a:active {
	COLOR: #4b66d3; TEXT-DECORATION: none;
}
.m-style .m_ul li.m_li a:hover {
	COLOR: #4b66d3;TEXT-DECORATION: underline;
}

.m-style .m_ul li span {
	float: right;
}

.m-style .m_ul_img {
	overflow: hidden;
	margin-top: 30px;
	border-bottom: 1px dashed #ccc;
}

.m-style .m_ul_img li {
	float: left;
	height: 80px;
	text-align: center;
	width: 88px;
}

.m-style .m_ul_img li img {
	height: 50px;
	width: 50px;
}

.m-style .m_p {
	height: 30px;
	line-height: 30px;
	width: 230px;
	margin: 0 auto;
	text-align: center;
}
.window-header .panel-title{
	color:#505050;
}
.a-ca-head-title {
	border-right: dotted 1px #dddddd;
	padding: 0 !important;
	width: 8%;
}
table {
	border-collapse: separate;
	border-spacing: 0;
}
table th {
	padding: 5px;
	font-weight: normal;
}
table td {
	padding: 5px;
}
.a-ca-t-week th{
	color: white;
	font-weight: normal;
}
.a-ca-t-worker {
	color: white;
}

.a-ca-table {
	border: none;
	width: 100%;
	/*margin: 10px;*/
}
.a-cat-td-content {
	text-align: left;
	border-right: dotted 1px #dddddd;
	border-bottom: dotted 1px #dddddd;
}
.a-cat-td-rank{
	text-align: center;
	border-left: dotted 1px #dddddd;
	border-right: dotted 1px #dddddd;
	border-bottom: dotted 1px #dddddd;
}
.a-ca-ht-div {
	background-image: url(${ctx}/static/images/xiexian.png);
	background-size: 100% 100%;
}
.rank_type {
	border-bottom: dotted 1px #dddddd;
	color:#4b66d3;
	font-weight: normal;
}
.a-ca-t-week th {
	color: #4b66d3;
	border-right: dotted 1px #dddddd;
	border-bottom: dotted 1px #dddddd;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#pp').portal({
			border : false,
			fit : false
		});
		//add();
		//initPage();
	});

	function initPage() {
		
		//<c:forEach items="${busDesktopToolbar}" var="toolbar">
		$.ajax({
			url : getContextPath() + '/${toolbar.address}',
			type : 'post',
			dataType : 'html',
			timeout : 15000,
			error : function() {
			},
			success : function(html) {
				var code = "#" + '${toolbar.code}';
				$(code).html("");
				$(code).html(html);
			}
		});
		//</c:forEach>
	}
	function remove() {
		$('#pp').portal('remove', $('#pgrid'));
		$('#pp').portal('resize');
	}
	
	function openAnn(annId){
		var url = "${ctx }/sys/announcement/show?id="+annId;
		showCommonDialog("showAnn",url,"查看公告",800,500);
	}
	
	function manageTools(){
		var url = "${ctx}/desktopToolbox/manageTools";
		showCommonDialog("ManageTools",url,"快捷方式管理",300,500);
     	//tabRefresh('tabs','我的桌面');
	}
</script>

<div region="north" class="title" border="false" style="height: 0px;">
</div>
<div region="center" border="false" style="height: 100%; overflow-x:hidden; overflow-y:auto;">
	<div class="m-style">
		<div id="pp" style="position: relative;overflow: hidden;">
			
			
		</div>
	</div>
</div>