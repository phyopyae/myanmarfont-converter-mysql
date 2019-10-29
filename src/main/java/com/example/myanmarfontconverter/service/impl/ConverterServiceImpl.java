package com.example.myanmarfontconverter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.myanmarfontconverter.dao.CommonDao;
import com.example.myanmarfontconverter.dto.ResultDto;
import com.example.myanmarfontconverter.service.ConverterService;
import com.google.myanmartools.TransliterateZ2U;
import com.google.myanmartools.ZawgyiDetector;

@Service
public class ConverterServiceImpl implements ConverterService {

	@Autowired
	CommonDao dao;

	@Value("${database.name}")
	String databaseName;

	private static final ZawgyiDetector DETECTOR = new ZawgyiDetector();

	private static final TransliterateZ2U CONVERTER = new TransliterateZ2U("Zawgyi to Unicode");

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTableNames() {
		String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA=:dbName ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", databaseName);
		try {
			List<String> tableNameList = (List<String>) dao.executeSqlQuery(sql, params);
			return tableNameList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllColumnNames(String tableName) {
		String sql = "SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` "
				+ "WHERE `TABLE_SCHEMA`=:dbName AND `TABLE_NAME`=:colName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", databaseName);
		params.put("colName", tableName);
		try {
			List<String> columnsList = (List<String>) dao.executeSqlQuery(sql, params);
			return columnsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getItems(String tableName, String columnName) {
		String dbTableName = databaseName + "." + tableName;
		String sql = "SELECT " + columnName + " FROM " + dbTableName + " WHERE " + columnName + " IS NOT NULL AND "
				+ columnName + " <> ''";
		try {
			List<String> itemList = (List<String>) dao.executeSqlQuery(sql, null);
			return itemList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public List<ResultDto> detectAndConvertUnicode(List<String> currentList) {
		List<ResultDto> resultList = new ArrayList<ResultDto>();
		ResultDto resultDto = new ResultDto();
		for (Object itemObj : currentList) {

			if (itemObj instanceof Number) {
				continue;
			}

			String item = (String) itemObj;

			resultDto.setCurrentItem(item);

			double scoreZ = DETECTOR.getZawgyiProbability(item);
			resultDto.setScoreZ(scoreZ);

			if (scoreZ > 0.999) {
				String convertedItem = CONVERTER.convert(item);
				resultDto.setConvertedItem(convertedItem);
				resultList.add(resultDto);
			} else {
				resultDto.setConvertedItem(item);
			}
		}
		return resultList;
	}

	@Override
	public String convertAll(List<ResultDto> newItemList, String tableName, String columnName) {
		boolean hasError = false;
		String dbTableName = databaseName + "." + tableName;
		for (ResultDto item : newItemList) {
			String convertSql = "UPDATE " + dbTableName + " SET " + columnName + "=:newColumnItem WHERE " + columnName
					+ "=:oldColumnItem";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("newColumnItem", item.getConvertedItem());
			params.put("oldColumnItem", item.getCurrentItem());

			try {
				dao.executeSqlUpdate(convertSql, params);
			} catch (Exception e) {
				hasError = true;
				e.printStackTrace();
			}
		}

		if (hasError) {
			return "Failure!";
		}
		return "Success!";
	}

}
