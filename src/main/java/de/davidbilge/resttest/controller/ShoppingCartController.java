package de.davidbilge.resttest.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.davidbilge.resttest.model.ShoppingCart;
import de.davidbilge.resttest.persistence.ShoppingCartPersistence;

@Controller
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
	
	@Resource
	private ShoppingCartPersistence shoppingCartPersistence;
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ShoppingCart read(@PathVariable final long id) {
		return shoppingCartPersistence.findShoppingCart(id);
	}
}
