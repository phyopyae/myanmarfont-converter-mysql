package com.example.myanmarfontconverter.service;

import java.util.List;

import com.example.myanmarfontconverter.dto.ResultDto;

public interface ConverterService {

	List<String> getAllTableNames();

	List<String> getAllColumnNames(String tableName);

	List<String> getItems(String tableName, String columnName);

	List<ResultDto> detectAndConvertUnicode(List<String> currentList);

	String convertAll(List<ResultDto> newItemList, String tableName, String columnName);

}
