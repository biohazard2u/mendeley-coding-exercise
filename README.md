################################
## Mendeley Coding Exercise  ###
################################
Please write a console application that simulates a simple supermarket checkout. It needs to meet the following requirements:

	* The application should read a list of items and prices from a JSON file. Please include an example input file.
	* From the list of items and prices, the application should calculate the total price, including any discounts.

	* Discounts are calculated based on rules defined in the system, and should be applied automatically. The following types of discount should be calculated:
		* A fixed percentage off an item (e.g. 50% off)
		* Buy 3 items of a type, get the cheapest free (e.g. buy three boxes of cereal, get the cheapest free)
		* Buy 2 identical items receive a promotional price(e.g. buy 2 for £2)

	* The application should output the total price of the items, a list of the items bought and any special offers applied

	* It should be easy to add more special offer rules
	* The application should be appropriately tested
	* A readme file should be included to explain project usage, and how to add more special offers.

We expect you to take 2 to 4 hours to complete this exercise. 
There is some code provided to you already to start you off - feel free to change/adapt anything you deem necessary.
Please provide your completed code as a zip file to your contact.



################################
### Running the application  ###
################################
This is an Springboot application - REST microservice.
 - To build the application do: mvn clean install
 - To run the application do: mvn spring-boot:run
 - The app has been set with swagger, to try the app just go to: http://localhost:8080/swagger-ui.html
 	JSON examples can be seen in swagger.
 - Chain of responsability pattern has been used to allow an easier introduction of more discounts.
 	To add another discount, a new class implementeting the Discount interface needs to be created.
 	The new discount needs to be added to chain in service bean.
 	Right now the class needs to override the setNextChain method accordinly.
 	
 TODOs:	 (Due to not enough time -> "We expect you to take 2 to 4 hours to complete this exercise.")
 - A better tuning of the discounts implementation is needed, I needed more time for that. 
 - I also wanted to add some emoticons in the tests :) , I'm using JUnit5 after all.
 
 
 
################
### Example  ###
################
 
 REQUEST:
	 curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ 
	   "items": [
	     {
	       "name": "apple",
	       "price": 3
	     }, 
	     {
	       "name": "pear",
	       "price": 2
	     },
	     {
	       "name": "pear",
	       "price": 2
	     } 
	   ]
	 }' 'http://localhost:8080/checkout'

RESPONSE:
	{
	  "total": 5.25,
	  "items in the basket": [
	    {
	      "name": "apple",
	      "price": 3
	    },
	    {
	      "name": "pear",
	      "price": 2
	    },
	    {
	      "name": "pear",
	      "price": 0.25
	    }
	  ],
	  "offers applied": [
	    {
	      "description": "Buy 2 Identical Promo"
	    }
	  ]
	}
