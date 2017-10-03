package com.mendeley.coding.discount.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mendeley.coding.discount.api.Discount;
import com.mendeley.coding.discount.util.DiscountUtil;
import com.mendeley.coding.dto.ShoppingBasket;
import com.mendeley.coding.model.Item;

import lombok.Getter;
import lombok.Setter;

/**
 * Buy 2 identical items receive a promotional price(e.g. buy 2 for £2)
 * 
 * @author Marcos
 */
@Getter
@Setter
public class BuyTwoIdenticalReceivePromoPrice implements Discount {

	public static final String BUY2IDENTICALDISCOUNT_DESCRIPTION = "Buy 2 Identical Promo";

	private static final BigDecimal PROMO_PRICE = BigDecimal.valueOf(0.25);

	private String description = BUY2IDENTICALDISCOUNT_DESCRIPTION;

	@JsonIgnore
	private Discount chain;

	@Override
	public void setNextChain(Discount nextChain) {
		this.chain = nextChain;
	}

	@Override
	public ShoppingBasket applyDiscount(ShoppingBasket basketInput) {
		ShoppingBasket basketOutput = basketInput;

		Set<Item> appeared = new HashSet<>();
		for (Item item : basketOutput.getItems()) {
			if (appeared.contains(item)) {
				item.setPrice(PROMO_PRICE);
				basketOutput.setDiscounts(DiscountUtil.setDiscountsList2List(new BuyTwoIdenticalReceivePromoPrice(),
						basketOutput.getDiscounts()));
			} else {
				appeared.add(item);
			}
		}

		BigDecimal sum = basketOutput.getItems().stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		basketOutput.setTotal(sum);

		return this.chain.applyDiscount(basketOutput);
	}
}
