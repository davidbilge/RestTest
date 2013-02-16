package de.davidbilge.resttest.persistence;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import de.davidbilge.resttest.model.Item;
import de.davidbilge.resttest.model.ShoppingCart;

@Component
public class ShoppingCartPersistence {

	private Map<Long, ShoppingCart> carts = new HashMap<>();

	@PostConstruct
	public void addDemoCarts()  {
		ShoppingCart s = new ShoppingCart();
		
		Item i = new Item();
		i.setName("Fancy product");
		i.setPrice(new BigDecimal("23.42"));
		s.addItem(i);
		
		i = new Item();
		i.setName("Another product");
		i.setCurrency(Currency.getInstance("USD"));
		i.setPrice(new BigDecimal("13.37"));
		s.addItem(i);
		
		carts.put(1L, s);
	}
	
	public ShoppingCart findShoppingCart(Long id) {
		return carts.get(id);
	}

	public Long addShoppingCart(ShoppingCart shoppingCart) {
		long newId = carts.size() + 1;

		while (carts.containsKey(newId)) {
			newId++;
		}

		carts.put(newId, shoppingCart);
		return newId;
	}

}
