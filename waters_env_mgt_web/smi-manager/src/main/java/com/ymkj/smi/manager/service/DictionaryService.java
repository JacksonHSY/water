package com.ymkj.smi.manager.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.Dictionary;
import com.ymkj.smi.manager.mapper.DictionaryMapper;
import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.base.Model_005001;

/**
* DictionaryService
* <p/>
* Author: 
* Date: 2017-07-24 18:31:53
* Mail: 
*/
@Service
public class DictionaryService{
	private static LinkedHashMap<Long, Dictionary> dictionaryMap = new LinkedHashMap<Long, Dictionary>();
    @Autowired
    private DictionaryMapper dictionaryMapper;
    
   public List<Dictionary> queryDictionary(Dictionary dictionary) {
		Example example = new Example(Dictionary.class);
		Criteria criteria=example.createCriteria();
//		criteria.andEqualTo("status", Constants.DATA_VALID);
		example.orderBy("id");
		List<Dictionary> dictionaryList = dictionaryMapper.selectByExample(example);
		return dictionaryList;
	}
	
   @PostConstruct
	public void start(){
		init(LoadDictionary());
	}
	
	protected static void init(List<Dictionary> loadDictionaryData) {
			for (Dictionary dictionarylist : loadDictionaryData) {
				dictionaryMap.put(dictionarylist.getId(), dictionarylist);
		}
	}
	
	protected List<Dictionary> LoadDictionary() {
		Dictionary dictionary = new Dictionary();
//		dictionary.setStatus(Constants.DATA_VALID);
		return queryDictionary(dictionary);
	}
	/**
	 * 根据字典类型和值查找 字典记录
	 * @param dataType
	 * @param value
	 * @return
	 */
	public Dictionary findByDataTypeAndValue(String dataType, String value) {
		for (Dictionary d : dictionaryMap.values()) {
			if (dataType.equals(d.getDataType()) && value.equals(d.getDataValue())) {
				return d;
			}
		}
		return null;
	}
	
	/**
	 * 根据条件查询dictionary Map
	 * @param condition
	 * @return
	 */
	public static List<Dictionary> findDictionaryByCondition(Map<Object, Object> condition){
		LinkedHashMap<Long, Dictionary> dictionarys = new LinkedHashMap<Long, Dictionary>();
		dictionarys.putAll(dictionaryMap);
		Iterator<Entry<Long, Dictionary>> iterator = dictionaryMap.entrySet().iterator();
		Dictionary dictionary;
		while(iterator.hasNext()){
			dictionary = iterator.next().getValue();
			if(condition.containsKey("id") && dictionary.getId() != condition.get("id")){
				dictionarys.remove(dictionary.getId());
			}
			if(condition.containsKey("dataType") && null != dictionary.getDataType() && !dictionary.getDataType().equals(condition.get("dataType"))){
				dictionarys.remove(dictionary.getId());
			}
			if(condition.containsKey("dataName") && null != dictionary.getDataName() && !dictionary.getDataName().equals(condition.get("dataName"))){
				dictionarys.remove(dictionary.getId());
			}
			if(condition.containsKey("dataValue") && null != dictionary.getDataValue() && !dictionary.getDataValue().equals(condition.get("dataValue"))){
				dictionarys.remove(dictionary.getId());
			}
			if(condition.containsKey("status") && null != dictionary.getStatus() && !dictionary.getStatus().equals(condition.get("status"))){
				dictionarys.remove(dictionary.getId());
			}
		}
		return convertMapToList(dictionarys);
	}
	
	/**
	 * Map转list
	 * @param dictionaryMap
	 * @return
	 */
	protected static List<Dictionary> convertMapToList(LinkedHashMap<Long, Dictionary> dictionaryMap){
		List<Dictionary> DictionaryList = new ArrayList<Dictionary>();
		Iterator<Entry<Long, Dictionary>> dictionaryIterator = dictionaryMap.entrySet().iterator();
		while(dictionaryIterator.hasNext()){
			Dictionary vo=new Dictionary();
			Dictionary dictionary=dictionaryIterator.next().getValue();
			vo.setId(dictionary.getId());
			vo.setDataType(dictionary.getDataType());
			vo.setMemo(dictionary.getMemo());
			vo.setDataName(dictionary.getDataName());
			vo.setDataValue(dictionary.getDataValue());
			vo.setStatus(dictionary.getStatus()); 
			DictionaryList.add(vo);
		}
		return DictionaryList;
	}
    
	
	/**
	 * 查询字典大类
	 * @return
	 */
	public static Map<String, String> getAllType() {

		Map<String, String> map = new HashMap<String, String>();
		for (Dictionary d : dictionaryMap.values()) {
			map.put(d.getDataType(), d.getMemo());
		}
		return map;
	}
	/**
	 * 查询字典大类
	 * @return
	 */
	public static List<Dictionary> getDataTypeNames(){		
		Map<String,String> map =getAllType();
		List<Dictionary> list = new ArrayList<Dictionary>();
		for (String key : map.keySet()) {  
			Dictionary dic = new Dictionary();
			dic.setDataType(key);
			dic.setDataName(map.get(key));
		    list.add(dic);
		  
		} 		
		return list;
	}
	
	/**
	 * 根据ID查找字典
	 * @param id
	 * @return
	 */
	public Dictionary findDicById(Long id){
		return dictionaryMap.get(id);
	}
	
	/**
	 * 更新缓存Map，重新加载
	 */
	public void updateMap(){
//		log.info(DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT) + " 更新字典缓存任务 start...");
		List<Dictionary> allDictionary=queryDictionary(null);
		dictionaryMap.clear();
		for (Dictionary dictionarylist : allDictionary) {
			if(!"sync_customer_time".equals(dictionarylist.getDataType()) && !"sync_order_time".equals(dictionarylist.getDataType())){
				dictionaryMap.put(dictionarylist.getId(), dictionarylist);
			}
		}
//		log.info(DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT) + " 更新字典缓存任务 end...");
	}
	
	/**
	 * 修改字典信息
	 * @param dubboRequest
	 * @return
	 */
	public int delDictionary(String ids,String status) {
		String[] idArrs = ids.split(",");
		for(int i =0;i<idArrs.length;i++){
			Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(Long.parseLong(idArrs[i]));
			dictionary.setStatus(status);
			dictionaryMapper.updateByPrimaryKey(dictionary);
		}
		return 0;
	}
	
	/**
	 * 保存字典信息
	 * @param dubboRequest
	 * @return 
	 * @return
	 */
	public int saveDictionary(Dictionary dictionary) {
		return dictionaryMapper.insert(dictionary);
	}
	/**
	 * 编辑字典信息
	 * @param dubboRequest
	 * @return 
	 * @return
	 */
	public int editDictionary(Dictionary dictionry){
		return dictionaryMapper.updateByPrimaryKeySelective(dictionry);
		
	}
	
	public  Result queryDictionaryByDateType(Model_005001 model){
		Result result = new Result();
		Example example=new Example(Dictionary.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("dataType", model.getDataType());
		criteria.andEqualTo("status", Constants.DATA_VALID);
		List<Dictionary> dictionaryList = dictionaryMapper.selectByExample(example);
		result.setSuccess(true);
		result.setData(dictionaryList);
		return result;
	}
	
} 