package com.example.myanmarfontconverter.service;

import java.util.List;
import java.util.Map;

import com.example.myanmarfontconverter.dto.ConfigDto;

public interface ConfigService {

	ConfigDto findConfig();

	void update(ConfigDto dto);

	Map<String, Object> save(ConfigDto dto);

}
