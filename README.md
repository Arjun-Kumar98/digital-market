# Digital Market
It is a sample ecommerce market simulation.

## **Prerequisites**
- Java 17 or later
- Springboot version 2.7.5+
- Git (for cloning the repository)
- IDE (e.g., IntelliJ IDEA, Eclipse)

## **Getting Started**
1. Clone the repository
2. Open the repository in an IDE
3. Navigate to the application properties page to view the MySQL database configuration.
4. Go to the main class and run the application as a Java application.

## **API List**

### **Users** (`/USER`)
- **POST**: `/saveUser`  
  To save a user.
  
- **PUT**: `/updateUser`  
  To update a user.

- **POST**: `/login?emailId=?&password=?`  
  This API returns a token. Use this token in the authorization bearer for future API requests.

---

### **Product** (`/PRODUCT`)
- **POST**: `/saveProduct`  
  To save a product.

- **PUT**: `/updateProduct`  
  To update the product details.

- **DELETE**: `/delete/{id}`  
  To delete a product.

- **GET**: `/priceRange`  
  To get products within a price range.

- **GET**: `/findByNameOrCategory`  
  To filter products by name or category.

---

### **Shopping** (`/SHOPPING`)
- **POST**: `/addCart`  
  To create a new cart.

- **PUT**: `/addCartItems`  
  To add items to the cart.

- **PUT**: `/modifyCartItems`  
  To modify cart items.

- **DELETE**: `/products/{id}`  
  To delete items in the cart.

- **GET**: `/viewCart`  
  To view the current cart items.

---

### **Order** (`/ORDER`)
- **POST**: `/placeOrder`  
  To place your order.

- **DELETE**: `/delete/{id}`  
  To delete an order.

- **GET**: `/payment/{id}`  
  To process payment for an order.




