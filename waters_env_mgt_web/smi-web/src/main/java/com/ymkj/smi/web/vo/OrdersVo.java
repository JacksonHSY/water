package com.ymkj.smi.web.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class OrdersVo {

		/**
	    * 用户类型
	    */
    	private String userType;
		/**
	    * 客户ID
	    */
	    private Long id;

	    /**
	    * 任务编码
	    */
	    private String taskCode;

	    /**
	    * 任务状态
	    */
	    private String taskStatus;

	    /**
	    * 任务类型
	    */
	    private String taskType;

	    /**
	    * 客户船的ID
	    */
	    private Long customerShipId;
	    
	    /**
		 * 作业船的ID
		 */
		 private Long workShipId;


	    /**
	    * 地点
	    */
	    private String adress;

	    /**
	    * 是否分类
	    */
	    private String ifType;

	    /**
	    * 收费方式
	    */
	    private String chargeType;

	    /**
	    * 收费金额
	    */
	    private String chargeAmount;

	    /**
	    * 预计生活垃圾量
	    */
	    private String eLife;

	    /**
	    * 实际生活垃圾量
	    */
	    private String fLife;

	    /**
	    * 预计扫舱垃圾量
	    */
	    private String eSweeping;

	    /**
	    * 实际扫舱垃圾量
	    */
	    private String fSweeping;

	    /**
	    * 预计食品废弃物量
	    */
	    private String eFood;

	    /**
	    * 实际食品废弃物量
	    */
	    private String fFood;

	    /**
	    * 预计焚烧炉火渣量
	    */
	    private String eBurnCinder;

	    /**
	    * 实际焚烧炉火渣量
	    */
	    private String fBurnCinder;

	    /**
	    * 预计塑料量
	    */
	    private String ePlastic;

	    /**
	    * 实际塑料量
	    */
	    private String fPlastic;

	    /**
	    * 预计生活污水量
	    */
	    private String eWater;

	    /**
	    * 实际生活污水量
	    */
	    private String fWater;

	    /**
	    * 作业日期
	    */
	    private String workDate;
	    
	    /**
	     * 预计油污量
	     */
	    private String eGungo;
	    
	    /**
	     * 实际油污量
	     */
	    private String fGungo;

	    /**
	    * 服务日期
	    */
	    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    private Date serviceDate;

	    /**
	    * 拒绝原因
	    */
	    private String reson;

	    /**
	    * 收费员
	    */
	    private String tollCollector;

	    /**
	    * 评价
	    */
	    private String judge;

	    /**
	    * 评价时间
	    */
	    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    private Date judgeTime;

	    /**
	    * 备注
	    */
	    private String memo;

	    /**
	    * 创建人
	    */
	    private String creater;

	    /**
	    * 创建时间
	    */
	    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    private Date createTime;

	    /**
	    * 更新时间
	    */
	    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    private Date updateTime;
	    /**
	     * 垃圾总量
	     */
	    private String amountGarbage;
	    
	    /**
	     * 实际垃圾总量
	     */
	    private String amountGarbageForShow;
	    
//	    @Transient
//	    private String workShipName;//工作船名
//	    
//	    @Transient
//	    private String customerShipName;//客户船名
//	    
//	    @Transient
//	    private String tollCollectorName;//收费/联络员姓名
//	    
//	    @Transient
//	    private String workDateStr;// 作业时间
//	    
//	    @Transient
//	    private Integer size;
//	    
//	    @Transient
//	    private Double totalJudge;
//	    
//	    @Transient
//	    private String judgeRate;
//	    
//	    @Transient
//	    private String dateBegin;
//	    
//	    @Transient
//	    private String dateEnd;
//	    
//	    @Transient
//	    private String charge;
//	    
//	    @Transient
//	    private String customerPhone;//客户船联系电话
//	    
//	    @Transient
//	    private String customerAddress;//客户船国籍
//	    
//	    @Transient
//	    private String statisticDimension;//统计维度shipName
//	    
//	    @Transient
//	    private String objName;//对象（客户船或联络员的名字）
	
}
