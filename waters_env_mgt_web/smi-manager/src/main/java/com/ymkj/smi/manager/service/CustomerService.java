package com.ymkj.smi.manager.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.Customer;
import com.ymkj.smi.manager.common.entity.CustomerShip;
import com.ymkj.smi.manager.common.untils.MD5;
import com.ymkj.smi.manager.common.untils.PageUtils;
import com.ymkj.smi.manager.common.vo.ShipVo;
import com.ymkj.smi.manager.mapper.CustomerMapper;
import com.ymkj.smi.manager.mapper.CustomerShipMapper;
import com.ymkj.smi.manager.web.api.dto.CustomerDTO;
import com.ymkj.smi.manager.web.api.dto.CustomersDTO;
import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.base.Model_001002;
import com.ymkj.smi.manager.web.api.model.base.Model_001004;
import com.ymkj.smi.manager.web.api.model.base.Model_005003;
import com.ymkj.smi.manager.web.api.model.base.Model_005004;
import com.ymkj.smi.manager.web.api.model.base.Model_005006;
import com.ymkj.smi.manager.web.api.model.base.Model_006003;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;


/**
* CustomerService
* <p/>
* Author: 
* Date: 2017-07-24 18:31:45
* Mail: 
*/
@Service
public class CustomerService {
	
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired 
	private CustomerShipMapper customerShipMapper;

