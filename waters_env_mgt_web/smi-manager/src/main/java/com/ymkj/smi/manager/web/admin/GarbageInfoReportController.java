package com.ymkj.smi.manager.web.admin;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.smi.manager.common.vo.GarbageReportVo;
import com.ymkj.smi.manager.service.OrdersService;

@Controller
@RequestMapping("/admin/report/garbage")
public class GarbageInfoReportController {
	
	@Autowired
	private OrdersService ordersService;
	
	/**
	 * 功能描述：引导页
	 * 输入参数：
	 * @param model
	 * @param httpSession
	 * @return
	 * 返回类型：String
	 * 创建人：tianx
	 * 日期：2017年8月3日
	 */
	@RequestMapping("")
	public String index(Model model,HttpSession httpSession) {
		model.addAttribute("circleValues", ordersService.getGarbageStatisticTotalForCircle());
		return "report/garbageInfoReport";
	}
	
	/**
	 * 功能描述：柱状图
	 * 输入参数：
	 * @param request
	 * @return
	 * 返回类型：GarbageReportVo
	 * 创建人：tianx
	 * 日期：2017年8月3日
	 */
	@RequestMapping("/getGarbageStatisticTotalForColumn")
    @ResponseBody
    public GarbageReportVo getGarbageStatisticTotalForColumn(HttpServletRequest request){
		GarbageReportVo garbageVo = new GarbageReportVo();
		
		List<String> xAxis = new ArrayList<String>();
		xAxis.add("生活垃圾");
		xAxis.add("生活污水");
		xAxis.add("食品废弃物");
		xAxis.add("塑料");
		xAxis.add("扫舱垃圾");
		xAxis.add("焚烧炉火渣");
		xAxis.add("油污");
		garbageVo.setXaxis(xAxis);
		
		garbageVo.setTitle("各类废料的年度收集量(单位：立方)");
		garbageVo.setSeries(ordersService.getGarbageStatisticTotalForColumn());
		
		return garbageVo;
    }
	
	/**
	 * 功能描述：饼图
	 * 输入参数：
	 * @param request
	 * @return
	 * 返回类型：GarbageReportVo
	 * 创建人：tianx
	 * 日期：2017年8月3日
	 */
	@RequestMapping("/getGarbageStatisticTotalForPie")
    @ResponseBody
    public GarbageReportVo getGarbageStatisticTotalForPie(HttpServletRequest request){
		GarbageReportVo garbageVo = new GarbageReportVo();
		garbageVo.setTitle("当月各类废料的收集量比重");
		garbageVo.setSeries(ordersService.getGarbageStatisticTotalForPie());
		return garbageVo;
    }
	
}
