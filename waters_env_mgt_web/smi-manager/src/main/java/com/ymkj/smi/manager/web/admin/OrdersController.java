package com.ymkj.smi.manager.web.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.smi.manager.common.entity.WorkShip;
import com.ymkj.smi.manager.common.untils.CreateExcelUtil;
import com.ymkj.smi.manager.common.untils.DateUtil;
import com.ymkj.smi.manager.common.untils.FileUtil;
import com.ymkj.smi.manager.common.untils.NumberUtil;
import com.ymkj.smi.manager.common.vo.ExcelBo;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.mapper.OrdersMapper;
import com.ymkj.smi.manager.service.AdminUserService;
import com.ymkj.smi.manager.service.CustomerShipService;
import com.ymkj.smi.manager.service.OrdersService;
import com.ymkj.smi.manager.service.WorkShipService;
import com.ymkj.springside.modules.exception.BusinessException;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;

import lombok.extern.log4j.Log4j;
/**
* OrdersController
* <p/>
* Author: 
* Date: 2017-07-24 18:31:59
* Mail: 
*/
@Log4j
@Controller
@RequestMapping("/admin/orders")
public class OrdersController  {
	
	@Autowired
	private WorkShipService workShipService;
	
	@Autowired
	private CustomerShipService customerShipService;
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrdersService ordersService;
	@RequestMapping("")
	public String index(Model model,HttpSession httpSession) {
		return "order/orderList";
	}
	
