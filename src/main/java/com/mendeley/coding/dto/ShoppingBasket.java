package com.mendeley.coding.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mendeley.coding.discount.api.Discount;
import com.mendeley.coding.model.Item;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Output should include:
 * 	- total price of the items, 
 *  - a list of the items bought and 
 *  - any special offers applied.
 * 
 * @author Marcos
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Checkout output")
public class ShoppingBasket extends ResourceSupport {


	@ApiModelProperty(value = "Total price of all items")
	@JsonProperty("total")
	private BigDecimal total;
	
	@ApiModelProperty(value = "Items in the basket")
	@JsonProperty("items in the basket")
	private List<Item> items;
	
	@ApiModelProperty(value = "Offers applied")
	@JsonProperty("offers applied")
	private List<Discount> discounts;
}