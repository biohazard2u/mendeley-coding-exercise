package com.mendeley.coding.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mendeley.coding.model.Item;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The application should read a list of items and prices from a JSON file
 * 
 * @author Marcos
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Checkout input")
public class Input {

	@ApiModelProperty(value = "List of items in shopping basket")
	@JsonProperty("items")
	private List<Item> items = new ArrayList<>();
}