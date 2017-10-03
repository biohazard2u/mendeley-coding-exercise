package com.mendeley.coding.discount.impl;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mendeley.coding.discount.api.Discount;
import com.mendeley.coding.discount.util.DiscountUtil;
import com.mendeley.coding.dto.ShoppingBasket;
import com.mendeley.coding.model.Item;

import lombok.Getter;
import lombok.Setter;

/**
 * A fixed percentage off an item (e.g. 50% off)
 * We'll apply this discount if no other discounts have been applied.
 * This discount is applied only to the cheapest item in the basket.
 * 
 * <br><br><b>NOTE: This is now the final Discount on the chain of responsibility.</b><br>
 * @author Marcos
 */
@Getter
@Setter
public class FixDiscount implements Discount {

	public static final String FIXDISCOUNT_DESCRIPTION = "Fix Discount";

	private String description = FIXDISCOUNT_DESCRIPTION;

	@JsonIgnore
	private Discount chain;

	@Override
	public void setNextChain(Discount nextChain) {
		this.chain = nextChain;
	}

	@Override
	public ShoppingBasket applyDiscount(ShoppingBasket basketInput) {
		ShoppingBasket basketOutput = new ShoppingBasket();

		if (basketInput.getDiscounts() == null || basketInput.getDiscounts().isEmpty()) {
			basketOutput.setItems(basketInput.getItems());			
			basketOutput.setDiscounts(DiscountUtil.setDiscountsList(new FixDiscount()));
			basketOutput.setTotal(calculateTotal(basketInput));
		} else{
			basketOutput = basketInput;
			BigDecimal sum = basketInput.getItems().stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
			basketOutput.setTotal(sum);
		}
		this.chain.applyDiscount(basketOutput);
		return basketOutput;
	}

	private BigDecimal calculateTotal(ShoppingBasket basketInput) {
		BigDecimal i = basketInput.getItems().stream().findFirst().get().getPrice().multiply(BigDecimal.valueOf(0.5));
		basketInput.getItems().stream().findFirst().get().setPrice(i);
		return basketInput.getItems().stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
