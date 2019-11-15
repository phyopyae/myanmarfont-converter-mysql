package com.example.myanmarfontconverter.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myanmarfontconverter.dto.ConfigDto;
import com.example.myanmarfontconverter.dto.ResultDto;
import com.example.myanmarfontconverter.service.ConfigService;
import com.example.myanmarfontconverter.service.ConverterService;

@RestController
public class APIController {

	@Autowired
	ConverterService service;
	
	@Autowired
	ConfigService configService;
	
    @PostMapping("/api/selectTable")
    public ResponseEntity<?> getSearchResultViaAjax(
            @Valid @RequestBody String selectedTable, Errors errors) {
    	ConfigDto config = configService.findConfig();
    	List<String> allColumnNames = service.getAllColumnNames(selectedTable, config);
		return ResponseEntity.ok(allColumnNames);
    }
    
    @PostMapping("/api/convertAll")
	public ResponseEntity<?> convertAll(@RequestBody LinkedHashMap<String, String> data, Errors errors) {
    	ConfigDto config = configService.findConfig();
    	String tableName = data.get("table");
    	String columnName = data.get("column");
    	String result = "";
    	
    	List<String> itemList = service.getItems(tableName, columnName, config);
    	
    	List<ResultDto> newItemList = service.detectAndConvertUnicode(itemList);
		if (newItemList != null && newItemList.size()>0) {
			result = service.convertAll(newItemList, tableName, columnName, config);
		}
		
		return ResponseEntity.ok(result);
	}
}
