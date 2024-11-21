## rewards-calculator-service
The Reward Points System is a Spring Boot-based application designed to manage purchases and reward points for customers. It includes robust exception handling, unit testing, and follows clean coding practices.

# Features
**Customer Management**: Add and fetch customer details.

**Product Management**: Add and fetch product details.

**Purchase System**: Manage customer purchases and reward points.

**Reward Points Calculation**: Automated reward points calculation based on purchase value.

**Error Handling**: Comprehensive exception handling for database operations, invalid requests.

# Technologies Used
1. Spring Boot
2. JUnit 5 & Mockito (Testing Framework)
3. Lombok (For reducing boilerplate code)
4. Maven (Dependency Management)
5. H2 Database (In-memory Database for Testing)

# How to Run the Project
**Clone the Repository:**

```
git clone <repository_url>
cd <project_directory>
```

# Build the Project:

```
mvn clean install
```

# Run the Application:

```
mvn spring-boot:run
```

# Access the APIs:

Open http://localhost:8080 in your browser or use Postman to interact with the API.

# Endpoints
**Customer**
1. **Add customer** : http://localhost:8080/api/customer/add
2. **Get All customers** : http://localhost:8080/api/customer/getAll
3. **Add customers in bulk** : http://localhost:8080/api/customer/bulk-add
4. **Sample Data Set for bulk add**
```
[
    {
  "firstName": "Dhanush",
  "lastName": "B",
  "email": "dannyb@email.com",
  "contact": "1302983967"
    },
    {
  "firstName": "John",
  "lastName": "Doe",
  "email": "johndoe@email.com",
  "contact": "8883933022"
    },
    {
  "firstName": "James",
  "lastName": "Bond",
  "email": "bond@email.com",
  "contact": "0070070070"
    },
    {
  "firstName": "Harry",
  "lastName": "Potter",
  "email": "notamuggle@email.com",
  "contact": "8889990006"
    },
    {
  "firstName": "Thor",
  "lastName": "Odin",
  "email": "thunder@email.com",
  "contact": "4446667779"
    }
]
```

**Category**
1. **Add category** : http://localhost:8080/api/category/add
2. **Get all categories** : http://localhost:8080/api/category/getAll
3. **Add categories in bulk** : http://localhost:8080/api/category/bulk-add
4. **Sample Data Set for bulk add**
```
[
    {
  "categoryName": "Electronics"
    },
    {
  "categoryName": "Clothing"
    },
    {
  "categoryName": "Food and Beverages"
    },
    {
  "categoryName": "Beauty Products"
    },
    {
  "categoryName": "Stationary"
    }
]
```

**Product**
1. **Add product** : http://localhost:8080/api/product/add
2. **Get all products** : http://localhost:8080/api/product/getAll
3. **Add products in bulk** : http://localhost:8080/api/product/bulk-add
4. **Sample Data Set for bulk add**
```
[
  {
    "productName": "Iphone",
    "price": "40000",
    "category": {
      "categoryId": "5"
    }
  },
  {
    "productName": "Macbook",
    "price": "100000",
    "category": {
      "categoryId": "5"
    }
  },
  {
    "productName": "Loreal Paris Shampoo",
    "price": "300",
    "category": {
      "categoryId": "4"
    }
  },
  {
    "productName": "Flying Machine Shirt",
    "price": "800",
    "category": {
      "categoryId": "2"
    }
  },
  {
    "productName": "Coke",
    "price": "90",
    "category": {
      "categoryId": "1"
    }
  },
  {
    "productName": "Lays",
    "price": "20",
    "category": {
      "categoryId": "1"
    }
  },
  {
    "productName": "Sugar Lipstick",
    "price": "300",
    "category": {
      "categoryId": "4"
    }
  },
  {
    "productName": "Colour book",
    "price": "100",
    "category": {
      "categoryId": "3"
    }
  },
  {
    "productName": "Pen Stand",
    "price": "80",
    "category": {
      "categoryId": "3"
    }
  }
]
```


**Purchase**
1. **Make purchase** : http://localhost:8080/api/purchase/makePurchase
2. **Sample Data Set**
```
{
  "customer": {
    "customerId": "2"
  },
  "products": [
    {
      "productId": "1"
    },
    {
      "productId": "4"
    },
    {
      "productId": "3"
    }
  ],
  "purchaseTime":"2024-09-15T12:00:00"
}

```
3. **Get All purchases** : http://localhost:8080/api/purchase/getAll

**Rewards**
1. **Get Rewards Summary** : http://localhost:8080/api/reward-points/get-summary
2. **Sample Data Set**
```
/*Based on the purchases made by customer we build the data set. The endpoint excepts a List of TransactionRequest objects which consists customerId and purchaseId fields. So if a customer with customerId 2 has three purchases with purchaseId 2,5 and 6 we can provide each combination as a particular request. Eg.
[
  {
    "customerId": "2",
    "purchaseId": "2"
  },
  {
    "customerId": "2",
    "purchaseId": "5"
  },
  {
    "customerId": "2",
    "purchaseId": "6"
  }
]
In the same way we can add other customerId and its corresponding and valid purchaseId as a particular TransactionRequest.
The result will be a RewardsSummaryResponse which consists of Customer name, Monthly rewards(If there are any purchases/rewards awarded for the particluar month ) and total rewards over the provided 3 months.
Below is just an example*/
[
  {
    "customerId": "5",
    "purchaseId": "1"
  },
  {
    "customerId": "4",
    "purchaseId": "2"
  },
  {
    "customerId": "4",
    "purchaseId": "4"
  },
  {
    "customerId": "1",
    "purchaseId": "3"
  }
]
```

# Folder Structure
```
src/main/java
├── com.homework.rewardpoints.reward
│   ├── controller          # API Controllers
│   ├── service             # Business Logic
│   ├── model               # Data Models
│   ├── repository          # Spring Data JPA Repositories
│   ├── exception           # Custom Exceptions
│   ├── dto                 # Request and Response DTOs
│   ├── utility             # Utility Classes (e.g., Calculators)

```
