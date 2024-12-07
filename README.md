# Personal Finance Management Backend

This repository contains the backend for a **Personal Finance Management App**, a mobile application designed to help users manage their budgets, accounts, and transactions. The backend is built using **Java Spring Boot** and is secured with **Spring Security**. It integrates with a PostgreSQL database to handle user data, transaction records, and recurring transaction functionality.

## Features

- **Account Management**: CRUD operations for user accounts.
- **Budget Tracking**: Manage and monitor budgets.
- **Transaction Management**: Log transactions and support for recurring transactions.
- **Security**: Implements robust security using **Spring Security**.
- **Database**: Uses **PostgreSQL** for data storage.
- **Performance**: Implements virtual scrolling for handling large datasets efficiently.

## Technologies Used

- **Spring Boot**: Backend framework for API development.
- **Spring Security**: Ensures secure authentication and authorization.
- **JPA (Java Persistence API)**: For database interactions.
- **PostgreSQL**: Relational database for storing user and transaction data.
- **Java**: Programming language for the backend.

## Challenges Faced

1. **First-time use of React Native and Expo**: Developing the mobile frontend alongside the backend was a new experience.
2. **Learning Spring Boot**: This was the first major project using **Java Spring Boot**, including features like **Spring Security**.
3. **Recurring Transactions**: Implementing logic for creating and managing recurring transactions.

## What I Learned

- Developing RESTful APIs with **Spring Boot**.
- Secure authentication and authorization using **Spring Security**.
- Database schema design and management with **PostgreSQL**.
- Integration between mobile apps and backend APIs.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)** version 17 or later.
- **PostgreSQL** installed and configured.
- **Maven** for dependency management.

### Setup Instructions

1. **Clone the repository**:

   ```bash
   git clone https://github.com/your-username/finance-app-backend.git
   cd finance-app-backend
   ```

2. **Set up the database**:
   - Create a PostgreSQL database for the application.
   - Update the `application.properties` file with your database credentials:

     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```

3. **Build and run the application**:

   ```bash
   mvn spring-boot:run
   ```

4. The application will be available at `http://localhost:8080`.

## API Endpoints

### Authentication
- `POST /auth/login`: Authenticate a user.
- `POST /auth/register`: Register a new user.

### Accounts
- `GET /accounts`: Fetch all accounts.
- `POST /accounts`: Create a new account.
- `PUT /accounts/{id}`: Update an account.
- `DELETE /accounts/{id}`: Delete an account.

### Budgets
- `GET /budgets`: Fetch all budgets.
- `POST /budgets`: Create a new budget.
- `PUT /budgets/{id}`: Update a budget.
- `DELETE /budgets/{id}`: Delete a budget.

### Transactions
- `GET /transactions`: Fetch all transactions.
- `POST /transactions`: Create a new transaction.
- `PUT /transactions/{id}`: Update a transaction.
- `DELETE /transactions/{id}`: Delete a transaction.

### Recurring Transactions
- `GET /recurring-transactions`: Fetch all recurring transactions.
- `POST /recurring-transactions`: Create a new recurring transaction.
- `PUT /recurring-transactions/{id}`: Update a recurring transaction.
- `DELETE /recurring-transactions/{id}`: Delete a recurring transaction.

## Contributing

Contributions are welcome! Please follow the steps below:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature-name`).
3. Commit your changes (`git commit -m 'Add your feature'`).
4. Push to the branch (`git push origin feature/your-feature-name`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries or issues, please contact kylehasan1@gmail.com.

