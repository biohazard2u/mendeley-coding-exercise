package com.mendeley.coding.discount.api;

import com.mendeley.coding.dto.ShoppingBasket;

/**
 * Discounts are calculated based on rules defined in the system, and should be applied automatically.
 * 
 * @author Marcos
 */
public interface Discount {
	
	void setNextChain(Discount nextChain);
	
	ShoppingBasket applyDiscount(ShoppingBasket output);

	String getDescription();
}
