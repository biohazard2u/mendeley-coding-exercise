package com.mendeley.coding.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mendeley.coding.discount.impl.FixDiscount;
import com.mendeley.coding.dto.Input;
import com.mendeley.coding.dto.ShoppingBasket;
import com.mendeley.coding.model.Item;

public class CheckoutServiceTest {

	CheckoutService sut;
	
	List<Item> items;
	Input input;
	
	@Before
	public void setUp(){
		sut = new CheckoutService();
		items = new ArrayList<>();
		input = new Input();
		input = new Input();
	}
	
	@Test
	public void checkout_notDiscounts_GetsOutput() {		
		items.add(new Item("meatballs", new BigDecimal("3")));
		items.add(new Item("chorizo", new BigDecimal("5")));
		items.add(new Item("lentils", new BigDecimal("1")));
		input.setItems(items);
		input.setItems(items);
		
		ShoppingBasket output = sut.checkout(input);
		
		assertEquals(output.getItems().size(), items.size());
		assertEquals(new BigDecimal(7.5), output.getTotal());
		assertEquals(FixDiscount.FIXDISCOUNT_DESCRIPTION, output.getDiscounts().get(0).getDescription());
	}
}