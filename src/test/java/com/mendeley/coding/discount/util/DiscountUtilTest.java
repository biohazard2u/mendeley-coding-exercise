package com.mendeley.coding.discount.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mendeley.coding.discount.api.Discount;
import com.mendeley.coding.discount.impl.BuyTreeGetCheapestDiscount;
import com.mendeley.coding.discount.impl.BuyTwoIdenticalReceivePromoPrice;
import com.mendeley.coding.discount.impl.FixDiscount;
import com.mendeley.coding.dto.ShoppingBasket;

public class DiscountUtilTest {

	@Test
	public void setDiscountsList() {
		List<Discount> actual = DiscountUtil.setDiscountsList(new FixDiscount());
		assertEquals(1, actual.size());
	}
	
	@Test
	public void setDiscountsList2List() {
		List<Discount> listToAdd = null;
		List<Discount> actual = DiscountUtil.setDiscountsList2List(new FixDiscount(), listToAdd);
		assertEquals(1, actual.size());
		
		DiscountUtil.setDiscountsList2List(new FixDiscount(), actual);
		assertEquals(2, actual.size());
	}
	
	@Test
	public void basketContainsDiscountByDescription(){
		ShoppingBasket basket = new ShoppingBasket();
		List<Discount> discounts = new ArrayList<>();
		discounts.add(new FixDiscount());
		basket.setDiscounts(discounts);
		
		assertTrue(DiscountUtil.basketContainsDiscountByDescription(basket , FixDiscount.FIXDISCOUNT_DESCRIPTION));
		assertFalse(DiscountUtil.basketContainsDiscountByDescription(basket , BuyTreeGetCheapestDiscount.BUY3CHEAPESTDISCOUNT_DESCRIPTION));
		assertFalse(DiscountUtil.basketContainsDiscountByDescription(basket , BuyTwoIdenticalReceivePromoPrice.BUY2IDENTICALDISCOUNT_DESCRIPTION));
	}
}
