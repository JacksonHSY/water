package com.ymkj.smi.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.WorkShip;
import com.ymkj.smi.manager.common.untils.PageUtils;
import com.ymkj.smi.manager.common.vo.WorkShipBo;
import com.ymkj.smi.manager.mapper.WorkShipMapper;
import com.ymkj.springside.modules.orm.PageInfo;

/**
* WorkShipService
* <p/>
* Author: 
* Date: 2017-07-24 18:32:10
* Mail: 
*/
@Service
public class WorkShipService {
	
	@Autowired
	private WorkShipMapper workShipMapper ;
	
	/**
	 * 
	 * @TODO
	 * @param flag true:所有 false:只显示闲置工作船
	 * @return
	 * List<Map<String,Object>>
	 * @author changj@yuminsoft.com
	 * @date2017年7月28日
	 */
	public List<Map<String,Object>>  getWorkShipForOption(boolean flag){
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		List<WorkShip> list  = new ArrayList<WorkShip>();
		if(flag){
			Example example=new Example(WorkShip.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("status", Constants.DATA_VALID);
			 list = workShipMapper.selectByExample(example);
		}else{
			list = workShipMapper.getWorkShipForOptionCheck();
		}
		if(list!=null && list.size()>0){
			for(WorkShip ws:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id",ws.getId());
				map.put("workSname",ws.getName());
				retList.add(map);
			}
		}
		return retList;
	}
    
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param ship
	 * @return
	 * 返回类型：List<WorkShip>
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	public List<WorkShip> queryWorkShipList(WorkShip ship){
		ship.setStatus(Constants.DATA_VALID);
		return workShipMapper.select(ship);
	}
	
	/**
	 * 功能描述：查询作业船信息
	 * 输入参数：
	 * @param ship
	 * @return
	 * 返回类型：List<WorkShip>
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	public List<WorkShip> queryWorkShipByExample(WorkShip ship){
		Example example = new Example(WorkShip.class);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(ship.getName())){
			criteria.andLike("name", "%"+ship.getName()+"%");
		}
		criteria.andEqualTo("status", Constants.DATA_VALID);
		example.setOrderByClause("CREATE_TIME desc");
		return workShipMapper.selectByExample(example);
	}
	
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param pageInfo
	 * @return
	 * 返回类型：PageInfo<WorkShip>
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	@SuppressWarnings("rawtypes")
	public PageInfo<WorkShip> queryWorkShipPage(PageInfo pageInfo){
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		WorkShip ship = (WorkShip) pageInfo.getQueryParam();
		Page<WorkShip> page = (Page) this.queryWorkShipByExample(ship);
		return PageUtils.convertPage(page);
	}
	
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param ship
	 * @return
	 * 返回类型：int
	 * 创建人：tianx
	 * 日期：2017年7月28日
	 */
	public int insert(WorkShip ship){
		ship.setStatus(Constants.DATA_VALID);
		ship.setCreateTime(new Date());
		ship.setUpdateTime(new Date());
		return workShipMapper.insert(ship);
	}
	
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param ship
	 * @return
	 * 返回类型：int
	 * 创建人：tianx
	 * 日期：2017年7月28日
	 */
	public int updateByPrimaryKey(WorkShip ship){
		ship.setUpdateTime(new Date());
		return workShipMapper.updateByPrimaryKey(ship);
	}
	
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param ship
	 * @return
	 * 返回类型：int
	 * 创建人：tianx
	 * 日期：2017年7月28日
	 */
	public WorkShip selectOne(WorkShip ship){
		ship.setStatus(Constants.DATA_VALID);
		return workShipMapper.selectOne(ship);
	}
	
	/**
	 * 功能描述：根据ID校验数据是否存在
	 * 输入参数：
	 * @param id
	 * @return
	 * 返回类型：Boolean
	 * 创建人：tianx
	 * 日期：2017年7月28日
	 */
	public Boolean isValidRecordById(Long id){
		Example example = new Example(WorkShip.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", id);
		criteria.andEqualTo("status", Constants.DATA_VALID);
		int count = workShipMapper.selectCountByExample(example);
		if(count < 1){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 功能描述：查询船长
	 * 输入参数：
	 * @return
	 * 返回类型：List<WorkShip>
	 * 创建人：tianx
	 * 日期：2017年8月4日
	 */
	public List<WorkShip> getCaptainGroup(){
		return workShipMapper.getCaptainGroup();
	}
}