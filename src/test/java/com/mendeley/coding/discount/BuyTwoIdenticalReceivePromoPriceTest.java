package com.mendeley.coding.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mendeley.coding.discount.impl.BuyTwoIdenticalReceivePromoPrice;
import com.mendeley.coding.discount.util.DiscountUtil;
import com.mendeley.coding.dto.ShoppingBasket;
import com.mendeley.coding.model.Item;

/**
 * Buy 2 identical items receive a promotional price(e.g. buy 2 for £2)
 * 
 * @author Marcos
 */
public class BuyTwoIdenticalReceivePromoPriceTest {

	BuyTwoIdenticalReceivePromoPrice sut;

	ShoppingBasket basketInput;
	List<Item> items;

	@Before
	public void setUp() {
		sut = new BuyTwoIdenticalReceivePromoPrice();
		DiscountUtil.setFinalChain(sut);
		
		basketInput = new ShoppingBasket();
		items = new ArrayList<>();

		Item item1 = new Item("chorizo", new BigDecimal(3));
		items.add(item1);
		basketInput.setItems(items);
	}

	@Test
	public void applyDiscount_1item_ifNo2Identical_NoPromoPriceApplied() {
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertFalse(DiscountUtil.basketContainsDiscountByDescription(actualBasketOutput, BuyTwoIdenticalReceivePromoPrice.BUY2IDENTICALDISCOUNT_DESCRIPTION));
		assertEquals(1, actualBasketOutput.getItems().size());
	}
	
	@Test
	public void applyDiscount_3item_ifNo2Identical_NoPromoPriceApplied() {
		Item item1 = new Item("cereal", new BigDecimal(1));
		Item item2 = new Item("milk", new BigDecimal(2));
		items.add(item1);
		items.add(item2);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertFalse(DiscountUtil.basketContainsDiscountByDescription(actualBasketOutput, BuyTwoIdenticalReceivePromoPrice.BUY2IDENTICALDISCOUNT_DESCRIPTION));
		assertEquals(3, actualBasketOutput.getItems().size());
	}
	
	@Test
	public void applyDiscount_if2Identical_BuyTwoIdenticalReceivePromoPriceApplied() {
		Item item1 = new Item("cereal", new BigDecimal(1));
		Item item2 = new Item("chorizo", new BigDecimal(3));
		items.add(item1);
		items.add(item2);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertTrue(DiscountUtil.basketContainsDiscountByDescription(actualBasketOutput, BuyTwoIdenticalReceivePromoPrice.BUY2IDENTICALDISCOUNT_DESCRIPTION));
		assertEquals(3, actualBasketOutput.getItems().size());
	}
	
	@Test
	public void applyDiscount_withNoPromoPriceApplied_goodResult() {
		Item item1 = new Item("cereal", new BigDecimal(1));
		Item item2 = new Item("cat food", new BigDecimal(3));
		items.add(item1);
		items.add(item2);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertEquals(BigDecimal.valueOf(7), actualBasketOutput.getTotal());
	}
	
	@Test
	public void applyDiscount_withPromoPriceApplied_goodResult() {
		Item item1 = new Item("cereal", new BigDecimal(1));
		Item item2 = new Item("cereal", new BigDecimal(3));
		items.add(item1);
		items.add(item2);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertEquals(BigDecimal.valueOf(4.25), actualBasketOutput.getTotal());
	}
}
