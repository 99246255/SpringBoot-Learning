package com.camunda.demo.springboot.kafka;

import java.io.Serializable;
import java.util.Date;

public class Data implements Serializable{
	
	private Long barcode;

	public Data() {
	}

	public Data(Long barcode) {
		this.barcode = barcode;
	}

	public Long getBarcode() {
		return barcode;
	}

	public void setBarcode(Long barcode) {
		this.barcode = barcode;
	}
}