package com.mendeley.coding.discount.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mendeley.coding.discount.api.Discount;
import com.mendeley.coding.dto.ShoppingBasket;

import lombok.Getter;
import lombok.Setter;

/**
 * Not a real discount. It's just a way to mark the end of the chain on all discounts.
 * 
 * @author Marcos
 */
@Getter
@Setter
public class EndOfChainDiscount implements Discount {

	public static final String END_OF_CHAIN = "End of chain";

	private String description = END_OF_CHAIN;

	@JsonIgnore
	private Discount chain;

	@Override
	public void setNextChain(Discount nextChain) {
		this.chain = nextChain;
	}

	@Override
	public ShoppingBasket applyDiscount(ShoppingBasket basketInput) {
		return basketInput;
	}
}