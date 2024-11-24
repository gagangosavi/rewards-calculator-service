Client

   |
   
   |-- HTTP GET Request (/api/reward-points/get-3months-summary)
   
   |
   
Controller Layer (RewardPointsController)

   |
   
   |-- Calls -> RewardPointsService.getThreeMonthsRewardsFoAllCustomers()
   
   |
   
Service Layer (RewardPointsService)

   |
   
   |-- Fetches all customers from CustomerRepository.findAll()
   
   |    |
   
   |   |-- Queries the database for customers
   
   |   |
   
   |-- Fetches purchases for the last 3 months from PurchaseRepository.findPurchasesForLastThreeMonths()
   
   |   |
   
   |   |-- Executes a custom JPQL query to retrieve purchases
   
   |   |
   
   |-- Calls -> GenerateRewardsSummaryResponse.generateRewardsSummaryResponseForGivenCustomersAndPurchases()
   
   |   |
   
   |-- Aggregates rewards data by customer and month
   
   |
   
Repository Layer

   |
   
   |-- CustomerRepository.findAll() -> Fetches all customers
   
   |-- PurchaseRepository.findPurchasesForLastThreeMonths() -> Fetches purchases
   
   |
   
Database

   |
   
   |-- Retrieves customer data
   
   |-- Retrieves purchases data
   
   |
   
   |
   
Response (HTTP 200 OK)

   |
   
   |-- JSON Response with RewardsSummaryResponse
