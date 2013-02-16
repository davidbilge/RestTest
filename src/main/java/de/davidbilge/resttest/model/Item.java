package de.davidbilge.resttest.model;

import java.math.BigDecimal;
import java.util.Currency;

public class Item {
	private String name;
	private Currency currency = Currency.getInstance("EUR");
	private BigDecimal price = BigDecimal.ZERO;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
