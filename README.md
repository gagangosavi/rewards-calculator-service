## rewards-calculator-service
The Reward Points System is a Spring Boot-based application designed to manage purchases and reward points for customers. It includes exception handling, unit testing.

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
5. MySQL Database

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
**Category**

**Add category** : http://localhost:8080/api/category/add

**Sample Valid Request**

```
{
  "categoryName": "Electronics"
}
```

**Sample Valid Response**

```
{
    "categoryName": "Electronics"
}
```

**Sample Invalid Request - Category null**

```
{
  "categoryName": null
}
```

**Response**:

```
{
    "messages": {
        "categoryName": "Category name cannot be null"
    },
    "error": "Validation failure",
    "status": 400
}
```
**Get All categories** : http://localhost:8080/api/category/getAll

**Sample Response**

```
[
    {
        "categoryName": "Beauty Products"
    },
    {
        "categoryName": "Electronics"
    }
]
```

**Add categories in bulk** : http://localhost:8080/api/customer/bulk-add

**Sample Valid Request**

```
[
    {
  "categoryName": "Clothing"
    },
    {
  "categoryName": "Food and Beverages"
    },
    {
  "categoryName": "Stationary"
    }
]
```
**Response**

```
[
    {
        "categoryName": "Food and Beverages"
    },
    {
        "categoryName": "Stationary"
    },
    {
        "categoryName": "Clothing"
    }
]
```

**Customer**

**Add Customer** : http://localhost:8080/api/customer/add

**Sample Valid Request**

```
{
        "firstName": "John",
        "lastName": "Doe",
        "email": "johndoe@email.com",
        "contact": "888393"
    }
```
**Response**:

```
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "johndoe@email.com",
    "contact": "8883931234",
    "totalRewardPoints": 0
}
```

**Sample Invalid Request - First Name/Last Name blank**

```
{
        "firstName": "",
        "lastName": "",
        "email": "johndoe@email.com",
        "contact": "8883931234"
    }
```

**Response**
```
{
    "messages": {
        "firstName": "Customer first name cannot be blank",
        "lastName": "Customer last name cannot be blank"
    },
    "error": "Validation failure",
    "status": 400
}
```

**Sample Invalid Request - Inavlid email**
```
{
        "firstName": "John",
        "lastName": "Doe",
        "email": "johndoeemail",
        "contact": "8883931234"
    }
```

**Response**

```
{
    "messages": {
        "email": "Please provide a valid email"
    },
    "error": "Validation failure",
    "status": 400
}
```

**Sample Invalid Request - Inavlid Contact**

```
{
        "firstName": "John",
        "lastName": "Doe",
        "email": "johndoeemail",
        "contact": "88839"
    }
```

**Response**

```
{
    "messages": {
        "contact": "Please provide a valid contact",
        "email": "Please provide a valid email"
    },
    "error": "Validation failure",
    "status": 400
}
```


**Get all customers** : http://localhost:8080/api/customer/getAll\

**Response**

```
[
    {
        "firstName": "John",
        "lastName": "Doe",
        "email": "johndoe@email.com",
        "contact": "8883931234",
        "totalRewardPoints": 0
    },
    {
        "firstName": "Dhanush",
        "lastName": "B",
        "email": "dannyb@email.com",
        "contact": "1302983967",
        "totalRewardPoints": 0
    }
]
```

**Add customers in bulk** : http://localhost:8080/api/customer/bulk-add

**Sample Valid Request**

```
[
   
    
    {
        
        "firstName": "Harry",
        "lastName": "Potter",
        "email": "notamuggle@email.com",
        "contact": "8889990006"
    },
    {
        "firstName": "James",
        "lastName": "Bond",
        "email": "bond@email.com",
        "contact": "0070070070",
        
    },
    {
        
        "firstName": "Thor",
        "lastName": "Odin",
        "email": "thunder@email.com",
        "contact": "4446667779"
    }
]
```

**Response**

```
[
    {
        "firstName": "Harry",
        "lastName": "Potter",
        "email": "notamuggle@email.com",
        "contact": "8889990006",
        "totalRewardPoints": 0
    },
    {
        "firstName": "Thor",
        "lastName": "Odin",
        "email": "thunder@email.com",
        "contact": "4446667779",
        "totalRewardPoints": 0
    },
    {
        "firstName": "James",
        "lastName": "Bond",
        "email": "bond@email.com",
        "contact": "0070070070",
        "totalRewardPoints": 0
    }
]
```

**Sample Inavlid Request - Empty List**

```
[]
```

**Response**

```
Please provide at least one customer to be added
```

**Product**

**Add product** : http://localhost:8080/api/product/add

**Sample Valid Request**

```
{
    "productName": "Iphone",
    "price": "40000",
    "categoryRequest": {
        "categoryName" : "Electronics"
    }
  }
```

**Response**

```
{
    "productName": "Iphone",
    "price": 40000.0,
    "category": "Electronics"
}
```

**Sample Inavlid Request - Product name empty, price negative, categoryRequest null**

