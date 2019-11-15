package com.example.myanmarfontconverter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.myanmarfontconverter.dao.CommonDao;
import com.example.myanmarfontconverter.dto.ConfigDto;
import com.example.myanmarfontconverter.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	CommonDao dao;

	@Value("${database.name}")
	String databaseName;

	public static final String CONFIG_NAME = "database";
	
	@Override
	public ConfigDto findConfig() {
		String sql = "select databaseName from " + databaseName + ".config where isDeleted = 0 and configName = :configName ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("configName", CONFIG_NAME);
		
		ConfigDto dto = new ConfigDto();
		try {
			List<String> configList = (List<String>) dao.executeSqlQuery(sql, params);
			
			if (configList.size()>0) {
				dto.setDatabaseName(configList.get(0));
				dto.setConfigName(CONFIG_NAME);
			}
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	@Override
	public Map<String, Object> save(ConfigDto dto) {
		
		try {
			return dao.save(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HashMap<String, Object>();
	}
	
	@Override
	public void update(ConfigDto dto) {
		String uptSql = "update " + databaseName + ".config set databaseName = :newDatabaseName where configName = :configName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newDatabaseName", dto.getDatabaseName());
		params.put("configName", CONFIG_NAME);
		try {
			dao.executeSqlUpdate(uptSql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
