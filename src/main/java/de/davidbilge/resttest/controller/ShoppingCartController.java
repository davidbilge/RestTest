package de.davidbilge.resttest.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.davidbilge.resttest.model.ShoppingCart;
import de.davidbilge.resttest.persistence.ShoppingCartPersistence;

@Controller
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartController.class);

	@Resource
	private ShoppingCartPersistence shoppingCartPersistence;

	@Secured("ROLE_ACCESS")
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ShoppingCart read() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		LOGGER.info("Authenticated user name: " + userName);

		return shoppingCartPersistence.findShoppingCart(userName);
	}
}
