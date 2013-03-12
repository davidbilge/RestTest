package de.davidbilge.resttest.model;

import com.google.common.collect.Multimap;

public class Product {
	private String name;
	private Multimap<String, String> parameters;

	public Product() {
	}

	public Product(String name, Multimap<String, String> parameters) {
		super();
		this.name = name;
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Multimap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Multimap<String, String> parameters) {
		this.parameters = parameters;
	}

}
