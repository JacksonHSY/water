package com.ymkj.smi.manager.service.function.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymkj.smi.manager.service.AdminUserService;
import com.ymkj.smi.manager.service.AnnouncementService;
import com.ymkj.smi.manager.service.CustomerService;
import com.ymkj.smi.manager.service.CustomerShipService;
import com.ymkj.smi.manager.service.DictionaryService;
import com.ymkj.smi.manager.service.OrdersService;
import com.ymkj.smi.manager.service.ValidateCodeService;
import com.ymkj.smi.manager.service.WorkShipService;
import com.ymkj.smi.manager.service.function.BusinessService;
import com.ymkj.smi.manager.web.api.anno.FunctionId;
import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.base.Model_001001;
import com.ymkj.smi.manager.web.api.model.base.Model_001002;
import com.ymkj.smi.manager.web.api.model.base.Model_001003;
import com.ymkj.smi.manager.web.api.model.base.Model_001004;
import com.ymkj.smi.manager.web.api.model.base.Model_002001;
import com.ymkj.smi.manager.web.api.model.base.Model_002002;
import com.ymkj.smi.manager.web.api.model.base.Model_003001;
import com.ymkj.smi.manager.web.api.model.base.Model_003002;
import com.ymkj.smi.manager.web.api.model.base.Model_003003;
import com.ymkj.smi.manager.web.api.model.base.Model_003004;
import com.ymkj.smi.manager.web.api.model.base.Model_003005;
import com.ymkj.smi.manager.web.api.model.base.Model_003006;
import com.ymkj.smi.manager.web.api.model.base.Model_004001;
import com.ymkj.smi.manager.web.api.model.base.Model_004002;
import com.ymkj.smi.manager.web.api.model.base.Model_004003;
import com.ymkj.smi.manager.web.api.model.base.Model_005001;
import com.ymkj.smi.manager.web.api.model.base.Model_005002;
import com.ymkj.smi.manager.web.api.model.base.Model_005003;
import com.ymkj.smi.manager.web.api.model.base.Model_005004;
import com.ymkj.smi.manager.web.api.model.base.Model_005006;
import com.ymkj.smi.manager.web.api.model.base.Model_006001;
import com.ymkj.smi.manager.web.api.model.base.Model_006002;
import com.ymkj.smi.manager.web.api.model.base.Model_006003;
import com.ymkj.smi.manager.web.api.model.base.Model_006004;
import com.ymkj.smi.manager.web.api.model.base.Model_006005;
import com.ymkj.smi.manager.web.api.model.base.Model_006006;
import com.ymkj.smi.manager.web.api.model.req.ReqMain;

/**
 * APP业务接口实现
 * 
 * @author longjw@yuminsoft.com
 * 
 */
