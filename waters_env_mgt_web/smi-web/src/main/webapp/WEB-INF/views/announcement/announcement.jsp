<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<link href="${ctx}/static/css/style.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="${ctx}/static/js/utils/commonutil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/shipWeb.js"></script>
   <div class="fengcai_tit"><span>公告</span><div><a href="${ctx}/announcement/announcementList">更多 ></a></div></div>
				<div class="fengcai_k">
                	<ul>
						<c:forEach var="item" items="${msg.infos.results}">
	                    	<li class="fengcai_02">
	                        	<table width="100%" border="0" cellspacing="0" cellpadding="5">
	                              <tr>
	                                <td><a href="${ctx}/announcement/announcementDetail?id=${item.id}" target="_blank">${item.title}</a></td>
	                                <td width="35" align="right">
	                              	  <span >${item.createTimeStr}</span><br/>
	                                </td>
	                              </tr>
	                            </table>
	                        </li>
                    	</c:forEach> 
                    </ul>
                </div>
    
        
        