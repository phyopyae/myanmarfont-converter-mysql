package com.example.myanmarfontconverter.dto;

import javax.validation.constraints.NotBlank;

public class ConverterDto {

	@NotBlank
	String tableName;

	@NotBlank
	String columnName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
