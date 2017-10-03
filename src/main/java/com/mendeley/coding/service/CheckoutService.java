package com.mendeley.coding.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mendeley.coding.controller.CheckoutController;
import com.mendeley.coding.discount.api.Discount;
import com.mendeley.coding.discount.impl.BuyTreeGetCheapestDiscount;
import com.mendeley.coding.discount.impl.BuyTwoIdenticalReceivePromoPrice;
import com.mendeley.coding.discount.impl.EndOfChainDiscount;
import com.mendeley.coding.discount.impl.FixDiscount;
import com.mendeley.coding.dto.Input;
import com.mendeley.coding.dto.ShoppingBasket;

@Service
public class CheckoutService implements Checkout {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutController.class);
		
	public ShoppingBasket checkout(Input input) {

		ShoppingBasket output = new ShoppingBasket();
		
		LOGGER.debug("adding items");
		output.setItems(input.getItems());
		
		LOGGER.debug("handlying discounts");
		Discount discount1 = new BuyTwoIdenticalReceivePromoPrice();
		Discount discount2 = new BuyTreeGetCheapestDiscount();
		Discount discount3 = new FixDiscount();
		Discount endOfChain = new EndOfChainDiscount();
		
		LOGGER.debug("setting chain");
		discount1.setNextChain(discount2);
		discount2.setNextChain(discount3);
		discount3.setNextChain(endOfChain);
		
		LOGGER.debug("starting chain of responsability");
		return discount1.applyDiscount(output);
	}
}