```
{
    "productName": "",
    "price": "-40000",
    "categoryRequest": null
  }
```

**Response**

```
{
    "messages": {
        "categoryRequest": "Category cannot be null",
        "price": "Price must be a positive value",
        "productName": "Product name must contain minimum of 1 and maximum of 100 characters"
    },
    "error": "Validation failure",
    "status": 400
}
```


**Get all products** : http://localhost:8080/api/product/getAll

**Response**

```
[
    {
        "productName": "Iphone",
        "price": 40000.0,
        "category": "Electronics"
    },
    {
        "productName": "Macbook",
        "price": 100000.0,
        "category": "Electronics"
    }
]
```

**Add products in bulk** : http://localhost:8080/api/product/bulk-add

**Sample Valid Request for bulk add**
```
[
  
  {
    "productName": "Loreal Paris Shampoo",
    "price": "300",
    "categoryRequest": {
      "categoryName": "Beauty Products"
    }
  },
  {
    "productName": "Flying Machine Shirt",
    "price": "800",
    "categoryRequest": {
      "categoryName": "Clothing"
    }
  },
  {
    "productName": "Coke",
    "price": "90",
    "categoryRequest": {
      "categoryName": "Food and Beverages"
    }
  },
  {
    "productName": "Lays",
    "price": "20",
    "categoryRequest": {
      "categoryName": "Food and Beverages"
    }
  },
  {
    "productName": "Sugar Lipstick",
    "price": "300",
    "categoryRequest": {
      "categoryName": "Beauty Products"
    }
  },
  {
    "productName": "Colour book",
    "price": "100",
    "categoryRequest": {
      "categoryName": "Stationary"
    }
  },
  {
    "productName": "Pen Stand",
    "price": "80",
    "categoryRequest": {
      "categoryName": "Stationary"
    }
  }
]
```

**Response**

```
[
    {
        "productName": "Sugar Lipstick",
        "price": 300.0,
        "category": "Beauty Products"
    },
    {
        "productName": "Loreal Paris Shampoo",
        "price": 300.0,
        "category": "Beauty Products"
    },
    {
        "productName": "Coke",
        "price": 90.0,
        "category": "Food and Beverages"
    },
    {
        "productName": "Lays",
        "price": 20.0,
        "category": "Food and Beverages"
    },
    {
        "productName": "Pen Stand",
        "price": 80.0,
        "category": "Stationary"
    },
    {
        "productName": "Flying Machine Shirt",
        "price": 800.0,
        "category": "Clothing"
    },
    {
        "productName": "Colour book",
        "price": 100.0,
        "category": "Stationary"
    }
]
```

**Purchase**

**Make purchase** : http://localhost:8080/api/purchase/makePurchase
**Sample Valid Request**
```
{
  "customer": {
    "customerId": "5"
  },
  "products": [
    {
      "productId": "1"
    },
    {
      "productId": "1"
    },
    {
      "productId": "6"
    }
  ],
  "purchaseTime":"2024-10-15T12:00:00"
}
```

**Response**

```
{
    "purchaseTime": "2024-10-15T12:00:00",
    "customerResponse": {
        "firstName": "Thor",
        "lastName": "Odin",
        "email": "thunder@email.com",
        "contact": "4446667779",
        "totalRewardPoints": 81450
    },
    "productResponses": [
        {
            "productName": "Iphone",
            "price": 40000.0,
            "category": "Electronics"
        },
        {
            "productName": "Flying Machine Shirt",
            "price": 800.0,
            "category": "Clothing"
        }
    ],
    "purchaseAmount": 40800.0,
    "totalRewardPoints": 81450
}
```

**Get All purchases** : http://localhost:8080/api/purchase/getAll

**Response**

