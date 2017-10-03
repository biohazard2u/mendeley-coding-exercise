package com.mendeley.coding.discount.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mendeley.coding.discount.api.Discount;
import com.mendeley.coding.discount.util.DiscountUtil;
import com.mendeley.coding.dto.ShoppingBasket;
import com.mendeley.coding.model.Item;

import lombok.Getter;
import lombok.Setter;

/**
 * Buy 3 items of a type, get the cheapest free (e.g. buy three boxes of cereal,
 * get the cheapest free)
 * 
 * @author Marcos
 */
@Getter
@Setter
public class BuyTreeGetCheapestDiscount implements Discount {

	public static final String BUY3CHEAPESTDISCOUNT_DESCRIPTION = "Buy 3 get cheapest Discount";

	private String description = BUY3CHEAPESTDISCOUNT_DESCRIPTION;

	@JsonIgnore
	private Discount chain;

	@Override
	public void setNextChain(Discount nextChain) {
		this.chain = nextChain;
	}

	@Override
	public ShoppingBasket applyDiscount(ShoppingBasket basket) {
		
		// TODO: get the cheapest free,not the first one.
		basket.getItems().stream()
			.filter(i -> Collections.frequency(basket.getItems(), i) > 2)
			.collect(Collectors.toSet())
			.forEach(i -> i.setItemFree());
		
		List<Item> items = basket.getItems();
		for (Item item : items) {
			if(item.getPrice().equals(BigDecimal.ZERO))
				basket.setDiscounts(DiscountUtil.setDiscountsList2List(new BuyTreeGetCheapestDiscount(), basket.getDiscounts()));
		}
			
		BigDecimal sum = basket.getItems().stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		basket.setTotal(sum);
		
		return this.chain.applyDiscount(basket);
	}

}
