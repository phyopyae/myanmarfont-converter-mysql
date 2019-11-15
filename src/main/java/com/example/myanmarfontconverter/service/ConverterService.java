package com.example.myanmarfontconverter.service;

import java.util.List;

import com.example.myanmarfontconverter.dto.ConfigDto;
import com.example.myanmarfontconverter.dto.ResultDto;

public interface ConverterService {

	List<String> getAllTableNames(ConfigDto config);

	List<String> getAllColumnNames(String tableName, ConfigDto config);

	List<String> getItems(String tableName, String columnName, ConfigDto config);

	List<ResultDto> detectAndConvertUnicode(List<String> currentList);

	String convertAll(List<ResultDto> newItemList, String tableName, String columnName, ConfigDto config);

}
