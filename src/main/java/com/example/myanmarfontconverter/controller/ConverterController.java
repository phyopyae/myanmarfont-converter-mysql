package com.example.myanmarfontconverter.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.myanmarfontconverter.dto.ConfigDto;
import com.example.myanmarfontconverter.dto.ConverterDto;
import com.example.myanmarfontconverter.dto.ResultDto;
import com.example.myanmarfontconverter.service.ConfigService;
import com.example.myanmarfontconverter.service.ConverterService;

@Controller
public class ConverterController {

	@Autowired
	ConverterService service;
	
	@Autowired
	ConfigService configService;

	@RequestMapping(value = "/")
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/config")
	public String config(Model model) {
		ConfigDto config = configService.findConfig();
		if (config != null) {
			model.addAttribute("configDto", config);
		} else {
			model.addAttribute("configDto", new ConfigDto());
		}

		return "config";
	}

	@RequestMapping(value = "/converter")
	public String converter(Model model, HttpSession session) {
		ConfigDto config = configService.findConfig();
		if (config == null) {
			model.addAttribute("error", "Please set database name in config");
			return "index";
		}
		
		List<String> tableList = service.getAllTableNames(config);
		model.addAttribute("tableList", tableList);
		model.addAttribute("converterDto", new ConverterDto());
		session.setAttribute("config", config);
		return "index";
	}

	@RequestMapping(value = "/converter", method = RequestMethod.POST)
	public String converterBy(Model model, HttpSession session, @Valid @ModelAttribute ConverterDto dto, BindingResult bindingResult) {
		ConfigDto config = (ConfigDto) session.getAttribute("config");
		
		List<String> tableList = service.getAllTableNames(config);
		model.addAttribute("tableList", tableList);

		if (bindingResult.hasErrors()) {
			return "index";
		}

		List<String> itemList = service.getItems(dto.getTableName(), dto.getColumnName(), config);

		List<ResultDto> newItemList = service.detectAndConvertUnicode(itemList);
		if (newItemList != null && newItemList.size() > 0) {
			model.addAttribute("newList", newItemList);
		}

		model.addAttribute("selectedTable", dto.getTableName());
		model.addAttribute("selectedColumn", dto.getColumnName());
		model.addAttribute("converterDto", new ConverterDto());

		return "index";
	}
	
	@RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
	public String saveConfig(Model model, HttpSession session, @Valid @ModelAttribute ConfigDto dto, BindingResult bindingResult) {
		
		configService.update(dto);
		return "redirect:/config";
	}
}
