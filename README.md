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
//other apis coming soon
