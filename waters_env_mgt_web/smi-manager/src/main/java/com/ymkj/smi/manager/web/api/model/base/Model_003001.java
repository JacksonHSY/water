package com.ymkj.smi.manager.web.api.model.base;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Getter
@Setter
public class Model_003001 extends ReqParam{

	private static final long serialVersionUID = 5088332760887304144L;
	
	@NotBlank(message="用户类型不能为空")
    private String userType;
	
	@NotBlank(message="任务类型不能为空")
    private String taskType;
	
	@NotBlank(message="任务状态不能为空")
    private String taskStatus;

	@NotBlank(message="作业地点不能为空")
    private String adress;
	
	@NotBlank(message="是否已分类不能为空")
    private String ifType;
	
	@NotNull(message="用户id不能为空")
    private Long id;
	
	@NotNull(message="客户船名不能为空")
    private Long customerShipId;
	
	private Long workShipId;
	
	/**
	 * 预计生活垃圾量
	 */
    private String eLife;
    
    /**
	 * 实际生活垃圾量
	 */
    private String fLife;
    
    /**
	 * 预计扫仓垃圾量
	 */
    private String eSweeping;
    
    /**
	 * 实际扫仓垃圾量
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
	 * 预计炉渣量
	 */
    private String eBurnCinder;
    
    /**
	 * 实际炉渣量
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
     * 预计油污量
     */
    private String eGungo;
    
    /**
     * 实际油污量
     */
    private String fGungo;
      
    /**
	 * 垃圾总量
	 */
    private String amountGarbage;
    
    /**
   	 * 不同时间维度的垃圾总量(实际垃圾总量)
   	 */
     private String amountGarbageForShow;
	
     @NotNull(message="作业时间不能为空")
    private Date workDate;
	
    /**
   	 * 评价
   	 */
     private String judge;
     
     private String memo;//备注

}