	/**
	 * 列表显示
	 * @TODO
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param ordes
	 * @param request
	 * @return
	 * ResultVo
	 * @author changj@yuminsoft.com
	 * @date2017年7月27日
	 */
	@RequestMapping("/orderList")
	@ResponseBody
	public ResultVo orderListPage(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			Orders ordes,HttpServletRequest request) {
	    PageInfo<Orders> requestbody = new PageInfo<Orders>();
	    requestbody.setPageNo(page);
		requestbody.setPageSize(rows);
		requestbody.setQueryParam(ordes);
		requestbody = ordersService.getOrderListPage(requestbody);
		return ResultVo.returnPage(requestbody);
	}
	/**
	 * 
	 * @TODO 作业船列表
	 * @param request
	 * @return
	 * JSONArray
	 * @author changj@yuminsoft.com
	 * @date2017年7月26日
	 */
	@RequestMapping("/getWorkShipForOption")
    @ResponseBody
    public JSONArray getWorkShipForOption(HttpServletRequest request){
        try{
            List<Map<String,Object>> list = workShipService.getWorkShipForOption(true);
            if(list.size()>0){
                JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
                return jsonArray;
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
	/**
	 * 
	 * @TODO 作业船列表过滤已在作业的船
	 * @param request
	 * @return
	 * JSONArray
	 * @author changj@yuminsoft.com
	 * @date2017年7月26日
	 */
	@RequestMapping("/getWorkShipForOptionCheck")
    @ResponseBody
    public JSONArray getWorkShipForOptionCheck(HttpServletRequest request){
        try{
            List<Map<String,Object>> list = workShipService.getWorkShipForOption(false);
            if(list.size()>0){
                JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
                return jsonArray;
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
	/**
	 * 
	 * @TOD客户船列表
	 * @param request
	 * @return
	 * JSONArray
	 * @author changj@yuminsoft.com
	 * @date2017年7月26日
	 */
	@RequestMapping("/getCustomerShipForOption")
    @ResponseBody
    public JSONArray getCustomerShipForOption(HttpServletRequest request){
        try{
            List<Map<String,Object>> list = customerShipService.getCustomerShipForOption();
            if(list.size()>0){
                JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
                return jsonArray;
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
	
	/**
	 * 
	 * @TOD收费员列表
	 * @param request
	 * @return
	 * JSONArray
	 * @author changj@yuminsoft.com
	 * @date2017年7月26日
	 */
	@RequestMapping("/getCashierForOption")
    @ResponseBody
    public JSONArray getCashierForOption(HttpServletRequest request){
        try{
            List<Map<String,Object>> list = adminUserService.getCashierForOption();
            if(list.size()>0){
                JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
                return jsonArray;
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
	/**
	 * 
	 * @TODO 新增任务
	 * @param orders
	 * @param request
	 * @return
	 * @throws Exception
	 * ResultVo
	 * @author changj@yuminsoft.com
	 * @date2017年7月27日
	 */
	@RequestMapping("/saveOrder")
	@ResponseBody
	public ResultVo saveOrder(Orders orders,HttpServletRequest request) throws Exception {
		try {
			
			//跟进客户船ID 获取客户ID
			Long cid = customerShipService.getCustomerIdByShipId(orders.getCustomerShipId());
			orders.setCusId(cid);
			StringBuffer sb = new StringBuffer();
			sb.append(Constants.ORDER_TYPE.get((orders.getTaskType())));
			sb.append(cid);
			orders.setTaskCode(NumberUtil.getNumberForPK(sb.toString()));
			orders.setTaskStatus(Constants.WAIT_DEAL);//已分派待处理
			orders.setCreateTime(new Date());
			HttpSession session = request.getSession();
			if(session!=null){
				AdminUser adminUser  = (AdminUser) session.getAttribute(Constants.SYS_LOGIN_USR);
				orders.setCreater(adminUser.getName());
			}
			ordersService.saveOrder(orders);
			return  ResultVo.returnMsg(true, "任务创建成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  ResultVo.returnMsg(false, "任务创建失败");
	}
	/**
	 * 
	 * @TODO分派保存
	 * @param orders
	 * @param request
	 * @return
	 * @throws Exception
	 * ResultVo
	 * @author changj@yuminsoft.com
	 * @date2017年7月28日
	 */
	@RequestMapping("/saveFpOrder")
	@ResponseBody
	public ResultVo saveFpOrder(Orders order,HttpServletRequest request) throws Exception {
		try {
			Orders od = ordersService.selectOneById(order.getId());
			od.setServiceDate(order.getServiceDate());
			od.setWorId(order.getWorId());
			od.setAdress(order.getAdress());
			od.setWorkDate(DateUtil.strToDate(order.getWorkDateStr(),DateUtil.DATE_TIME_FORMAT));
			od.setTollCollector(order.getTollCollector());
			od.setUpdateTime(new Date());
			od.setTaskStatus(Constants.WAIT_DEAL);//已分派待处理
			ordersService.updateOrder(od);
			return  ResultVo.returnMsg(true, "任务分派成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  ResultVo.returnMsg(false, "任务分派失败");
	}
	/**
	 * @TODO任务改派
	 * @param order
	 * @param request
	 * @return
	 * @throws Exception
	 * ResultVo
	 * @author changj@yuminsoft.com
	 * @date2017年7月28日
	 */
	@RequestMapping("/saveGpOrder")
	@ResponseBody
	public ResultVo saveGpOrder(Orders order,HttpServletRequest request) throws Exception {
		try {
			Orders od = ordersService.selectOneById(order.getId());
			od.setWorId(order.getWorId());
			od.setServiceDate(order.getServiceDate());
			od.setWorkDate(DateUtil.strToDate(order.getWorkDateStr(),DateUtil.DATE_TIME_FORMAT));
			od.setAdress(order.getAdress());
			od.setTollCollector(order.getTollCollector());
			od.setUpdateTime(new Date());
			od.setTaskStatus(Constants.WAIT_DEAL);//已分派待处理
			ordersService.updateOrder(od);
			return  ResultVo.returnMsg(true, "任务改派成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  ResultVo.returnMsg(false, "任务改派失败");
	}
	/**
	 * 取消任务
	 * @TODO
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 * ResultVo
	 * @author changj@yuminsoft.com
	 * @date2017年7月28日
	 */
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public ResultVo cancelOrder(Long id,HttpServletRequest request) throws Exception {
		try {
			Orders od = ordersService.selectOneById(id);
			od.setUpdateTime(new Date());
			od.setTaskStatus(Constants.CANCELLED);//改为已取消
			ordersService.updateOrder(od);
			return  ResultVo.returnMsg(true, "任务取消成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  ResultVo.returnMsg(false, "任务取消失败");
	}
	
	/**
	 * 功能描述：任务excel导出
	 * 输入参数：
	 * @param ordes
	 * @param resp
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年8月7日
	 */
	@RequestMapping(value="/orderXlsDownload",method = RequestMethod.GET)
	@ResponseBody 
	public Response orderXlsDownload(Orders ordes,HttpServletResponse resp){
		Response response = Response.success();
		try {
			List<Orders> list = ordersMapper.selectOrderListByConditions(ordes);
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String temp = sf.format(new Date()); 
	        
			String[] headArr = new String[]{"任务编号","任务状态","任务类型","作业船","客户船名/单位","地点","是否已分类","收费金额/方式","垃圾量合计预计/实际",
					"生活垃圾预计/实际","扫舱垃圾预计/实际","食品废弃物预计/实际","焚烧炉灰渣预计/实际","塑料预计/实际","生活污水预计/实际","油污预计/实际","服务时间","拒绝原因","联络员（编号或简称）","备注"};
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(ordes.getTaskStatus())){
				map.put("任务状态", Constants.ORDER_STATUS.get(ordes.getTaskStatus()));
			}
			if(StringUtils.isNotBlank(ordes.getTaskStatus())){
				map.put("任务类型", Constants.ORDER_TYPE_STR.get(ordes.getTaskType()));
			}
			if(null != ordes.getWorId()){
				map.put("作业船", ordes.getWorId());
			}
			if(null != ordes.getCustomerShipId()){
				map.put("客户船名/单位", ordes.getCustomerShipId());
			}
			if(StringUtils.isNotBlank(ordes.getTollCollector())){
				map.put("联络员", ordes.getTollCollector());
			}
			
			ExcelBo bo = new ExcelBo();
			bo.setFileName("任务信息_"+temp);
			bo.setTitle("任务信息");
			bo.setParams(map);
			bo.setSubTitle("任务信息");
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
	public List<Map<Integer, Object>> transfer(List<Orders> list){
		List<Map<Integer, Object>> data = new ArrayList<Map<Integer, Object>>();
		if(null != list && list.size()>0){
			for(Orders ws : list){
				Map<Integer, Object> map = new HashMap<Integer, Object>();
				map.put(0, ws.getTaskCode());
				map.put(1, Constants.ORDER_STATUS.get(ws.getTaskStatus()));
				map.put(2, Constants.ORDER_TYPE_STR.get(ws.getTaskType()));
				map.put(3, ws.getWorkShipName());
				map.put(4, ws.getCustomerShipName());
				map.put(5, ws.getAdress());
				map.put(6, getIfTypeStr(ws.getIfType()));
				map.put(7, ws.getCharge());
				map.put(8, ws.getAmountGarbage());
				map.put(9, ws.getFLife());
				map.put(10, ws.getFSweeping());
				map.put(11, ws.getFFood());
				map.put(12, ws.getFBurnCinder());
				map.put(13, ws.getFPlastic());
				map.put(14, ws.getFWater());
				map.put(15, ws.getFGungo());
				map.put(16, ws.getWorkDateStr());
				map.put(17, ws.getReson());
				map.put(18, ws.getTollCollectorName());
				map.put(19, ws.getMemo());
				data.add(map);
			}
		}
		return data;
	}
	
	private String getIfTypeStr(String type){
		if("0".equals(type)){
			return "是";
		}else{
			return "否";
		}
	}
}