package com.ymkj.smi.manager.web.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.WorkShip;
import com.ymkj.smi.manager.common.untils.CreateExcelUtil;
import com.ymkj.smi.manager.common.untils.FileUtil;
import com.ymkj.smi.manager.common.vo.ExcelBo;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.WorkShipService;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;

import lombok.extern.log4j.Log4j;

/**
 * @Description：作业船Controller
 * @ClassName: WorkShipController.java
 * @Author：tianx
 * @Date：2017年7月27日
 * -----------------变更历史-----------------
 * 如：who  2017年7月27日  修改xx功能
 */
@Log4j
@Controller
@RequestMapping("/admin/workShip")
public class WorkShipController  {
	
	@Autowired
	private WorkShipService workShipService;
	
	/**
	 * 功能描述：引导页
	 * 输入参数：
	 * @return
	 * 返回类型：ModelAndView
	 * 创建人：tianx
	 * 日期：2017年7月31日
	 */
	@RequestMapping("/index")
	public ModelAndView applyFileView(){
		ModelAndView modelAndView = new ModelAndView("busi/work_ship_list");
		return modelAndView;
	}
	
	/**
	 * 功能描述：获取分页数据
	 * 输入参数：
	 * @param workShip
	 * @param request
	 * @return
	 * 返回类型：ResultVo
	 * 创建人：tianx
	 * 日期：2017年7月31日
	 */
	@RequestMapping("/getWorkShipPage")
	@ResponseBody 
	public ResultVo getWorkShipPage(WorkShip workShip,Integer  page,Integer  rows,HttpServletRequest request){
		PageInfo pageInfo = new PageInfo();
		pageInfo.setQueryParam(workShip);
		pageInfo.setPageNo(page);
		pageInfo.setPageSize(rows);
		pageInfo = workShipService.queryWorkShipPage(pageInfo);
		return ResultVo.returnPage(pageInfo);
	}
	
	/**
	 * 功能描述：查询信息
	 * 输入参数：
	 * @param id
	 * @param request
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年7月31日
	 */
	@RequestMapping("/getCaptainGroupList")
	@ResponseBody 
	public List<WorkShip> getCaptainGroupList(HttpServletRequest request){
		List<WorkShip> records = workShipService.getCaptainGroup();
		return records;
	}
	
	/**
	 * 功能描述：新增、修改
	 * 输入参数：
	 * @param workShip
	 * @param request
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年7月31日
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody 
	public Response saveWorkShip(WorkShip workShip,HttpServletRequest request){
		Response response = Response.success();
		try {
			if(null == workShip.getId()){
				if(StringUtils.isBlank(workShip.getName())){
					response.setCode(Constants.CODE_FAILURE);
					response.setMsg("新增失败,船名为空!");
					return response;
				}
				if(StringUtils.isBlank(workShip.getShipNature())){
					response.setCode(Constants.CODE_FAILURE);
					response.setMsg("新增失败,船舶性质为空!");
					return response;
				}
				workShipService.insert(workShip);
			}else{
				WorkShip query = new WorkShip();
				query.setId(workShip.getId());
				WorkShip update = workShipService.selectOne(query);
				if(null == update){
					response.setCode(Constants.CODE_FAILURE);
					response.setMsg("修改失败,记录不存在!");
					return response;
				}
				
				update.setShipNature(workShip.getShipNature());
				update.setCaptain(workShip.getCaptain());
				update.setName(workShip.getName());
				workShipService.updateByPrimaryKey(update);
			}
		} catch (Exception e) {
			response.setCode(Constants.CODE_FAILURE);
			response.setMsg("修改失败,系统异常请稍后再试!");
			return response;
		}
		return response;
	}
	
	/**
	 * 功能描述：注销
	 * 输入参数：
	 * @param workShip
	 * @param request
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年7月31日
	 */
	@RequestMapping("/cancel")
	@ResponseBody 
	public Response cancel(WorkShip workShip,HttpServletRequest request){
		Response response = Response.success();
		try {
			if(null == workShip.getId()){
				response.setCode(Constants.CODE_FAILURE);
				response.setMsg("注销失败,未选中记录!");
				return response;
			}
			WorkShip query = new WorkShip();
			query.setId(workShip.getId());
			WorkShip update = workShipService.selectOne(query);
			if(null == update){
				response.setCode(Constants.CODE_FAILURE);
				response.setMsg("修改失败,记录不存在或已注销!");
				return response;
			}
			update.setStatus(Constants.DATA_UNVALID);
			workShipService.updateByPrimaryKey(update);
		} catch (Exception e) {
			response.setCode(Constants.CODE_FAILURE);
			response.setMsg("修改失败,系统异常请稍后再试!");
			return response;
		}
		return response;
	}
	
	/**
	 * 功能描述：excel文件下载
	 * 输入参数：
	 * @param workShip
	 * @param resp
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年8月7日
	 */
	@RequestMapping(value="/workShipXlsDownload",method = RequestMethod.GET)
	@ResponseBody 
	public Response workShipXlsDownload(WorkShip workShip,HttpServletResponse resp){
		Response response = Response.success();
		try {
			List<WorkShip> list = workShipService.queryWorkShipByExample(workShip);
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String temp = sf.format(new Date()); 
	        
			String[] headArr = new String[]{"船名","船舶性质","船长"};
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(workShip.getName())){
				map.put("船名", workShip.getName());
			}
			ExcelBo bo = new ExcelBo();
			bo.setFileName("作业船信息_"+temp);
			bo.setTitle("作业船信息");
			bo.setParams(map);
			bo.setSubTitle("作业船信息");
			bo.setThead(headArr);
			bo.setData(transfer(list));
			SXSSFWorkbook workbook = CreateExcelUtil.createExcel(bo);
			FileUtil.downloadXls(bo.getFileName(), workbook, resp);
		} catch (Exception e) {
			log.error("下载excel失败！",e);
			response.setCode(Constants.CODE_FAILURE);
			response.setMsg("excel下载失败!");
		}
		return response;
	}
	
	/**
	 * 功能描述：封装excel数据
	 * 输入参数：
	 * @param list
	 * @return
	 * 返回类型：List<Map<Integer,Object>>
	 * 创建人：tianx
	 * 日期：2017年8月7日
	 */
	public List<Map<Integer, Object>> transfer(List<WorkShip> list){
		List<Map<Integer, Object>> data = new ArrayList<Map<Integer, Object>>();
		if(null != list && list.size()>0){
			for(WorkShip ws : list){
				Map<Integer, Object> map = new HashMap<Integer, Object>();
				map.put(0, ws.getName());
				map.put(1, Constants.WORK_SHIP_NATURE.get(ws.getShipNature()));
				map.put(2, ws.getCaptain());
				data.add(map);
			}
		}
		return data;
	}
}