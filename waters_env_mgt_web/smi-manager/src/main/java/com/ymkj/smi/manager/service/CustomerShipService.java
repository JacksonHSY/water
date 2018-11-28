package com.ymkj.smi.manager.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.Customer;
import com.ymkj.smi.manager.common.entity.CustomerShip;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.smi.manager.common.untils.MD5;
import com.ymkj.smi.manager.common.untils.PageUtils;
import com.ymkj.smi.manager.mapper.CustomerMapper;
import com.ymkj.smi.manager.mapper.CustomerShipMapper;
import com.ymkj.smi.manager.mapper.OrdersMapper;
import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.base.Model_006004;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;


/**
* CustomerShipService
* <p/>
* Author: 
* Date: 2017-07-24 18:31:49
* Mail: 
*/
@Service
public class CustomerShipService {

	@Autowired
	private CustomerShipMapper customerShipMapper ;
	
	@Autowired
	private CustomerMapper customerMapper ;
	
	@Autowired
	private OrdersMapper ordersMapper ;
	
	/**
	 * 
	 * @TODO 客户船船下拉列表
	 * @return
	 * List<Map<String,Object>>
	 * @author changj@yuminsoft.com
	 * @date2017年7月26日
	 */
	public List<Map<String,Object>>  getCustomerShipForOption(){
		Example example=new Example(CustomerShip.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", Constants.DATA_VALID);
		List<CustomerShip> list = customerShipMapper.selectByExample(example);
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		if(list!=null && list.size()>0){
			for(CustomerShip ws:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id",ws.getId());
				map.put("customerShipName",ws.getSName());
				retList.add(map);
			}
		}
		return retList;
	}
	/**
	 * 
	 * @ 跟进客户船ID 获取客户ID
	 * @param shipId
	 * @return
	 * Long
	 * @author changj@yuminsoft.com
	 * @date2017年7月27日
	 */
	public Long getCustomerIdByShipId(Long shipId){
		Example example=new Example(CustomerShip.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", shipId);
		criteria.andEqualTo("status", Constants.DATA_VALID);
		List<CustomerShip> list = customerShipMapper.selectByExample(example);
		if(list!= null && list.size()>0){
			Long cid = list.get(0).getCusId();
			Customer customer = new Customer();
			customer.setId(cid);
			return customerMapper.selectOne(customer).getId();
		}
		return null;
		
	}
	/**
	 * 管理客户船分页查询
	 * @param pageInfo
	 * @return
	 * PageInfo<CustomerShip>
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<CustomerShip> getFileManagementPage(PageInfo<CustomerShip> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		CustomerShip customerShip = (CustomerShip) pageInfo.getQueryParam();
		Page<CustomerShip> page = (Page)getFilemanagementList(customerShip);
        return PageUtils.convertPage(page);
	}
	/**
	 * 根据条件查询申请记录表
	 * @author huangsy@yuminsoft.com
	 */
	public List<CustomerShip> getFilemanagementList(CustomerShip customerShip){
		List<CustomerShip> shipList = new ArrayList<CustomerShip>();
		Map<String, Object> map = new HashMap<String,Object>();
		if(StrUtils.isNotBlank(customerShip.getSName())){
			map.put("sName", customerShip.getSName());
		}if(StrUtils.isNotBlank(customerShip.getSNature())){
			map.put("sNature", customerShip.getSNature());
		}
		
		
		 shipList = customerShipMapper.getCustomerShipList(map);
		
		return shipList;
	}
	/**
	 * 新增客户船
	 * @author huangsy@yuminsoft.com
	 * */
	public Response creatShip(CustomerShip customerShip){
		//CustomerShip customerS = customerShipMapper.selectOne(customerShip);
		Customer customer = new Customer();
		if(customerShip.getId()!=null){
			customer.setId(customerShip.getCusId());
			customer.setCusName(customerShip.getCusName());
			customer.setPhone(customerShip.getPhone());
			customer.setUpdateTime(new Date());
			if("3".equals(customerShip.getSNature())){
				customer.setUserName("310"+customer.getId());
			}else{
				customer.setUserName("210"+customer.getId());
			}
			customerMapper.updateByPrimaryKeySelective(customer);
			customerShip.setUpdateTime(new Date());
			customerShipMapper.updateByPrimaryKeySelective(customerShip);
		}else{
			creatCustomerShip(customerShip);
		}
		//creatCustomerShip(customerShip);
		
		return Response.success();
	}
	
	@Transactional
	public void creatCustomerShip(CustomerShip customerShip){
		
		Customer customer = new Customer();
		//customer.setCusName(customerShip.getCusName());
		/*if(null!=customerShip.getPhone()){
			customer.setPhone(customerShip.getPhone());
		}*/
		customer.setPassword(MD5.MD5Encode(MD5.MD5Encode("888888")));
		customer.setStatus("1");
		customer.setCusName(customerShip.getCusName());
		customer.setPhone(customerShip.getPhone());
		customer.setCreateTime(new Date());
		customer.setUpdateTime(new Date());
		customer.setCreater(customerShip.getCreater());
		customerMapper.insertCustomer(customer);
		Long id = customer.getId();
		if("3".equals(customerShip.getSNature())){
			customer.setUserName("310"+id);
		}else{
			customer.setUserName("210"+id);
		}
		customerMapper.updateByPrimaryKey(customer);
		
		customerShip.setCusId(id);
		customerShip.setStatus("1");
		customerShip.setCreateTime(new Date());
		customerShip.setUpdateTime(new Date());
		customerShipMapper.insertCustomerShip(customerShip);
	}
	
	
	/**
	 * 注销
	 * @param customerShip
	 * @return
	 * Response
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response updateShip(CustomerShip customerShip){
		Customer customer = new Customer();
		customerShip.setStatus("0");
		customerShipMapper.updateByPrimaryKeySelective(customerShip);
		customer.setId(customerShip.getCusId());
		customer.setStatus("0");
		customerMapper.updateByPrimaryKeySelective(customer);
		return Response.success();
	}
	
	/**
	 * @author huangsy@yuminsoft.com
	 * 重置
	 * */
	public Response cancleShipPwd(CustomerShip customerShip){
		Customer customer = new Customer();
		customer.setId(customerShip.getCusId());
		customer.setPassword(MD5.MD5Encode(MD5.MD5Encode(customerShip.getPassword())));
		customerMapper.updateByPrimaryKeySelective(customer);
		return Response.success();
	}
	
	/**
	 * 修改船的状态
	 * @param model
	 * @return
	 * Result
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月31日
	 */
	public Result deleteShip(Model_006004 model) {
		// TODO Auto-generated method stub
		 Long id = model.getId();
		    CustomerShip customerShip = new CustomerShip();
		    customerShip.setId(id);
		    customerShip.setStatus("0");
		    Example example=new Example(Orders.class);
		    Criteria criteria = example.createCriteria();
		    criteria.andEqualTo("customerShipId", customerShip.getId());
		    List<Orders> shipOrders = (List<Orders>) ordersMapper.selectByExample(example);
		    if(CollectionUtils.isEmpty(shipOrders)){
		      customerShipMapper.updateByPrimaryKeySelective(customerShip);
		    }else{
		      return Result.fail("该船存在任务不可删除");
		    }
		    
		    return Result.success("删除成功");
	}
}