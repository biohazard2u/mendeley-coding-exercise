package com.mendeley.coding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mendeley.coding.dto.Input;
import com.mendeley.coding.dto.ShoppingBasket;
import com.mendeley.coding.service.CheckoutService;

@RestController
@RequestMapping("checkout")
public class CheckoutController {

	@Autowired
	CheckoutService checkOutService; // This could be an interface.

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ShoppingBasket> checkout(@RequestBody(required = false) Input input) {

		ShoppingBasket output = checkOutService.checkout(input);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}
