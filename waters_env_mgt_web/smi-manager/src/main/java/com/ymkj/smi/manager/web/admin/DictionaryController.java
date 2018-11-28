package com.ymkj.smi.manager.web.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.Dictionary;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.DictionaryService;
import com.ymkj.springside.modules.utils.Response;

/**
 * DictionaryController.java
 *
 * Author: Yangying
 * Date: 2016年11月7日 上午11:20:37
 * Mail: yangy06@zendaimoney.com
 */
@Controller
@RequestMapping(value = "/admin/dictionary")
public class DictionaryController {
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping("list")
	public String list(Model model) {
		Map<String,String> m= dictionaryService.getAllType();
		model.addAttribute("dictiontypes",m);
 		return "sys/dictionary_list";
	}
	
	/**
	 * 查询某一大类下的所有字典数据
	 * @param dataType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findByDataType",method = RequestMethod.POST)
	public List<Dictionary> findByDataType(String dataType) {
		Map<Object, Object> condition = new HashMap<Object, Object>();
		if(StringUtils.isEmpty(dataType)){
			dataType = "WS_TYPE";
		}
		condition.put("dataType", dataType);
		List<Dictionary> list=dictionaryService.findDictionaryByCondition(condition);
		return  list;
	}
	/**
	 * 根据数据类型以及数值查询字典数据
	 * @param dataType
	 * @param dataValue 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findByDataTypeAndValue",method = RequestMethod.POST)
	public List<Dictionary> findByDataTypeAndValue(String dataType, String dataValue) {
		Map<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("dataType", dataType);
		condition.put("dataValue", dataValue);
		List<Dictionary> list = DictionaryService.findDictionaryByCondition(condition);
		return  list;
	}
 
	/**
	 * 保存字典
	 * @param dictionary
	 * @return
   */
	@ResponseBody
	@RequestMapping(value = "/saveDictionary",method = RequestMethod.POST)
	//@SystemControllerLog(description = "保存",action = com.ymkj.thumb.assistant.manager.constants.Constants.LOGMODULE_字典管理)
	public ResultVo saveDictionary(Dictionary dictionary,HttpServletRequest request) {
		try {
			String dataType=dictionary.getDataType();
			
			Dictionary dic=dictionaryService.findByDataTypeAndValue(dataType, dictionary.getDataValue());

			Map<String,String> map=DictionaryService.getAllType();
			if(dictionary.getId()==null){
				if(dic!=null){
					return new ResultVo(null, 0, Boolean.FALSE, "该字典数据已经存在");
				}				
				dictionary.setCreatepTime(new Date());
				dictionary.setStatus(Constants.DATA_VALID);
				dictionary.setUpdateTime(new Date());
				dictionary.setMemo(map.get(dataType));
				dictionaryService.saveDictionary(dictionary);
			}else{
				if(dic!=null&&(!new Long(dic.getId()).equals(new Long(dictionary.getId())))){
					return new ResultVo(null, 0, Boolean.FALSE, "该字典数据已经存在");
				}
				dictionary.setUpdateTime(new Date());
				dictionaryService.editDictionary(dictionary);
			}
			 dictionary.setMemo(map.get(dataType));
			 dictionaryService.updateMap();//刷新缓存
			 return new ResultVo(null, 0, Boolean.TRUE, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(null, 0, Boolean.FALSE, "操作发生错误，请稍后再试或联系后台管理员");
		}
	}
	/**
	 * 查询字典所有大类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDataTypeNames",method = RequestMethod.POST)
	public List<Dictionary> getDataTypeNames() {
		List<Dictionary> list=dictionaryService.getDataTypeNames();
		return list;
	}
	/**
	 * 修改
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDicById",method = RequestMethod.POST)
	//@SystemControllerLog(description = "修改",action = com.ymkj.thumb.assistant.manager.constants.Constants.LOGMODULE_字典管理)
	public Dictionary findDicById(Long id) {
		Dictionary dictionary=dictionaryService.findDicById(id);
		return dictionary;
	}
	
	/**
	 * 启用、停用
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	//@SystemControllerLog(description = "启用/停用",action = com.ymkj.thumb.assistant.manager.constants.Constants.LOGMODULE_字典管理)
	public ResultVo updateStatus(String ids,String status) throws Exception {
		try{
			dictionaryService.delDictionary(ids,status);
			dictionaryService.updateMap();//刷新缓存
			return new ResultVo(null, 0, Boolean.TRUE, "操作成功");
		} catch (Exception e) {
			e.getStackTrace();
		}
		return  ResultVo.returnMsg(Boolean.FALSE, "操作发生错误，请稍后再试或联系后台管理员");
	}
	
	
}
