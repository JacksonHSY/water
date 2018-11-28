package com.ymkj.smi.manager.service;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.expression.operators.conditional.AndExpression;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.JsonObject;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Customer;
import com.ymkj.smi.manager.common.entity.CustomerShip;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.smi.manager.common.untils.DateUtil;
import com.ymkj.smi.manager.common.untils.NumberUtil;
import com.ymkj.smi.manager.common.untils.PageUtils;
import com.ymkj.smi.manager.common.vo.GarbageReportBO;
import com.ymkj.smi.manager.common.vo.PieReportBO;
import com.ymkj.smi.manager.common.vo.WorkShipBo;
import com.ymkj.smi.manager.common.vo.WorkShipReportBo;
import com.ymkj.smi.manager.mapper.AdminUserMapper;
import com.ymkj.smi.manager.mapper.CustomerMapper;
import com.ymkj.smi.manager.mapper.CustomerShipMapper;
import com.ymkj.smi.manager.mapper.OrdersMapper;
import com.ymkj.smi.manager.mapper.WorkShipMapper;
import com.ymkj.smi.manager.web.api.dto.CustomerShipsDto;
import com.ymkj.smi.manager.web.api.dto.ShipsDto;
import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.base.Model_003001;
import com.ymkj.smi.manager.web.api.model.base.Model_003002;
import com.ymkj.smi.manager.web.api.model.base.Model_003003;
import com.ymkj.smi.manager.web.api.model.base.Model_003004;
import com.ymkj.smi.manager.web.api.model.base.Model_003005;
import com.ymkj.smi.manager.web.api.model.base.Model_003006;
import com.ymkj.smi.manager.web.api.model.base.Model_004001;
import com.ymkj.smi.manager.web.api.model.base.Model_004002;
import com.ymkj.smi.manager.web.api.model.base.Model_004003;
import com.ymkj.smi.manager.web.api.model.base.Model_005002;
import com.ymkj.smi.manager.web.api.model.base.Model_006005;
import com.ymkj.springside.modules.orm.PageInfo;

/**
* OrdersService
* <p/>
* Author: 
* Date: 2017-07-24 18:31:59
* Mail: 
*/
@Service
public class OrdersService  {

	@Autowired
	private OrdersMapper ordersMapper ;
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CustomerShipMapper customerShipMapper ;
	@Autowired
	private WorkShipMapper workShipMapper ;
	@Autowired
	private WorkShipService workShipService ;
	@Autowired
	private CustomerService customerService ;
	
