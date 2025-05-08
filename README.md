
🍽️ Recipe Management System
A comprehensive full-stack web application that allows users to create, manage, and explore a variety of recipes. Built with Java (Spring Boot) for the backend and React.js for the frontend, this system offers features like user authentication, recipe creation, editing, deletion, and AI-powered recipe suggestions.

📚 Table of Contents
Getting Started

Frontend (React)

Backend (Java - Spring Boot)

Features

Technologies Used

Contributing

License

🚀 Getting Started
Prerequisites
Ensure you have the following installed on your machine:

Java 11 or higher

Node.js and npm

MySQL

Backend Setup
Navigate to the backend directory:

bash
Copy
Edit
cd RecipeBackEnd
Configure the application.properties file with your MySQL credentials:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
Build and run the backend:

bash
Copy
Edit
mvn clean install
mvn spring-boot:run
Frontend Setup
Navigate to the frontend directory:

bash
Copy
Edit
cd RecipeFrontEnd/recipe-manager
Install dependencies:

bash
Copy
Edit
npm install
Start the development server:

bash
Copy
Edit
npm start
💻 Frontend (React)
The frontend is developed using React.js and styled with React Bootstrap. It provides users with an intuitive interface to interact with the application.

Key Components
Authentication: Login and registration forms with JWT token handling.

Recipe Management: Pages to create, view, edit, and delete recipes.

Search Functionality: Search bar to find recipes by name or description.

AI Suggestions: Interface to get recipe suggestions powered by OpenAI.

🛠️ Backend (Java - Spring Boot)
The backend is built with Spring Boot and handles all business logic, data processing, and API endpoints.

Key Modules
User Management: Handles user registration, authentication, and authorization using JWT.

Recipe API: CRUD operations for recipes, including search functionality.

AI Integration: Endpoints to fetch recipe suggestions from OpenAI based on user input.

🌟 Features
User Authentication: Secure login and registration using JWT tokens.

Recipe Management: Create, edit, delete, and view recipes.

AI Recipe Suggestions: Generate recipe ideas based on user input.

Search Functionality: Search recipes by name or description.

Responsive Design: User-friendly interface compatible with various devices.

🧰 Technologies Used
Frontend: React.js, React Bootstrap

Backend: Java, Spring Boot

Database: MySQL

Authentication: JWT (JSON Web Tokens)

AI Integration: OpenAI API

🤝 Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

📄 License
This project is licensed under the MIT License.
