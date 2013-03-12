package de.davidbilge.resttest.model;

import java.util.Map;

public class ProductConfiguration {
	private String productName;
	private Integer circulation;
	private DeliveryServiceType deliveryServiceType;
	private Map<String, String> productParameters;

	public ProductConfiguration() {
	}

	public ProductConfiguration(String productName, Integer circulation, DeliveryServiceType deliveryServiceType,
			Map<String, String> productParameters) {
		this.productName = productName;
		this.circulation = circulation;
		this.deliveryServiceType = deliveryServiceType;
		this.productParameters = productParameters;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getCirculation() {
		return circulation;
	}

	public void setCirculation(Integer circulation) {
		this.circulation = circulation;
	}

	public DeliveryServiceType getDeliveryServiceType() {
		return deliveryServiceType;
	}

	public void setDeliveryServiceType(DeliveryServiceType deliveryServiceType) {
		this.deliveryServiceType = deliveryServiceType;
	}

	public Map<String, String> getProductParameters() {
		return productParameters;
	}

	public void setProductParameters(Map<String, String> productParameters) {
		this.productParameters = productParameters;
	}

}
