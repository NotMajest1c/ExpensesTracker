# Expenses Tracker Application

A Spring Boot application for managing user expenses with JWT-based authentication.

---

## **Prerequisites**
- Java 17
- PostgreSQL
- Gradle

---

## **Setup**

### **1. Database Setup**
1. Create a PostgreSQL database named `ExpensesTracker`.
2. Update the `application.properties` file with the database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ExpensesTracker
   spring.datasource.username=username
   spring.datasource.password=password

3. Application Setup

   Clone the repository
   

   git clone https://github.com/NotMajest1c/ExpensesTracker

   Navigate to the project directory:
   

   cd expensestracker

Running the Application

    Build the application:

    ./gradlew build

    Run the application:

    ./gradlew bootRun

    The application will start at http://localhost:8081.

API Documentation:

User Endpoints

    Register User: POST /users/register
    json
    {
      "username": "john_doe",
      "password": "password123"
    }

    Login: POST /users/login (Will return a JWT Token)
    json
    {
      "username": "john_doe",
      "password": "password123"
    }


Expense Endpoints

    Add Expense: POST /expenses
    json
    {
      "category": "Food",
      "description": "Dinner",
      "amount": 50.00
    }

    Get All Expenses: GET /expenses

    Delete an Expense: DELETE /expenses/{id}

    Update an Expense: PUT /expenses/{id}

    Filter Expenses: GET /expenses/filter?category=Food&startDate=2025-02-01&endDate=2025-02-28&page=0&size=10

Testing the API

    Use Postman or any API testing tool.

    Include the JWT token in the Authorization header for authenticated endpoints:

    Authorization: Bearer <JWT_TOKEN>

Troubleshooting

    Database Issues: Ensure PostgreSQL is running and credentials(username/password) are correct.

    JWT Errors: Verify the token is valid and included in the request header.


