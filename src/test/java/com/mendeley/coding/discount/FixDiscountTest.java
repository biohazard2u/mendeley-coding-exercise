package com.mendeley.coding.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mendeley.coding.discount.impl.BuyTreeGetCheapestDiscount;
import com.mendeley.coding.discount.impl.FixDiscount;
import com.mendeley.coding.discount.util.DiscountUtil;
import com.mendeley.coding.dto.ShoppingBasket;
import com.mendeley.coding.model.Item;

public class FixDiscountTest {

	FixDiscount sut;

	ShoppingBasket basketInput;
	List<Item> items;

	@Before
	public void setUp() {
		sut = new FixDiscount();
		DiscountUtil.setFinalChain(sut);
		
		basketInput = new ShoppingBasket();
		items = new ArrayList<>();
		
		Item item1 = new Item("chorizo", new BigDecimal(3));
		items.add(item1);
		basketInput.setItems(items);
	}

	@Test
	public void applyDiscount_ifBasketHasOtherDiscount_noFixDiscountApplied() {
		basketInput.setDiscounts(DiscountUtil.setDiscountsList(new BuyTreeGetCheapestDiscount()));

		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertFalse(DiscountUtil.basketContainsDiscountByDescription(actualBasketOutput, FixDiscount.FIXDISCOUNT_DESCRIPTION));
		assertTrue(DiscountUtil.basketContainsDiscountByDescription(actualBasketOutput, BuyTreeGetCheapestDiscount.BUY3CHEAPESTDISCOUNT_DESCRIPTION));
		assertEquals(1, actualBasketOutput.getItems().size());
	}
	
	@Test
	public void applyDiscount_ifBasketNoOtherDiscount_fixDiscountApplied() {
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertTrue(DiscountUtil.basketContainsDiscountByDescription(actualBasketOutput, FixDiscount.FIXDISCOUNT_DESCRIPTION));
		assertEquals(1, actualBasketOutput.getItems().size());
	}
	
	@Test
	public void applyDiscount_ifFixDiscountApplied_ShoppingBasketProperlyCalculated() {
		Item item2 = new Item("lentils", new BigDecimal(1));
		Item item3 = new Item("beans", new BigDecimal(2));
		items.add(item2);
		items.add(item3);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertEquals(new BigDecimal(4.5), actualBasketOutput.getTotal());
		assertEquals(3, actualBasketOutput.getItems().size());
	}

	@Test
	public void applyDiscount_ifFixDiscountNotApplied_ShoppingBasketProperlyCalculated() {
		basketInput.setDiscounts(DiscountUtil.setDiscountsList(new BuyTreeGetCheapestDiscount()));
		Item item2 = new Item("lentils", new BigDecimal(1));
		Item item3 = new Item("beans", new BigDecimal(2));
		items.add(item2);
		items.add(item3);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertEquals(new BigDecimal(6), actualBasketOutput.getTotal());
		assertEquals(3, actualBasketOutput.getItems().size());
	}
}
