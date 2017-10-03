package com.mendeley.coding.service;

import com.mendeley.coding.dto.Input;
import com.mendeley.coding.dto.ShoppingBasket;

@FunctionalInterface
public interface Checkout {
	
	ShoppingBasket checkout(Input shoppingBasket);
}
