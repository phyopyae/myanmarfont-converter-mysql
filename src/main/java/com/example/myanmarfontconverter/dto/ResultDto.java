package com.example.myanmarfontconverter.dto;

public class ResultDto {

	String currentItem;

	double scoreZ;

	double scoreU;

	String convertedItem;

	public String getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(String currentItem) {
		this.currentItem = currentItem;
	}

	public double getScoreZ() {
		return scoreZ;
	}

	public void setScoreZ(double scoreZ) {
		this.scoreZ = scoreZ;
	}

	public double getScoreU() {
		return scoreU;
	}

	public void setScoreU(double scoreU) {
		this.scoreU = scoreU;
	}

	public String getConvertedItem() {
		return convertedItem;
	}

	public void setConvertedItem(String convertedItem) {
		this.convertedItem = convertedItem;
	}
}
