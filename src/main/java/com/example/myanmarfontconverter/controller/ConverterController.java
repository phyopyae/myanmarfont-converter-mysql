package com.example.myanmarfontconverter.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.myanmarfontconverter.dto.ConverterDto;
import com.example.myanmarfontconverter.dto.ResultDto;
import com.example.myanmarfontconverter.service.ConverterService;

@Controller
public class ConverterController {
	
	@Autowired
	ConverterService service;
	
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/converter")
	public String converter(Model model) {
		List<String> tableList = service.getAllTableNames();
		model.addAttribute("tableList", tableList);
		model.addAttribute("converterDto", new ConverterDto());
		return "index";
	}
	
	@RequestMapping(value = "/converter", method = RequestMethod.POST)
	public String converterBy(Model model, @Valid @ModelAttribute ConverterDto dto,  BindingResult bindingResult) {
		
		List<String> tableList = service.getAllTableNames();
		model.addAttribute("tableList", tableList);
		
	    if (bindingResult.hasErrors()) {
	        return "index";
	    }

		List<String> itemList = service.getItems(dto.getTableName(), dto.getColumnName());
		
		List<ResultDto> newItemList = service.detectAndConvertUnicode(itemList);
		if (newItemList != null && newItemList.size()>0) {
			model.addAttribute("newList", newItemList);
		}
		
		model.addAttribute("selectedTable", dto.getTableName());
		model.addAttribute("selectedColumn", dto.getColumnName());
		model.addAttribute("converterDto", new ConverterDto());
		
		return "index";
	}
}
