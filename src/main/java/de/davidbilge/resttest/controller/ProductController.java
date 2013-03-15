package de.davidbilge.resttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.davidbilge.resttest.model.Product;

@Controller
@RequestMapping("/api/products")
public class ProductController {

	private static List<Product> products = new ArrayList<>();

	{
		Multimap<String, String> bookletParams = ArrayListMultimap.create();
		bookletParams.put("format", "DINA4");
		bookletParams.put("format", "DINA5");
		bookletParams.put("format", "DINA5landscape");
		bookletParams.put("format", "DINlang");
		bookletParams.put("format", "Quad");
		bookletParams.put("paper", "135g_gloss__135g_gloss");
		bookletParams.put("paper", "135g_matte__135g_matte");
		bookletParams.put("color", "4/4");
		bookletParams.put("color", "1/1");
		bookletParams.put("pages", "4..56/4");
		bookletParams.put("binding", "saddle_stitch");
		bookletParams.put("binding", "ring_eyelet");
		products.add(new Product("booklet", bookletParams));

		Multimap<String, String> bcardParams = ArrayListMultimap.create();
		bookletParams.put("format", "bcard_landscape");
		bookletParams.put("format", "bcard_portrait");
		bookletParams.put("paper", "300g_matte");
		bookletParams.put("paper", "250g_handmade");
		bookletParams.put("color", "4/0");
		bookletParams.put("color", "4/4");
		products.add(new Product("bcard", bcardParams));

		products.add(new Product("hardcover", ArrayListMultimap.<String, String> create()));
		products.add(new Product("softcover", ArrayListMultimap.<String, String> create()));
		products.add(new Product("placard", ArrayListMultimap.<String, String> create()));
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		return products;
	}

	@ResponseBody
	@RequestMapping(value = "/{productName}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable("productName") String productName) {
		for (Product product : products) {
			if (product.getName().equalsIgnoreCase(productName)) {
				return product;
			}
		}

		throw new RuntimeException("Product with name \"" + productName + "\" does not exist");
	}

}
