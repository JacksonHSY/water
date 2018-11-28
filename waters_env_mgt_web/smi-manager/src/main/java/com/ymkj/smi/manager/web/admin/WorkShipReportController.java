package com.ymkj.smi.manager.web.admin;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.smi.manager.common.untils.DateUtil;
import com.ymkj.smi.manager.service.OrdersService;
import com.ymkj.springside.modules.utils.StrUtils;

@Controller
@RequestMapping("/admin/WorkShipReport")
public class WorkShipReportController {
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping("")
	public String index(Model model,HttpSession httpSession) {
		List<Orders> glist = ordersService.getOrdersForAmountGarbageTop(5,null);
		model.addAttribute("glist", glist);//垃圾回收排名
		List<Orders> jlist = ordersService.getOrdersForHighPraiseTop5();
		model.addAttribute("jlist", jlist);//好评排名
		
		List<Orders> oList = ordersService.getOrdersForOrdersSumTop(5,null);
		model.addAttribute("oList", oList);//出船次数排名
		return "report/WorkShipReport";
	}
	/**
	 * 
	 * @TODO 本月回收垃圾总量top5的工作船 柱状图
	 * @param request
	 * @return
	 * JSONArray
	 * @author changj@yuminsoft.com
	 * @date2017年7月31日
	 */
	@RequestMapping("/getOrdersForAmountGarbageTop")
    @ResponseBody
    public JSONObject getOrdersForAmountGarbageTop5(HttpServletRequest request){
		String workDate = request.getParameter("workDate");
		List<Orders> list = null;
		JSONObject jo = new JSONObject(); 
		if(StrUtils.isNotBlank(workDate)){
			list = ordersService.getOrdersForAmountGarbageTop(20,DateUtil.getCommStyleTimeForMonth(workDate));
			  jo.put("subTitle", workDate);  
		}else{
			 Calendar cal = Calendar.getInstance();
			 int year = cal.get(Calendar.YEAR);
			 int month = cal.get(Calendar.MONTH) + 1;
		     jo.put("subTitle", year+"-"+month);  
			 list = ordersService.getOrdersForAmountGarbageTop(20,null);
		}
		 String[] workSnames =  new String[list.size()];//
		 double[] totalAgrbage =  new double[list.size()];//
		 if(list.size()>0){
			 for(int i=0;i<list.size();i++){
				 if(list.get(i)==null||StrUtils.isEmpty(list.get(i).getAmountGarbageForShow())){
					 continue;
				 }
				 workSnames[i] = list.get(i).getWorkShipName();
				 totalAgrbage[i] = Double.parseDouble(list.get(i).getAmountGarbageForShow());
				 jo.put("workSname"+i, list.get(i).getWorkShipName());
			 }
		 }
		 jo.put("amountGarbageForShow", totalAgrbage);
         jo.put("title", "月度作业船收集统计");  
         jo.put("text", "当月收集垃圾的总量(立方)");  
         jo.put("workSnames", workSnames);  
         return jo;
    }
}
