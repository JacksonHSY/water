package com.ymkj.smi.manager.common.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import com.ymkj.springside.modules.orm.AbstractEntity;

/**
* Orders
* <p/>
* Author: 
* Date: 2017-07-24 18:31:59
* Mail: 
*/
@Table(name = "orders")
@Getter
@Setter
public class Orders extends AbstractEntity<Long> {
    @Id
    private Long id;

    /**
    * 客户ID
    */
    private Long cusId;

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
    * 作业船_ID
    */
    private Long worId;

    /**
    * 客户船ID
    */
    private Long customerShipId;

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
     * 预计油污量
     */
    private String eGungo;
    
    /**
     * 实际油污量
     */
    private String fGungo;
    /**
    * 作业日期
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

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
    
    @Transient
    private String workShipName;//工作船名
    
    @Transient
    private String customerShipName;//客户船名
    
    @Transient
    private String tollCollectorName;//收费/联络员姓名
    
    @Transient
    private String workDateStr;// 作业时间
    
    @Transient
    private Integer size;
    
    @Transient
    private Double totalJudge;
    
    @Transient
    private String judgeRate;
    
    @Transient
    private String dateBegin;
    
    @Transient
    private String dateEnd;
    
    @Transient
    private String charge;
    
    @Transient
    private String customerPhone;//客户船联系电话
    
    @Transient
    private String customerAddress;//客户船国籍
    
    @Transient
    private String statisticDimension;//统计维度shipName
    
    @Transient
    private String objName;//对象（客户船或联络员的名字）
    
    @Transient
    private String taskStatusName;//任务状态名称
    
    @Transient
    private String weekNum;//当月第几周
    
    @Transient
    private String taskTypeName;//任务类型
    
    @Transient
    private String aurPhone;
    
    @Transient
    private String showTime;
    
    @Transient
    private String days;//每月日期
}