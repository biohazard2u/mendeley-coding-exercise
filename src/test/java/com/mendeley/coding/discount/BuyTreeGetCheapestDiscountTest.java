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
import com.mendeley.coding.discount.util.DiscountUtil;
import com.mendeley.coding.dto.ShoppingBasket;
import com.mendeley.coding.model.Item;

public class BuyTreeGetCheapestDiscountTest {

	BuyTreeGetCheapestDiscount sut;

	ShoppingBasket basketInput;
	List<Item> items;

	@Before
	public void setUp() {
		sut = new BuyTreeGetCheapestDiscount();
		DiscountUtil.setFinalChain(sut);
		
		basketInput = new ShoppingBasket();
		items = new ArrayList<>();
		
		Item item1 = new Item("cereal", new BigDecimal(1));
		Item item2 = new Item("cereal", new BigDecimal(2));
		Item item3 = new Item("beans", new BigDecimal(2));
		
		items.add(item1);
		items.add(item2);
		items.add(item3);
		
		basketInput.setItems(items);
	}

	@Test
	public void applyDiscount_ifItemless3Times_noBuyTreeGetCheapestDiscountApplied() {
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertFalse(DiscountUtil.basketContainsDiscountByDescription(actualBasketOutput, BuyTreeGetCheapestDiscount.BUY3CHEAPESTDISCOUNT_DESCRIPTION));
		assertEquals(3, actualBasketOutput.getItems().size());
	}
	
	@Test
	public void applyDiscount_ifItem3Times_BuyTreeGetCheapestDiscountApplied() {
		Item item4 = new Item("cereal", new BigDecimal(3));
		items.add(item4);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertTrue(DiscountUtil.basketContainsDiscountByDescription(actualBasketOutput, BuyTreeGetCheapestDiscount.BUY3CHEAPESTDISCOUNT_DESCRIPTION));
		assertEquals(4, actualBasketOutput.getItems().size());
	}
	
	@Test
	public void applyDiscount_ifBuyTreeGetCheapestDiscountApplied_ShoppingBasketProperlyCalculated() {
		Item item4 = new Item("cereal", new BigDecimal(4));
		Item item5 = new Item("beans", new BigDecimal(4));
		items.add(item4);
		items.add(item5);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertEquals(new BigDecimal(12), actualBasketOutput.getTotal());
		assertEquals(5, actualBasketOutput.getItems().size());
	}

	@Test
	public void applyDiscount_ifBuyTreeGetCheapestDiscountNotApplied_ShoppingBasketProperlyCalculated() {
		basketInput.setDiscounts(DiscountUtil.setDiscountsList(new BuyTreeGetCheapestDiscount()));
		Item item4 = new Item("lentils", new BigDecimal(1));
		Item item5 = new Item("beans", new BigDecimal(4));
		items.add(item4);
		items.add(item5);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertEquals(new BigDecimal(10), actualBasketOutput.getTotal());
		assertEquals(5, actualBasketOutput.getItems().size());
	}
	
	@Test
	public void applyDiscount_ifBuyTreeGetCheapestDiscountTwicetApplied_ShoppingBasketProperlyCalculated() {
		basketInput.setDiscounts(DiscountUtil.setDiscountsList(new BuyTreeGetCheapestDiscount()));
		Item item4 = new Item("cereal", new BigDecimal(1));
		Item item5 = new Item("beans", new BigDecimal(2));
		Item item6 = new Item("beans", new BigDecimal(4));
		Item item7 = new Item("lentils", new BigDecimal(1));
		
		items.add(item4);
		items.add(item5);
		items.add(item6);
		items.add(item7);
		basketInput.setItems(items);
		
		ShoppingBasket actualBasketOutput = sut.applyDiscount(basketInput);

		assertEquals(new BigDecimal(10), actualBasketOutput.getTotal());
		assertEquals(7, actualBasketOutput.getItems().size());
	}
}
