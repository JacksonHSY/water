package com.ymkj.smi.manager.service.function;

import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.req.ReqMain;


/**
 * 对接APP业务接口
 * 
 * @author Cherish
 *
 */
public interface BusinessService extends FunctionService {

    /**
     * 登录(作业船APP)
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result login(ReqMain reqMain) throws Exception;

    /**
     * 登录(网站、便民APP)
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result customerLogin(ReqMain reqMain) throws Exception;

    /**
     * 修改密码
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result modifyPassword(ReqMain reqMain) throws Exception;
    
    /**
     * 便民服务修改密码
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result modifyCustomerPassword(ReqMain reqMain) throws Exception;

    /**
     * 查询公告分页
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result queryAnnouncementPage(ReqMain reqMain) throws Exception;
    
    /**
     * 查询公告详情
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result queryAnnouncementDetail(ReqMain reqMain) throws Exception;

    /**
     * 创建订单
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result addOrder(ReqMain reqMain) throws Exception;

    /**
     * 查询订单分页
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result queryOrderPage(ReqMain reqMain) throws Exception;
    
    /**
     * 查询订单详情
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result queryOrderDetail(ReqMain reqMain) throws Exception;

    /**
     * 评价订单
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result judgeOrder(ReqMain reqMain) throws Exception;

    /**
     * 修改订单
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result updateOrder(ReqMain reqMain) throws Exception;
    
    /**
     * 取消订单
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result cancelOrder(ReqMain reqMain) throws Exception;

    /**
     * 任务统计
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result taskStatis(ReqMain reqMain) throws Exception;
    
    /**
     * 任务列表
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result taskList(ReqMain reqMain) throws Exception;
    
    /**
     * 任务统计按天
     * @TODO
     * @param reqMain
     * @return
     * @throws Exception
     * Result
     * @author changj@yuminsoft.com
     * @date2017年9月7日
     */
    public Result taskStatisForDays(ReqMain reqMain) throws Exception;
    /**
     * 字典查询
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result queryDictionary(ReqMain reqMain) throws Exception;

    /**
     * 字典查询
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result queryShips(ReqMain reqMain) throws Exception;
    
    /**
     * 注册客户
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result regiestCustomer(ReqMain reqMain) throws Exception;
    
    /**
     * 查询客户信息
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result selectCustomer(ReqMain reqMain) throws Exception;
    
    /**
     * 修改个人信息
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result updateCustomer(ReqMain reqMain) throws Exception;
    
    /**
     * 发送验证码
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result sendValidateCode(ReqMain reqMain) throws Exception;
    /**
     * 保存验证码
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result validateCode(ReqMain reqMain) throws Exception;
    
    /**
     * 校验验证码
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result validateSelect(ReqMain reqMain) throws Exception;
    
    /**
     * 忘记密码
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result forgotPassword(ReqMain reqMain) throws Exception;
    
    /**
     * 修改船的状态
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result deleteShip(ReqMain reqMain) throws Exception;
    
    /**
     * 客户船id查询船对应的任务
     * 
     * @param reqMain
     * @return
     * @throws Exception
     */
    public Result selectShipOrder(ReqMain reqMain) throws Exception;
    
}