	public void saveOrder(Orders order){
		ordersMapper.insert(order);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<Orders> getOrderListPage(PageInfo<Orders> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		Orders order = (Orders) pageInfo.getQueryParam();
		Page<Orders> page = (Page)ordersMapper.selectOrderListByConditions(order);
        return PageUtils.convertPage(page);
	}
	/**
	 * id查询
	 * @TODO
	 * @param id
	 * @return
	 * Orders
	 * @author changj@yuminsoft.com
	 * @date2017年7月28日
	 */
	public Orders selectOneById(Long id){
		return ordersMapper.selectByPrimaryKey(id);
	}
	/**
	 * update
	 * @TODO
	 * @param order
	 * void
	 * @author changj@yuminsoft.com
	 * @date2017年7月28日
	 */
	public void updateOrder(Orders order){
		ordersMapper.updateByPrimaryKey(order);
	}
	/**
	 * 
	 * @TODO 本月回收垃圾总量top的工作船
	 * @return
	 * List<Map<String,Object>>
	 * @author changj@yuminsoft.com
	 * @date2017年7月31日
	 */
	public List<Orders>  getOrdersForAmountGarbageTop(int size,Date date){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("size", size);
		map.put("workDate", date);
		return ordersMapper.getOrdersForAmountGarbageTop(map);
	}
    /**
     * 
     * @TODO 月度好评前五工作船
     * @return
     * List<Orders>
     * @author changj@yuminsoft.com
     * @date2017年8月1日
     */
	public List<Orders>  getOrdersForHighPraiseTop5(){
		List<Orders> list =ordersMapper.getOrdersForHighPraiseTop5();
		if(list.size()>0){
			for(Orders os:list){
				double percent = os.getTotalJudge()==null? 0: os.getTotalJudge()/ (os.getSize()*5);
				NumberFormat nt = NumberFormat.getPercentInstance();
				nt.setMinimumFractionDigits(2);
				os.setJudgeRate( nt.format(percent));
			}
		}
		return list;
	}
	public List<Orders>  getOrdersForOrdersSumTop(int size,Date date){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("size", size);
		map.put("workDate", date);
		return ordersMapper.getOrdersForOrdersSumTop(map);
	}
	/**
	 * 单日统计信息
	 * @param pageInfo
	 * @author liangj@yuminsoft.com
	 * @return
	 */
	public PageInfo<Orders> getDailyStatisticPage(PageInfo<Orders> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		Orders orders = (Orders) pageInfo.getQueryParam();
		Page<Orders> page = (Page)getDailyStatisticPageList(orders);
        return PageUtils.convertPage(page);
	}
	
	public List<Orders> getDailyStatisticPageList(Orders orders) {
		Map<String,Object> map = new HashMap<String,Object>();
        if(null!=orders.getWorId()){
        	map.put("worId", orders.getWorId());
        }
        if(null!=orders.getCustomerShipId()){
        	map.put("customerShipId", orders.getCustomerShipId());     	
        }
        if(StringUtils.isNotBlank(orders.getTollCollector())){
        	map.put("tollCollector", orders.getTollCollector());       	
        }
        if(StringUtils.isNotBlank(orders.getWorkDateStr())){
        	map.put("workDateStr", orders.getWorkDateStr());       	
        }
        if(StringUtils.isNotBlank(orders.getAdress())){
        	map.put("adress", orders.getAdress());       	
        }
        return ordersMapper.getDailyStatisticPageList(map);
	}
	
	/**
	 * 单月统计信息
	 * @param pageInfo
	 * @author liangj@yuminsoft.com
	 * @return
	 */
	public PageInfo<Orders> getMonthStatisticPage(PageInfo<Orders> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		Orders orders = (Orders) pageInfo.getQueryParam();
		Page<Orders> page = (Page)getMonthStatisticPageList(orders);
        return PageUtils.convertPage(page);
	}
	
	public List<Orders> getMonthStatisticPageList(Orders orders) {
		Map<String,Object> map = new HashMap<String,Object>();        
        if(StringUtils.isNotBlank(orders.getDateBegin())){
        	map.put("dateBegin", orders.getDateBegin());       	
        }
        if(StringUtils.isNotBlank(orders.getDateEnd())){
        	map.put("dateEnd", orders.getDateEnd());       	
        }
        if("0".equals(orders.getStatisticDimension())){
        	return ordersMapper.getMonthStatisticByCustomer(map);       	
        }
        if("1".equals(orders.getStatisticDimension())){
        	return ordersMapper.getMonthStatisticByLiaison(map);
        }
        if("2".equals(orders.getStatisticDimension())){
        	return ordersMapper.getMonthStatisticByWorkShip(map);
        }
        return null;
	}
	
	/**
	 * 功能描述：针对柱状图获取废弃物统计数据
	 * 输入参数：
	 * @return
	 * 返回类型：List<Map<String,Object>>
	 * 创建人：tianx
	 * 日期：2017年8月3日
	 */
	public List<Map<String,Object>> getGarbageStatisticTotalForColumn() {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> reportList = new ArrayList<Map<String,Object>>();
		map.put("timeType", Constants.STATISTIC_TIME_CURYEAR);
		GarbageReportBO curYearBO = ordersMapper.queryGarbageTotalByDate(map);
		BigDecimal[] curYearArr = new BigDecimal[]{curYearBO.getGarbageTotal(),curYearBO.getWaterTotal(),curYearBO.getFoodTotal(),curYearBO.getPlasticTotal(),curYearBO.getSweepingTotal(),curYearBO.getBurnCinderTotal(),curYearBO.getGungoTotal()};
		Map<String,Object> curYearMap = new HashMap<String,Object>(); 
		curYearMap.put("name", "今年");
		curYearMap.put("data", curYearArr);
		reportList.add(curYearMap);
		
		map.clear();
		map.put("timeType", Constants.STATISTIC_TIME_LASTYEAR);
		GarbageReportBO lastYearBO = ordersMapper.queryGarbageTotalByDate(map);
		BigDecimal[] lastYearArr = new BigDecimal[]{lastYearBO.getGarbageTotal(),lastYearBO.getWaterTotal(),lastYearBO.getFoodTotal(),lastYearBO.getPlasticTotal(),lastYearBO.getSweepingTotal(),lastYearBO.getBurnCinderTotal()};
		Map<String,Object> lastYearMap = new HashMap<String,Object>();
		lastYearMap.put("name", "去年");
		lastYearMap.put("data", lastYearArr);
		reportList.add(lastYearMap);
		
        return reportList;
	}
	
	/**
	 * 功能描述：针对饼图获取废弃物统计数据
	 * 输入参数：
	 * @return
	 * 返回类型：List<Map<String,Object>>
	 * 创建人：tianx
	 * 日期：2017年8月3日
	 */
	public List<Map<String,Object>> getGarbageStatisticTotalForPie() {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> reportList = new ArrayList<Map<String,Object>>();
		map.put("timeType", Constants.STATISTIC_TIME_CURMONTH);
		GarbageReportBO curMonthBO = ordersMapper.queryGarbageTotalByDate(map);
		BigDecimal allTotal = curMonthBO.getGarbageTotal().add(curMonthBO.getWaterTotal())
				.add(curMonthBO.getFoodTotal()).add(curMonthBO.getPlasticTotal())
				.add(curMonthBO.getSweepingTotal()).add(curMonthBO.getBurnCinderTotal()).add(curMonthBO.getGungoTotal());
		List<PieReportBO> dataList = new ArrayList<PieReportBO>();
		if(BigDecimal.ZERO.compareTo(allTotal)<0){
			dataList.add(new PieReportBO(Constants.GARBAGE_TYPE_GARBAGE+":"+curMonthBO.getGarbageTotal().setScale(2, BigDecimal.ROUND_DOWN).toString(), curMonthBO.getGarbageTotal().divide(allTotal, 4, BigDecimal.ROUND_HALF_DOWN)));
			dataList.add(new PieReportBO(Constants.GARBAGE_TYPE_WATER+":"+curMonthBO.getWaterTotal().setScale(2, BigDecimal.ROUND_DOWN).toString(), curMonthBO.getWaterTotal().divide(allTotal, 4, BigDecimal.ROUND_HALF_DOWN)));
			dataList.add(new PieReportBO(Constants.GARBAGE_TYPE_FOOD+":"+curMonthBO.getFoodTotal().setScale(2, BigDecimal.ROUND_DOWN).toString(), curMonthBO.getFoodTotal().divide(allTotal, 4, BigDecimal.ROUND_HALF_DOWN)));
			dataList.add(new PieReportBO(Constants.GARBAGE_TYPE_PLASTIC+":"+curMonthBO.getPlasticTotal().setScale(2, BigDecimal.ROUND_DOWN).toString(), curMonthBO.getPlasticTotal().divide(allTotal, 4, BigDecimal.ROUND_HALF_DOWN)));
			dataList.add(new PieReportBO(Constants.GARBAGE_TYPE_SWEEPING+":"+curMonthBO.getSweepingTotal().setScale(2, BigDecimal.ROUND_DOWN).toString(), curMonthBO.getSweepingTotal().divide(allTotal, 4, BigDecimal.ROUND_HALF_DOWN)));
			dataList.add(new PieReportBO(Constants.GARBAGE_TYPE_BURNCINDER+":"+curMonthBO.getBurnCinderTotal().setScale(2, BigDecimal.ROUND_DOWN).toString(), curMonthBO.getBurnCinderTotal().divide(allTotal, 4, BigDecimal.ROUND_HALF_DOWN)));
			dataList.add(new PieReportBO(Constants.GARBAGE_TYPE_GUNGO+":"+curMonthBO.getGungoTotal().setScale(2, BigDecimal.ROUND_DOWN).toString(), curMonthBO.getGungoTotal().divide(allTotal, 4, BigDecimal.ROUND_HALF_DOWN)));
		}
		
		Map<String,Object> curMonthMap = new HashMap<String,Object>(); 
		curMonthMap.put("type", "pie");
		curMonthMap.put("name", "比重");
		curMonthMap.put("data", dataList);
		reportList.add(curMonthMap);
        return reportList;
	}
	
	/**
	 * 功能描述：针对环图获取废弃物统计数据
	 * 输入参数：
	 * @return
	 * 返回类型：List<PieReportBO>
	 * 创建人：tianx
	 * 日期：2017年8月3日
	 */
	public List<PieReportBO> getGarbageStatisticTotalForCircle() {
		Map<String,Object> map = new HashMap<String,Object>();
		List<PieReportBO> reportList = new ArrayList<PieReportBO>();
		map.put("timeType", Constants.STATISTIC_TIME_CURMONTH);
		GarbageReportBO curMonthBO = ordersMapper.queryGarbageTotalByDate(map);
		PieReportBO garbageBO = new PieReportBO(Constants.GARBAGE_TYPE_GARBAGE, curMonthBO.getGarbageTotal());
		PieReportBO waterBO = new PieReportBO(Constants.GARBAGE_TYPE_WATER, curMonthBO.getWaterTotal());
		PieReportBO foodBO = new PieReportBO(Constants.GARBAGE_TYPE_FOOD, curMonthBO.getFoodTotal());
		PieReportBO plasticBO = new PieReportBO(Constants.GARBAGE_TYPE_PLASTIC, curMonthBO.getPlasticTotal());
		PieReportBO sweepingBO = new PieReportBO(Constants.GARBAGE_TYPE_SWEEPING, curMonthBO.getSweepingTotal());
		PieReportBO burnCinderBO = new PieReportBO(Constants.GARBAGE_TYPE_BURNCINDER, curMonthBO.getBurnCinderTotal());
		PieReportBO gungoBO = new PieReportBO(Constants.GARBAGE_TYPE_GUNGO, curMonthBO.getGungoTotal());
		map.clear();
		map.put("timeType", Constants.STATISTIC_TIME_LASTMONTH);
		GarbageReportBO lastMonthBO = ordersMapper.queryGarbageTotalByDate(map);
		
		/**
		 * 计算环比增长率
		 */
		garbageBO.setMomRate(BigDecimal.ONE);
		if(null != lastMonthBO.getGarbageTotal() && BigDecimal.ZERO.compareTo(lastMonthBO.getGarbageTotal())<0){
			garbageBO.setMomRate(curMonthBO.getGarbageTotal().subtract(lastMonthBO.getGarbageTotal())
					.divide(lastMonthBO.getGarbageTotal(),2, BigDecimal.ROUND_DOWN));
		}
		garbageBO.setMomRate(garbageBO.getMomRate().multiply(new BigDecimal(100)));
		
		waterBO.setMomRate(BigDecimal.ONE);
		if(null != lastMonthBO.getWaterTotal() && BigDecimal.ZERO.compareTo(lastMonthBO.getWaterTotal())<0){
			waterBO.setMomRate(curMonthBO.getWaterTotal().subtract(lastMonthBO.getWaterTotal())
					.divide(lastMonthBO.getWaterTotal(),2, BigDecimal.ROUND_DOWN));
		}
		waterBO.setMomRate(waterBO.getMomRate().multiply(new BigDecimal(100)));
		
		foodBO.setMomRate(BigDecimal.ONE);
		if(null != lastMonthBO.getFoodTotal() && BigDecimal.ZERO.compareTo(lastMonthBO.getFoodTotal())<0){
			foodBO.setMomRate(curMonthBO.getFoodTotal().subtract(lastMonthBO.getFoodTotal())
					.divide(lastMonthBO.getFoodTotal(),2, BigDecimal.ROUND_DOWN));
		}
		foodBO.setMomRate(foodBO.getMomRate().multiply(new BigDecimal(100)));
		
		plasticBO.setMomRate(BigDecimal.ONE);
		if(null != lastMonthBO.getPlasticTotal() && BigDecimal.ZERO.compareTo(lastMonthBO.getPlasticTotal())<0){
			plasticBO.setMomRate(curMonthBO.getPlasticTotal().subtract(lastMonthBO.getPlasticTotal())
					.divide(lastMonthBO.getPlasticTotal(),2, BigDecimal.ROUND_DOWN));
		}
		plasticBO.setMomRate(plasticBO.getMomRate().multiply(new BigDecimal(100)));
		
		sweepingBO.setMomRate(BigDecimal.ONE);
		if(null != lastMonthBO.getSweepingTotal() && BigDecimal.ZERO.compareTo(lastMonthBO.getSweepingTotal())<0){
			sweepingBO.setMomRate(curMonthBO.getSweepingTotal().subtract(lastMonthBO.getSweepingTotal())
					.divide(lastMonthBO.getSweepingTotal(),2, BigDecimal.ROUND_DOWN));
		}
		sweepingBO.setMomRate(sweepingBO.getMomRate().multiply(new BigDecimal(100)));
		
		burnCinderBO.setMomRate(BigDecimal.ONE);
		if(null != lastMonthBO.getBurnCinderTotal() && BigDecimal.ZERO.compareTo(lastMonthBO.getBurnCinderTotal())<0){
			burnCinderBO.setMomRate(curMonthBO.getBurnCinderTotal().subtract(lastMonthBO.getBurnCinderTotal())
					.divide(lastMonthBO.getBurnCinderTotal(),2, BigDecimal.ROUND_DOWN));
		}
		burnCinderBO.setMomRate(burnCinderBO.getMomRate().multiply(new BigDecimal(100)));
		
		gungoBO.setMomRate(BigDecimal.ONE);
		if(null != lastMonthBO.getGungoTotal() && BigDecimal.ZERO.compareTo(lastMonthBO.getGungoTotal())<0){
			burnCinderBO.setMomRate(curMonthBO.getGungoTotal().subtract(lastMonthBO.getGungoTotal())
					.divide(lastMonthBO.getGungoTotal(),2, BigDecimal.ROUND_DOWN));
		}
		gungoBO.setMomRate(gungoBO.getMomRate().multiply(new BigDecimal(100)));
		/**
		 * 封装数据
		 */
		reportList.add(0,garbageBO);
		reportList.add(1,waterBO);
		reportList.add(2,foodBO);
		reportList.add(3,plasticBO);
		reportList.add(4,sweepingBO);
		reportList.add(5,burnCinderBO);
		reportList.add(6,gungoBO);
        return reportList;
	}
	
	/**
	 * 创建订单
	 * @param model
	 * @return
	 * Result
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月4日
	 */
	public Result createOrders(Model_003001 model){
//		String descStr = URLDecoder.decode(model, "UTF-8");
		Result result = new Result();
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		Orders ord = new Orders();
		ord.setTaskType(model.getTaskType());
		if("1".equals(model.getUserType())){
			ord.setCusId(model.getId());
		}else{
			CustomerShip cts = new CustomerShip();
			cts.setId(model.getCustomerShipId());
			cts = customerShipMapper.selectOne(cts);
			ord.setCusId(cts.getCusId());
			ord.setTollCollector(String.valueOf(model.getId()));
		}
		ord.setCustomerShipId(model.getCustomerShipId());
		sb.append(Constants.ORDER_TYPE.get((model.getTaskType())));
		sb.append(model.getId());
		ord.setWorId(model.getWorkShipId());
		ord.setMemo(model.getMemo());
		ord.setTaskCode(NumberUtil.getNumberForPK(sb.toString()));
		ord.setTaskStatus(model.getTaskStatus());
		ord.setAdress(model.getAdress());
		ord.setIfType(model.getIfType());
		Double amountGarbage = 0D;
		Double garbageForShow = 0D;
		if(StringUtils.isNotBlank(model.getELife())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getELife());
			ord.setELife(model.getELife());
		}
		if(StringUtils.isNotBlank(model.getFLife())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFLife());
			ord.setFLife(model.getFLife());
		}
		if(StringUtils.isNotBlank(model.getESweeping())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getESweeping());
			ord.setESweeping(model.getESweeping());
		}
		if(StringUtils.isNotBlank(model.getFSweeping())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFSweeping());
			ord.setFSweeping(model.getFSweeping());
		}
		if(StringUtils.isNotBlank(model.getEFood())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEFood());
			ord.setEFood(model.getEFood());
		}
		if(StringUtils.isNotBlank(model.getFFood())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFFood());
			ord.setFFood(model.getFFood());
		}
		if(StringUtils.isNotBlank(model.getEBurnCinder())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEBurnCinder());
			ord.setEBurnCinder(model.getEBurnCinder());
		}
		if(StringUtils.isNotBlank(model.getFBurnCinder())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFBurnCinder());
			ord.setFBurnCinder(model.getFBurnCinder());
		}
		if(StringUtils.isNotBlank(model.getEPlastic())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEPlastic());
			ord.setEPlastic(model.getEPlastic());
		}
		if(StringUtils.isNotBlank(model.getFPlastic())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFPlastic());
			ord.setFPlastic(model.getFPlastic());
		}
		if(StringUtils.isNotBlank(model.getEWater())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEWater());
			ord.setEWater(model.getEWater());
		}
		if(StringUtils.isNotBlank(model.getFWater())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFWater());
			ord.setFWater(model.getFWater());
		}	
		if(StringUtils.isNotBlank(model.getEGungo())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEGungo());
			ord.setEGungo(model.getEGungo());
		}
		if(StringUtils.isNotBlank(model.getFGungo())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFGungo());
			ord.setFGungo(model.getFGungo());
		}
		if(amountGarbage!=0D){			
			ord.setAmountGarbage(Double.toString(amountGarbage));		
		}
		if(garbageForShow!=0D){
			ord.setAmountGarbageForShow(Double.toString(garbageForShow));
		}		
		ord.setWorkDate(model.getWorkDate());
		ord.setJudge(model.getJudge());
		ord.setCreateTime(date);
		ord.setUpdateTime(date);
	
		ordersMapper.insert(ord);
		result.setSuccess(true);
		result.setMessage("订单创建成功!");
					
		return result;
	}
	
	/**
	 * 查询订单分页
	 * @param model
	 * @return
	 * Result
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月8日
	 */
	public Result queryOrderPage(Model_003002 model){
//		PageInfo pageInfo = new PageInfo();		
		Result result = new Result();
		Orders ord = new Orders();
		
		Customer customer = new Customer();
		customer.setId(model.getId());
		customer = customerMapper.selectOne(customer);
		if(null==customer){
			return Result.fail("客户不存在");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cusId", customer.getId());
//		Example example=new Example(Orders.class);
//		Criteria criteria = example.createCriteria();
////		criteria.andEqualTo("status", Constants.DATA_VALID);		
//		criteria.andEqualTo("cusId", customer.getId());	
		PageHelper.startPage(model.getPageNo(), model.getPageSize());
		Page<Orders> page = (Page)ordersMapper.selectOrders(map);
		result.setSuccess(true);
		result.setData(PageUtils.convertPage(page));
		return result;
	}
	
	/**
	 * 查询订单详情
	 * @param model
	 * @return
	 * Result
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月7日
	 */
	public Result queryOrderDetail(Model_003003 model){
		Result result = new Result();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskCode", model.getTaskCode());
//		ord.setTaskCode(model.getTaskCode());
		List<Orders> list = ordersMapper.selectOrders(map);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("订单不存在");
		}
		Orders ord = new Orders();
		ord = list.get(0);
		result.setSuccess(true);
		result.setData(ord);
		
		return result;
	}
	
	/**
	 * 修改订单
	 * @param model
	 * @return
	 * Result
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月4日
	 */
	public Result updateOrders(Model_003005 model){
		Result result = new Result();
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		Orders ord = new Orders();
		ord.setTaskCode(model.getTaskCode());
		List<Orders> list = query(ord);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("订单不存在");
		}
		ord = list.get(0);
		if(ord.getTaskStatus().equals(model.getTaskStatus())&&!Constants.WAIT_DISPATCH.equals(ord.getTaskStatus())){
			return Result.fail("订单已经确定");
		}else if(!ord.getTaskStatus().equals(model.getTaskStatus())&&!Constants.DEALING.equals(ord.getTaskStatus())&&!Constants.WAIT_DEAL.equals(ord.getTaskStatus())){
			return Result.fail("任务状态不是待处理和处理中的，无法修改");
		}
		ord.setWorkDate(model.getWorkDate());
		ord.setMemo(model.getMemo());
		ord.setTaskType(model.getTaskType());
		ord.setTaskStatus(model.getTaskStatus());
		ord.setAdress(model.getAdress());
		ord.setIfType(model.getIfType());
		Double amountGarbage = 0D;
		Double garbageForShow = 0D;
		if(StringUtils.isNotBlank(model.getELife())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getELife());
			ord.setELife(model.getELife());
		}
		if(StringUtils.isNotBlank(model.getFLife())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFLife());
			ord.setFLife(model.getFLife());
		}
		if(StringUtils.isNotBlank(model.getESweeping())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getESweeping());
			ord.setESweeping(model.getESweeping());
		}
		if(StringUtils.isNotBlank(model.getFSweeping())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFSweeping());
			ord.setFSweeping(model.getFSweeping());
		}
		if(StringUtils.isNotBlank(model.getEFood())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEFood());
			ord.setEFood(model.getEFood());
		}
		if(StringUtils.isNotBlank(model.getFFood())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFFood());
			ord.setFFood(model.getFFood());
		}
		if(StringUtils.isNotBlank(model.getEBurnCinder())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEBurnCinder());
			ord.setEBurnCinder(model.getEBurnCinder());
		}
		if(StringUtils.isNotBlank(model.getFBurnCinder())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFBurnCinder());
			ord.setFBurnCinder(model.getFBurnCinder());
		}
		if(StringUtils.isNotBlank(model.getEPlastic())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEPlastic());
			ord.setEPlastic(model.getEPlastic());
		}
		if(StringUtils.isNotBlank(model.getFPlastic())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFPlastic());
			ord.setFPlastic(model.getFPlastic());
		}
		if(StringUtils.isNotBlank(model.getEWater())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEWater());
			ord.setEWater(model.getEWater());
		}
		if(StringUtils.isNotBlank(model.getFWater())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFWater());
			ord.setFWater(model.getFWater());
		}	
		if(StringUtils.isNotBlank(model.getEGungo())){
			amountGarbage = amountGarbage+Double.parseDouble(model.getEGungo());
			ord.setEGungo(model.getEGungo());
		}
		if(StringUtils.isNotBlank(model.getFGungo())){
			garbageForShow = garbageForShow+Double.parseDouble(model.getFGungo());
			ord.setFGungo(model.getFGungo());
		}
				
		if(amountGarbage!=0D){			
			ord.setAmountGarbage(Double.toString(amountGarbage));		
		}
		if(garbageForShow!=0D){
			ord.setAmountGarbageForShow(Double.toString(garbageForShow));
		}	
		ord.setJudge(model.getJudge());
		ord.setUpdateTime(date);
		
		ordersMapper.updateByPrimaryKeySelective(ord);
		result.setSuccess(true);
		result.setMessage("订单修改成功!");
					
		return result;
	}
	
	/**
	 * 取消订单
	 * @param model
	 * @return
	 * Result
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月7日
	 */
	public Result cancelOrders(Model_003006 model){
		Date date = new Date();
		Result result = new Result();
		Orders ord = new Orders();
		ord.setTaskCode(model.getTaskCode());
		List<Orders> list = query(ord);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("订单不存在");
		}
		ord = list.get(0);
		if("1".equals(model.getUserType())){			
			if(!Constants.WAIT_DISPATCH.equals(ord.getTaskStatus())){
				return Result.fail("订单已经确定");
			}
		}else{
			if(!Constants.WAIT_DISPATCH.equals(ord.getTaskStatus())&&!Constants.WAIT_DEAL.equals(ord.getTaskStatus())){
				return Result.fail("订单已经在处理中");
			}
		}
		
		if(StringUtils.isNotBlank(model.getReason())){
			ord.setReson(model.getReason());
		}
		ord.setTaskStatus(Constants.CANCELLED);
		ord.setUpdateTime(date);
		
		ordersMapper.updateByPrimaryKeySelective(ord);
		result.setSuccess(true);
		result.setMessage("订单取消成功!");
		
		return result;
	}
	
	/**
	 * 评价订单
	 * @param model
	 * @return
	 * Result
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月7日
	 */
	public Result judgeOrders(Model_003004 model){
		Result result = new Result();
		Orders ord = new Orders();
		ord.setTaskCode(model.getTaskCode());
		List<Orders> list = query(ord);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("订单不存在");
		}
		ord = list.get(0);
		if(Constants.EVAL_DONE.equals(ord.getTaskStatus())){
			return Result.fail("订单已经评价");
		}
		ord.setJudge(model.getJudge());
		ord.setTaskStatus(Constants.EVAL_DONE);
		
		ordersMapper.updateByPrimaryKeySelective(ord);
		result.setSuccess(true);
		result.setMessage("订单评价成功!");
		
		return result;
	}
	
	/**
	 * 查询船只
	 * @param model
	 * @return
	 * Result
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月15日
	 */
	public Result queryShips(Model_005002 model){
		Map<String,Object> map = new HashMap<String,Object>();
		Result result = new Result();
		ShipsDto shipsDto = new ShipsDto();
		if("1".equals(model.getUserType())){
			map.put("cusId", model.getId());
			List<CustomerShipsDto> customerShipList = customerShipMapper.getShipsByCusId(map);
			shipsDto.setCustomerShipList(customerShipMapper.getShipsByCusId(map));
		}else{
			shipsDto.setCustomerShipList(customerShipMapper.getShipsByCusId(map));
			shipsDto.setWorkShipList(workShipMapper.getShips(map));
		}
		result.setSuccess(true);
		result.setData(shipsDto);
		return result;
	}
	
	/**
	 * 查询订单
	 * @param ord
	 * @return
	 * List<Orders>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月7日
	 */
	public List<Orders> query(Orders ord){
		Example example=new Example(Orders.class);
		Criteria criteria = example.createCriteria();
//		criteria.andEqualTo("status", Constants.DATA_VALID);
		
		if(StringUtils.isNotBlank(ord.getTaskCode())){//
			criteria.andEqualTo("taskCode", ord.getTaskCode());
		}
		return ordersMapper.selectByExample(example);
	}
	/**
	 * 
	 * @TODO
	 * @param model
	 * @return
	 * Result
	 * @date2017年8月8日
	 */
	public Result getOrderReportForApp(Model_004001 model){
		WorkShipReportBo wb = new WorkShipReportBo();
		List<Orders> odList = ordersMapper.getWorkShipReportByWeeks(model.getId());
		List<Orders> judgeList = ordersMapper.getJudgeList(model.getId());
		int times = 0;
		Double amountGarbage = 0D;
		Double amountGarbageForShow = 0D;
		Double burnCinder = 0D;
		Double sweeping = 0D;
		Double water = 0D;
		Double foods = 0D;
		Double plastic = 0D;
		Double gungo = 0D;
		Double life = 0D;
		for(Orders od: odList){
			amountGarbage += StringUtils.isEmpty(od.getAmountGarbage())?0:Double.parseDouble(od.getAmountGarbage());
			amountGarbageForShow += StringUtils.isEmpty(od.getAmountGarbageForShow())?0:Double.parseDouble(od.getAmountGarbageForShow());
			burnCinder += StringUtils.isEmpty(od.getFBurnCinder())?0:Double.parseDouble(od.getFBurnCinder());
			sweeping += StringUtils.isEmpty(od.getFSweeping())?0:Double.parseDouble(od.getFSweeping());
			water += StringUtils.isEmpty(od.getFWater())?0:Double.parseDouble(od.getFWater());
			foods += StringUtils.isEmpty(od.getFFood())?0:Double.parseDouble(od.getFFood());
			plastic += StringUtils.isEmpty(od.getFPlastic())?0:Double.parseDouble(od.getFPlastic());
			life += StringUtils.isEmpty(od.getFLife())?0:Double.parseDouble(od.getFLife());
			gungo += StringUtils.isEmpty(od.getFGungo())?0:Double.parseDouble(od.getFGungo());
			times += od.getSize();
		}
		wb.setOrderList(odList);
		wb.setAmountGarbage(amountGarbage);
		wb.setAmountGarbageForShow(amountGarbageForShow);
		wb.setTimes(times);
		wb.setBurnCinder(burnCinder);
		wb.setFoods(foods);
		wb.setJudgeList(judgeList);
		wb.setLife(life);
		wb.setOrderList(odList);
		wb.setPlastic(plastic);
		wb.setSweeping(sweeping);
		wb.setWater(water);
		wb.setGungo(gungo);
		return Result.success(wb);
	}
	/**
	 * 
	 * @TODO
	 * @param model
	 * @return
	 * Result
	 * @date2017年8月8日
	 */
	public Result getOrderReportForAppByDays(Model_004003 model){
		WorkShipReportBo wb = new WorkShipReportBo();
		List<Orders> odList = ordersMapper.getWorkShipReportByDays(model.getId());
		int times = 0;
		Double amountGarbage = 0D;
		Double amountGarbageForShow = 0D;
		Double burnCinder = 0D;
		Double sweeping = 0D;
		Double water = 0D;
		Double foods = 0D;
		Double plastic = 0D;
		Double gungo = 0D;
		Double life = 0D;
		for(Orders od: odList){
			amountGarbage += StringUtils.isEmpty(od.getAmountGarbage())?0:Double.parseDouble(od.getAmountGarbage());
			amountGarbageForShow += StringUtils.isEmpty(od.getAmountGarbageForShow())?0:Double.parseDouble(od.getAmountGarbageForShow());
			burnCinder += StringUtils.isEmpty(od.getFBurnCinder())?0:Double.parseDouble(od.getFBurnCinder());
			sweeping += StringUtils.isEmpty(od.getFSweeping())?0:Double.parseDouble(od.getFSweeping());
			water += StringUtils.isEmpty(od.getFWater())?0:Double.parseDouble(od.getFWater());
			foods += StringUtils.isEmpty(od.getFFood())?0:Double.parseDouble(od.getFFood());
			plastic += StringUtils.isEmpty(od.getFPlastic())?0:Double.parseDouble(od.getFPlastic());
			life += StringUtils.isEmpty(od.getFLife())?0:Double.parseDouble(od.getFLife());
			gungo += StringUtils.isEmpty(od.getFGungo())?0:Double.parseDouble(od.getFGungo());
			times += od.getSize();
		}
		wb.setOrderList(odList);
		wb.setAmountGarbage(amountGarbage);
		wb.setAmountGarbageForShow(amountGarbageForShow);
		wb.setTimes(times);
		wb.setBurnCinder(burnCinder);
		wb.setFoods(foods);
		wb.setLife(life);
		wb.setOrderList(odList);
		wb.setPlastic(plastic);
		wb.setSweeping(sweeping);
		wb.setWater(water);
		wb.setGungo(gungo);
		return Result.success(wb);
	}
	/**
	 * 	任务列表中的订单列表
	 * @param model
	 * @return
	 * Result
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月8日
	 */
		@SuppressWarnings({ "unchecked", "rawtypes"})
		public Result getOrderListPage(Model_004002 model) {
			JSONObject json = new JSONObject();
			String date = DateUtil.format(new Date(), DateUtil.DATAFORMAT_YYYY_MM_DD);
			String dateBegin = date+" 00:00:00";
			String dateEnd = date+" 23:59:59";
			AdminUser adminUser = new AdminUser();
			adminUser.setName(model.getName());
			List<AdminUser> list = adminUserMapper.select(adminUser);
			if(CollectionUtils.isEmpty(list)){
				return Result.fail("用户不存在");
			}
			adminUser  = list.get(0);
			Map<String,Object> map = new HashMap<String,Object>();
			List<String> status=new ArrayList<>();
			if("1".equals(model.getDifference())){
				map.put("dateBegin", dateBegin );
				map.put("dateEnd", dateEnd );
				status.add(Constants.WAIT_DEAL);
				status.add(Constants.DEALING);
				status.add(Constants.CANCELLED);
				status.add(Constants.DEAL_DONE);
				status.add(Constants.EVAL_DONE);
				map.put("status", status);
			}else{
				
				status.add(Constants.DEAL_DONE);
				status.add(Constants.EVAL_DONE);
				map.put("status", status);
				
			}
			map.put("tollCollector", adminUser.getId());
			
			/*Map<String,Object> map = new HashMap<String,Object>();
			map.put("tollCollector", adminUser.getId());
			map.put("dateBegin", dateBegin );
			map.put("dateEnd", dateEnd );*/
//			Orders orders = new Orders();
//			orders.setTollCollector((adminUser.getId()).toString());
			PageHelper.startPage(model.getPageNo(), model.getPageSize());
			Page<Orders> page = (Page)ordersMapper.selectWorkOrders(map);
			String acc = ordersMapper.selectOrdersCount(map);
			return Result.success(PageUtils.convertPage(page,acc));
			
		}
		

		/**
		 * 查询客户船的任务
		 * @param model
		 * @return
		 * Result
		 * @author huangsy@yuminsoft.com
		 * @date  2017年9月4日
		 */
	public Result selectShipOrder(Model_006005 model) {
		// TODO Auto-generated method stub
		Long id = model.getId();
		Orders order = new Orders();
		order.setCustomerShipId(id);
		List<Orders> shipOrderList = shipOrders(order,"task");
		if(CollectionUtils.isEmpty(shipOrderList)){
			return Result.fail("该船不存在任务");
		}
		return Result.success("存在任务");
		
	}
	/**
	 * 
	 * @param order
	 * @param type
	 * @return
	 */
	public List<Orders> shipOrders(Orders order,String type){
		Example example=new Example(Orders.class);
		Criteria criteria = example.createCriteria(); 
		if("task".equals(type)){
			List<String> statusList = new ArrayList<String>();
			statusList.add(Constants.DEALING);
			statusList.add(Constants.WAIT_DEAL);
			criteria.andIn("taskStatus", statusList);
		}
		if((order.getUpdateTime()!=null)){//
			criteria.andLessThanOrEqualTo("updateTime", order.getUpdateTime());
		}
		if((order.getCustomerShipId()!=null)){// 
			criteria.andEqualTo("customerShipId", order.getCustomerShipId());
		}
		return ordersMapper.selectByExample(example);
		
	}
}