@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private WorkShipService workShipService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ValidateCodeService validateCodeService;
	@Autowired
	private CustomerShipService customerShipService;
	/**
	 * 登录(作业船APP)
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "001001", desc = "登录(作业船APP)")
	@Override
	public Result login(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_001001 model = (Model_001001) reqMain.getReqParam();
		return adminUserService.adminLogin(model);
	}

	/**
	 * 登录(网站、便民APP)
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "001002", desc = "登录(网站、便民APP)")
	@Override
	public Result customerLogin(ReqMain reqMain) throws Exception {
		Model_001002 model = (Model_001002) reqMain.getReqParam();
		return customerService.login(model);
	}

	/**
	 * 修改密码
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "001003", desc = "修改密码")
	@Override
	public Result modifyPassword(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_001003 model = (Model_001003)reqMain.getReqParam();
		return adminUserService.adminUpdatePwd(model);
	}

	/**
     * 查询公告分页
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
	@FunctionId(value = "002001", desc = "查询公告分页")
	@Override
	public Result queryAnnouncementPage(ReqMain reqMain) throws Exception {
		Model_002001 model = (Model_002001)reqMain.getReqParam();
		return announcementService.getAnnouncementListPage(model);
	}

	/**
     * 查询公告详情
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
	@FunctionId(value = "002002", desc = "查询公告详情")
	@Override
	public Result queryAnnouncementDetail(ReqMain reqMain) throws Exception {
		Model_002002 model = (Model_002002)reqMain.getReqParam();
		return announcementService.getAnnouncementById(model);
	}

	/**
	 * 创建订单
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "003001", desc = "创建订单")
	@Override
	public Result addOrder(ReqMain reqMain) throws Exception {
		Model_003001 model = (Model_003001)reqMain.getReqParam();
		return ordersService.createOrders(model);
	}

	/**
	 * 查询订单分页
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "003002", desc = "查询订单分页")
	@Override
	public Result queryOrderPage(ReqMain reqMain) throws Exception {
		Model_003002 model = (Model_003002)reqMain.getReqParam();
		return ordersService.queryOrderPage(model);
	}

	/**
	 * 查询订单详情
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "003003", desc = "查询订单详情")
	@Override
	public Result queryOrderDetail(ReqMain reqMain) throws Exception {
		Model_003003 model = (Model_003003)reqMain.getReqParam();
		return ordersService.queryOrderDetail(model);
	}

	/**
	 * 评价订单
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "003004", desc = "评价订单")
	@Override
	public Result judgeOrder(ReqMain reqMain) throws Exception {
		Model_003004 model = (Model_003004)reqMain.getReqParam();
		return ordersService.judgeOrders(model);
	}

	/**
	 * 修改订单
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "003005", desc = "修改订单")
	@Override
	public Result updateOrder(ReqMain reqMain) throws Exception {
		Model_003005 model = (Model_003005)reqMain.getReqParam();
		return ordersService.updateOrders(model);
	}
	
	/**
	 * 取消订单
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "003006", desc = "取消订单")
	@Override
	public Result cancelOrder(ReqMain reqMain) throws Exception {
		Model_003006 model = (Model_003006)reqMain.getReqParam();
		return ordersService.cancelOrders(model);
	}

	/**
	 * 任务统计
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "004001", desc = "任务统计")
	@Override
	public Result taskStatis(ReqMain reqMain) throws Exception {
		Model_004001 model = (Model_004001)reqMain.getReqParam();
		return ordersService.getOrderReportForApp(model);
	}

	/**
	 * 任务列表
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "004002", desc = "任务列表")
	@Override
	public Result taskList(ReqMain reqMain) throws Exception {
		Model_004002 model = (Model_004002) reqMain.getReqParam();
		return ordersService.getOrderListPage(model);
	}
	/**
	 * 任务统计(按天)
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "004003", desc = "任务统计(按天)")
	@Override
	public Result taskStatisForDays(ReqMain reqMain) throws Exception {
		Model_004003 model = (Model_004003)reqMain.getReqParam();
		return ordersService.getOrderReportForAppByDays(model);
	}

	/**
	 * 字典查询
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "005001", desc = "字典查询")
	@Override
	public Result queryDictionary(ReqMain reqMain) throws Exception {
		Model_005001 model = (Model_005001) reqMain.getReqParam();
		return dictionaryService.queryDictionaryByDateType(model);
	}
	/**
	 * 船只查询
	 * 
	 * @param reqMain
	 * @return
	 * @throws Exception
	 */
	@FunctionId(value = "005002", desc = "船只查询")
	@Override
	public Result queryShips(ReqMain reqMain) throws Exception {
		Model_005002 model = (Model_005002) reqMain.getReqParam();
		return ordersService.queryShips(model);
	}

	/**
	 * 便民服务修改密码
	 */
	@FunctionId(value = "001004", desc = "(便民服务APP)修改密码")
	
	@Override
	public Result modifyCustomerPassword(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_001004 model = (Model_001004) reqMain.getReqParam();
		return customerService.updateCustomerPwd(model);
		
	}
	
	/**
	 * 客户注册
	 */
	@FunctionId(value = "005003", desc = "客户注册")
	@Override
	public Result regiestCustomer(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_005003 model = (Model_005003) reqMain.getReqParam();
		return customerService.regiestCustomer(model);
	}

	/**
	 * 查询客户信息
	 */
	@FunctionId(value = "005004", desc = "个人信息")
	@Override
	public Result selectCustomer(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_005004 model = (Model_005004) reqMain.getReqParam();
		return customerService.selectCustomer(model);
	}

	/**
	 * 修改个人信息
	 */
	@FunctionId(value = "005006", desc = "修改个人信息")
	@Override
	public Result updateCustomer(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_005006 model = (Model_005006)reqMain.getReqParam();
		return customerService.updateCustomer(model);
	}
	/**
	 * 发送验证码
	 */
	@FunctionId(value = "006006", desc = "发送验证码")
	@Override
	public Result sendValidateCode(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_006006 model = (Model_006006)reqMain.getReqParam();
		return validateCodeService.sendValidateCode(model.getMobile(),model.getType());
	}
	/**
	 * 保存验证码
	 */
	@FunctionId(value = "006001", desc = "保存验证码")
	@Override
	public Result validateCode(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_006001 model = (Model_006001)reqMain.getReqParam();
		return validateCodeService.saveCode(model);
	}

	/**
	 * 查询验证码
	 */
	@FunctionId(value = "006002", desc = "查询验证码")
	@Override
	public Result validateSelect(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_006002 model = (Model_006002)reqMain.getReqParam();
		return validateCodeService.validateSelect(model);
	}

	/**
	 * 忘记密码
	 */
	@FunctionId(value = "006003", desc = "忘记密码")
	@Override
	public Result forgotPassword(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_006003 model = (Model_006003)reqMain.getReqParam();
		return customerService.forgotPassword(model);
	}

	/**
	 * 修改船的状态
	 */
	@FunctionId(value = "006004", desc = "修改船")
	@Override
	public Result deleteShip(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_006004 model = (Model_006004)reqMain.getReqParam();
		return customerShipService.deleteShip(model);
	}

	/**
	 * 查询客户船对应的任务
	 */
	@FunctionId(value = "006005", desc = "查询船任务")
	@Override
	public Result selectShipOrder(ReqMain reqMain) throws Exception {
		// TODO Auto-generated method stub
		Model_006005 model = (Model_006005)reqMain.getReqParam();
		return ordersService.selectShipOrder(model);
	}
	


}
