Prerequisites

Java 17 or later

Springboot version 2.7.5+

Git (for cloning the repository)

IDE

Getting Started

Clone the repository

cd digital-market to go into the project root

Navigate to the application properties page, to view the mysql database configuration.

Go to the main class and run the application as a Java application

API LIST
Users :/USER

POST:/saveUser - to save a user

PUT:/updateUser - to update a user

POST:/login?emailId=?&password=? // this api will return a token, that token needs to be placed in the authorization bearer for future apis

Product: /PRODUCT

POST:/saveProduct - to save a product

PUT:/updateProduct - to update the product details

DELETE:/delete/{id} - to delete a product

GET:/priceRange - to get products between a price range

GET:/findByNameOrCategory - to filter products by their name or category

Shopping:/SHOPPING

POST:/addCart - to add a cart

PUT:/addCartItems - to add items in the cart

PUT:/modifyCartItems - to modify cart items

DELETE:/products/{id} - to delete items in a cart

GET:/viewCart - to view the current cart items


Order:/ORDER

POST:/placeOrder - to place your order

DELETE:/delete/{id} - to delete an order

GET:/payment/{id} - to pay for the order

ASSUMPTIONS:

The APIs will be called in a sequential manner


