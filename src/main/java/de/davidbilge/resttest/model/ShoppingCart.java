package de.davidbilge.resttest.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	private List<Item> items = new ArrayList<>();

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		
		items.add(item);
	}

}