```
[
    {
        "purchaseTime": "2024-10-15T12:00:00",
        "customerResponse": {
            "firstName": "Thor",
            "lastName": "Odin",
            "email": "thunder@email.com",
            "contact": "4446667779",
            "totalRewardPoints": 81450
        },
        "productResponses": [
            {
                "productName": "Iphone",
                "price": 40000.0,
                "category": "Electronics"
            },
            {
                "productName": "Flying Machine Shirt",
                "price": 800.0,
                "category": "Clothing"
            }
        ],
        "purchaseAmount": 40800.0,
        "totalRewardPoints": 81450
    },
    {
        "purchaseTime": "2024-11-15T12:00:00",
        "customerResponse": {
            "firstName": "James",
            "lastName": "Bond",
            "email": "bond@email.com",
            "contact": "0070070070",
            "totalRewardPoints": 281450
        },
        "productResponses": [
            {
                "productName": "Iphone",
                "price": 40000.0,
                "category": "Electronics"
            },
            {
                "productName": "Macbook",
                "price": 100000.0,
                "category": "Electronics"
            },
            {
                "productName": "Flying Machine Shirt",
                "price": 800.0,
                "category": "Clothing"
            }
        ],
        "purchaseAmount": 140800.0,
        "totalRewardPoints": 281450
    },
    {
        "purchaseTime": "2024-10-15T12:00:00",
        "customerResponse": {
            "firstName": "Dhanush",
            "lastName": "B",
            "email": "dannyb@email.com",
            "contact": "1302983967",
            "totalRewardPoints": 201490
        },
        "productResponses": [
            {
                "productName": "Macbook",
                "price": 100000.0,
                "category": "Electronics"
            },
            {
                "productName": "Lays",
                "price": 20.0,
                "category": "Food and Beverages"
            },
            {
                "productName": "Flying Machine Shirt",
                "price": 800.0,
                "category": "Clothing"
            }
        ],
        "purchaseAmount": 100820.0,
        "totalRewardPoints": 201490
    },
    {
        "purchaseTime": "2024-09-15T12:00:00",
        "customerResponse": {
            "firstName": "John",
            "lastName": "Doe",
            "email": "johndoe@email.com",
            "contact": "8883931234",
            "totalRewardPoints": 400580
        },
        "productResponses": [
            {
                "productName": "Macbook",
                "price": 100000.0,
                "category": "Electronics"
            },
            {
                "productName": "Lays",
                "price": 20.0,
                "category": "Food and Beverages"
            },
            {
                "productName": "Colour book",
                "price": 100.0,
                "category": "Stationary"
            }
        ],
        "purchaseAmount": 100120.0,
        "totalRewardPoints": 200090
    },
    {
        "purchaseTime": "2024-08-15T12:00:00",
        "customerResponse": {
            "firstName": "Harry",
            "lastName": "Potter",
            "email": "notamuggle@email.com",
            "contact": "8889990006",
            "totalRewardPoints": 399780
        },
        "productResponses": [
            {
                "productName": "Macbook",
                "price": 100000.0,
                "category": "Electronics"
            },
            {
                "productName": "Lays",
                "price": 20.0,
                "category": "Food and Beverages"
            }
        ],
        "purchaseAmount": 100020.0,
        "totalRewardPoints": 199890
    },
    {
        "purchaseTime": "2024-11-15T12:00:00",
        "customerResponse": {
            "firstName": "Harry",
            "lastName": "Potter",
            "email": "notamuggle@email.com",
            "contact": "8889990006",
            "totalRewardPoints": 399780
        },
        "productResponses": [
            {
                "productName": "Macbook",
                "price": 100000.0,
                "category": "Electronics"
            },
            {
                "productName": "Lays",
                "price": 20.0,
                "category": "Food and Beverages"
            }
        ],
        "purchaseAmount": 100020.0,
        "totalRewardPoints": 199890
    },
    {
        "purchaseTime": "2024-10-15T12:00:00",
        "customerResponse": {
            "firstName": "John",
            "lastName": "Doe",
            "email": "johndoe@email.com",
            "contact": "8883931234",
            "totalRewardPoints": 400580
        },
        "productResponses": [
            {
                "productName": "Macbook",
                "price": 100000.0,
                "category": "Electronics"
            },
            {
                "productName": "Lays",
                "price": 20.0,
                "category": "Food and Beverages"
            },
            {
                "productName": "Loreal Paris Shampoo",
                "price": 300.0,
                "category": "Beauty Products"
            }
        ],
        "purchaseAmount": 100320.0,
        "totalRewardPoints": 200490
    }
]
```

**Rewards**

**Get Rewards Summary for 3 months** : http://localhost:8080/api/reward-points/get-3months-summary

**Response**

```
[
    {
        "customerFirstName": "John",
        "customerLastName": "Doe",
        "monthlyRewards": {
            "OCTOBER": 610,
            "SEPTEMBER": 0,
            "NOVEMBER": 280010
        },
        "totalRewards": 280620
    },
    {
        "customerFirstName": "John",
        "customerLastName": "Doe",
        "monthlyRewards": {
            "OCTOBER": 200610,
            "SEPTEMBER": 201610,
            "NOVEMBER": 0
        },
        "totalRewards": 402220
    },
    {
        "customerFirstName": "Harry",
        "customerLastName": "Potter",
        "monthlyRewards": {
            "OCTOBER": 0,
            "SEPTEMBER": 0,
            "NOVEMBER": 80610
        },
        "totalRewards": 80610
    },
    {
        "customerFirstName": "Dhanush",
        "customerLastName": "B",
        "monthlyRewards": {
            "OCTOBER": 0,
            "SEPTEMBER": 0,
            "NOVEMBER": 280010
        },
        "totalRewards": 280010
    },
    {
        "customerFirstName": "James",
        "customerLastName": "Bond",
        "monthlyRewards": {
            "OCTOBER": 81450,
            "SEPTEMBER": 0,
            "NOVEMBER": 0
        },
        "totalRewards": 81450
    },
    {
        "customerFirstName": "Thor",
        "customerLastName": "Odin",
        "monthlyRewards": {
            "OCTOBER": 0,
            "SEPTEMBER": 0,
            "NOVEMBER": 0
        },
        "totalRewards": 0
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
