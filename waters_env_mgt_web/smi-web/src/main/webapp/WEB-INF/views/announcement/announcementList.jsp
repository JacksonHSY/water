<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
<head>
<title>公告列表</title>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
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
        	<div class="order" style="padding-bottom:20px;">
            	<div class="order_tit" style="margin-top:0px;">公告</div>
              <ul style="margin-bottom:50px;">
                　			<c:forEach var="item" items="${msg.infos.results}" >
	                	<li>
		                	<span style="width: 800px">
		                		<a href="${ctx}/announcement/announcementDetail?id=${item.id}" target="_blank">${item.title}</a>
		                	</span>
		                	<div style="width: 200px">
		                		${item.createTimeStr}
		                	</div>
	                	</li>
　　				</c:forEach> 
 			 </ul>
				<div class="flickr">    
        		<table class="page" cellpadding="0" cellspacing="5">
	                   <div id="pageNav" class="scott" align="center">
	                         <tr>   
		                      	 <div class="page-link" >
		                      	     <td align="center">
										<font color="#60c1e8">共${totalCount} 条数据,共${totalPage} 页${pageNav}</font>
									  </td>
								 </div>
							  </tr>
						</div>	
     
            	</table>
            </div>
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