	/**
	 * 登录(网站、便民APP)
	 * 
	 * @param model
	 * @return
	 */
	public Result login(Model_001002 model){
		String loginName = model.getLoginName();//登录名
		String password = model.getPassword();//密码：一次MD5加密
		
		Customer customer = new Customer();
		customer.setPhone(loginName);
		List<Customer> list = query(customer);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("登录名不存在");
		}
		customer = list.get(0);
		if(!MD5.MD5Encode(password).equals(customer.getPassword())){
			return Result.fail("密码错误");
		}
		CustomerDTO dto = new CustomerDTO();
		dto.setCusName(customer.getCusName());
		dto.setUserName(customer.getUserName());
		dto.setPhone(customer.getPhone());
		dto.setId(customer.getId());
		return Result.success(dto);
	}
   
	/**
	 * 查询客户
	 * 
	 * @param customer
	 * @return
	 */
	public List<Customer> query(Customer customer){
		Example example=new Example(Customer.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", Constants.DATA_VALID);
		
		if(StringUtils.isNotBlank(customer.getPhone())){//
			criteria.andEqualTo("phone", customer.getPhone());
		}
		if(StringUtils.isNotBlank(customer.getUserName())){//
			criteria.andEqualTo("userName", customer.getUserName());
		}
		return customerMapper.selectByExample(example);
	}
/**
 * 
 * @param model
 * @return
 * Result
 * @author huangsy@yuminsoft.com
 * @date  2017年8月4日
 */
	/**
	 * 便民服务修改
	 */
	
	public Result updateCustomerPwd(Model_001004 model) {
		// TODO Auto-generated method stub
		String userName = model.getUserName();//用户名
		String password = model.getOldPassword();//密码：一次MD5加密
		Customer customer = new Customer();
		customer.setUserName(userName);
		List<Customer> list = query(customer);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("用户名不存在");
		}
		customer = list.get(0);
		if(!MD5.MD5Encode(password).equals(customer.getPassword())){
			return Result.fail("密码错误");
		}else{
			customer.setPassword(MD5.MD5Encode(model.getNewPassword()));
			customerMapper.updateByPrimaryKeySelective(customer);
			return Result.success("修改成功");
		}
	}
	
	/**
	 * 客户注册
	 */
	public Result regiestCustomer(Model_005003 model){
		String phone = model.getPhone();
		Customer customer = new Customer();
		customer.setPhone(phone);
		List<Customer> list = queryPhone(customer);
		if(CollectionUtils.isEmpty(list)){
			//注册
			customer.setStatus(Constants.DATA_VALID);
			customer.setUserName(phone);
			customer.setPhone(phone);
			customer.setCreater(phone);
			customer.setCreateTime(new Date());
			customer.setPassword(MD5.MD5Encode(model.getPassword()));
			customerMapper.insert(customer);
			return Result.success("注册成功");
		}else{
			return Result.fail("手机号已经注册过，请直接登入");
		}
		
	}
	/**
	 * 手机查询客户
	 * 
	 * @param customer
	 * @return
	 */
	public List<Customer> queryPhone(Customer customer){
		Example example=new Example(Customer.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", Constants.DATA_VALID);
		
		if(StringUtils.isNotBlank(customer.getPhone())){//
			criteria.andEqualTo("phone", customer.getPhone());
		}
		return customerMapper.selectByExample(example);
	}
	
	/**
	 * 手机号查询客户对应的信息
	 * @param customer
	 * @return
	 * List<Customer>
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月18日
	 */
	public Result selectCustomer(Model_005004 model){
		String phone = model.getPhone();
		Customer customer = new Customer();
		customer.setPhone(phone);
		List<Customer> list = queryPhone(customer);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("不存在");
		}
		customer = list.get(0);
		
		CustomerShip customerShip = new CustomerShip();
		customerShip.setCusId(customer.getId());
		List<CustomerShip> listship = queryShip(customerShip);
		CustomersDTO cd = new CustomersDTO();
		cd.setShipList(listship);
		cd.setCusName(customer.getCusName());
		return Result.success(cd);
	}
	/**
	 * cus_id查询客户船
	 */
	public List<CustomerShip> queryShip(CustomerShip customerShip){
		Example example=new Example(CustomerShip.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", Constants.DATA_VALID);
		criteria.andEqualTo("cusId", customerShip.getCusId());
		
		return customerShipMapper.selectByExample(example);
	}
	
	/**
	 * 修改个人信息
	 */
	@Transactional
	public Result updateCustomer(Model_005006 model){
		List<ShipVo> ListCusShip = model.getShipList();
		Customer customer = new Customer();
		customer.setId(model.getCId());
		customer.setCusName(model.getCusName());
		customer.setUpdateTime(new Date());
		customerMapper.updateByPrimaryKeySelective(customer);
		
		CustomerShip cs = new CustomerShip();
		for(ShipVo ship:ListCusShip){
			cs.setCusId(model.getCId());
			if(StringUtils.isEmpty(ship.getId())){
				cs.setSName(ship.getName());
				cs.setAddress(ship.getAddress());
				cs.setCreater(model.getCusName());
				cs.setCreateTime(new Date());
				cs.setSNature(ship.getNature());
				cs.setStatus(Constants.DATA_VALID);
				customerShipMapper.insertCustomerShip(cs);
			}else{
				cs.setId(Long.parseLong(ship.getId()));
				cs.setSName(ship.getName());
				cs.setAddress(ship.getAddress());
				cs.setSNature(ship.getNature());
				cs.setStatus(Constants.DATA_VALID);
				cs.setUpdateTime(new Date());
				customerShipMapper.updateByPrimaryKeySelective(cs);
			}
		}
		return Result.success("操作成功");
	}
	

	/**
	 * 忘记密码
	 * @param model
	 * @return
	 * Result
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月30日
	 */
	public Result forgotPassword(Model_006003 model) {
		// TODO Auto-generated method stub
		String mobile = model.getMobile();
		String password = model.getPassword();
		Customer customer = new Customer();
		customer.setPhone(mobile);
		List<Customer> list = queryPhone(customer);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("手机号不存在，请先注册");
		}else{
			customer = list.get(0);
			customer.setPassword(MD5.MD5Encode(password));
			customerMapper.updateByPrimaryKeySelective(customer);
		}
		return Result.success("修改成功");
	}
	
	//新增客户
	
	/**
	 * 新增客户
	 * @author huangsy@yuminsoft.com
	 * */
	public Response creatCustomer(Customer customer){
		CustomerShip customerShip = new CustomerShip();
		//判断客户id，不为空就修改
		
		if(customer.getId()!=null){
			if("3".equals(customer.getSNature())){
				customer.setUserName(customer.getPhone());
			}else{
				customer.setUserName(customer.getPhone());
			}
			customer.setUpdateTime(new Date());
			customerMapper.updateByPrimaryKeySelective(customer);
			//判断船的id
			if(customer.getShipId()!=null){
				customerShip.setId(customer.getShipId());
				customerShip.setCusId(customer.getId());
				customerShip.setAddress(customer.getAddress());
				customerShip.setMmsiCode(customer.getMmsiCode());
				customerShip.setSName(customer.getSName());
				customerShip.setSNature(customer.getSNature());
				customerShip.setUpdateTime(new Date());
				customerShipMapper.updateByPrimaryKeySelective(customerShip);
			}else{
				customerShip.setCusId(customer.getId());
				customerShip.setStatus("1");
				customerShip.setAddress(customer.getAddress());
				customerShip.setMmsiCode(customer.getMmsiCode());
				customerShip.setSName(customer.getSName());
				customerShip.setSNature(customer.getSNature());
				customerShip.setCreateTime(new Date());
				customerShip.setUpdateTime(new Date());
				customerShipMapper.insertCustomerShip(customerShip);
			}
		}else{
			creatCustomers(customer);
		}
		
		return Response.success();
	}
	
	@Transactional
	public void creatCustomers(Customer customer) {
		CustomerShip customerShip = new CustomerShip();
		Long id = customer.getId();
		List<Customer> list = queryPhone(customer);
		if (CollectionUtils.isEmpty(list)) {
			customer.setPassword(MD5.MD5Encode(MD5.MD5Encode("888888")));
			customer.setStatus("1");
			customer.setCreateTime(new Date());
			customer.setUpdateTime(new Date());
			customerMapper.insertCustomer(customer);
			if ("3".equals(customer.getSNature())) {
				customer.setUserName(customer.getPhone());
			} else {
				customer.setUserName(customer.getPhone());
			}
			customerMapper.updateByPrimaryKey(customer);
			id = customer.getId();
		}else{
			id = list.get(0).getId();
		}
		customerShip.setCusId(id);
		customerShip.setStatus("1");
		customerShip.setAddress(customer.getAddress());
		customerShip.setMmsiCode(customer.getMmsiCode());
		customerShip.setSName(customer.getSName());
		customerShip.setSNature(customer.getSNature());
		customerShip.setCreateTime(new Date());
		customerShip.setUpdateTime(new Date());
		customerShip.setCreater(customer.getPhone());
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
	public Response updateShip(Customer customer) {
		CustomerShip customerShip = new CustomerShip();
		
		customerShip.setId(customer.getShipId());
		customerShip.setStatus("0");
		customerShipMapper.updateByPrimaryKeySelective(customerShip);
		//customer.setStatus("0");
		//customerMapper.updateByPrimaryKeySelective(customer);
		
		
		return Response.success();
	}
	

	/**
	 * 重置密码
	 * @param customerShip
	 * @return
	 * ResultVo
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response cancleShipPwd(Customer customer) {
		
		
		customer.setPassword(MD5.MD5Encode(MD5.MD5Encode("888888")));
		
		
		customerMapper.updateByPrimaryKeySelective(customer);
		return Response.success();
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
	public PageInfo<Customer> getFileManagementPage(PageInfo<Customer> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		Customer customer = (Customer) pageInfo.getQueryParam();
		Page<Customer> page = (Page)getFilemanagementList(customer);
        return PageUtils.convertPage(page);
	}
	/**
	 * 根据条件查询申请记录表
	 * @author huangsy@yuminsoft.com
	 */
	public List<Customer> getFilemanagementList(Customer customer){
		List<Customer> shipList = new ArrayList<Customer>();
		Map<String, Object> map = new HashMap<String,Object>();
		if(StrUtils.isNotBlank(customer.getSName())){
			map.put("sName", customer.getSName());
		}if(StrUtils.isNotBlank(customer.getSNature())){
			map.put("sNature", customer.getSNature());
		}
		
		
		 shipList = customerMapper.getCustomerShipList(map);
		
		return shipList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}