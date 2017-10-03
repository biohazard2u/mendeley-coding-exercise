package com.mendeley.coding.discount.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mendeley.coding.discount.api.Discount;
import com.mendeley.coding.discount.impl.BuyTreeGetCheapestDiscount;
import com.mendeley.coding.discount.impl.EndOfChainDiscount;
import com.mendeley.coding.discount.impl.FixDiscount;
import com.mendeley.coding.dto.ShoppingBasket;

public class DiscountUtil {
	
	private DiscountUtil() {
	}

	public static List<Discount> setDiscountsList(Discount discount) {
		List<Discount> discounts = new ArrayList<>();
		discounts.add(discount);
		return discounts;
	}
	
	public static List<Discount> setDiscountsList2List(Discount discount, List<Discount> discounts) {
		List<Discount> result = discounts != null ? discounts : new ArrayList<>();
		result.add(discount);
		return result;
	}
	
	/**
	 * This method is being used during testing. The name is self-explanatory.
	 * @param sut
	 */
	public static Boolean basketContainsDiscountByDescription(ShoppingBasket basket, String discount2Find) {
		if(basket.getDiscounts() == null)
			return false;
		
		Optional<Discount> optional = basket.getDiscounts().stream()
				.filter(x -> discount2Find.equals(x.getDescription())).findFirst();
		return optional.isPresent();
	}
	
	/**
	 * This method is being used during testing, it ensures that discount tests are unit tests.
	 * @param sut
	 */
	public static void setFinalChain(Discount sut) {
		sut.setNextChain(new EndOfChainDiscount());	// Setting a final chain
	}
}