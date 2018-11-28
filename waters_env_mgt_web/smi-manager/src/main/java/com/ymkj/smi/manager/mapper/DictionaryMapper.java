package com.ymkj.smi.manager.mapper;

import java.util.List;

import com.ymkj.smi.manager.common.entity.Dictionary;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* DictionaryMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:31:53
* Mail: 
*/
public interface DictionaryMapper extends JdMapper<Dictionary, Long> {
	
	public int update(Dictionary dictionary);
}