
# 🍽️ Recipe Management System

A comprehensive full-stack web application that enables users to create, manage, and explore a variety of recipes. Built with **Spring Boot** (Java) for the backend and **React.js** for the frontend, this system offers features like user authentication, recipe creation, editing, deletion, and AI-powered recipe suggestions.

---

## 📚 Table of Contents

- [📚 Table of Contents](#-table-of-contents)
- [🧰 Technologies Used](#-technologies-used)
- [🧱 Application Modules](#-application-modules)
- [📦 Project Structure](#-project-structure)
- [🛠️ How to Run](#️-how-to-run)
  - [✅ Backend Setup](#-backend-setup)
  - [✅ Frontend Setup](#-frontend-setup)
- [🔐 Environment Variables](#-environment-variables)
- [📸 Screenshots](#-screenshots)
- [🧪 API Endpoints (Java Backend)](#-api-endpoints-java-backend)
- [🙋‍♂️ Author](#-author)
- [📄 License](#-license)
- [🤝 Contributions](#-contributions)

---

## 🧰 Technologies Used

### Backend (Java + Spring Boot)
- Spring Boot (REST APIs)
- Spring Security with JWT Authentication
- Hibernate + JPA
- MySQL Database
- Lombok

### Frontend (React)
- React.js (SPA)
- React Bootstrap
- Axios (API integration)
- React Router

### AI Integration
- OpenAI GPT for smart recipe suggestions (via prompt-based input)

---

## 🧱 Application Modules

### 🔐 Authentication Module
- Login/Register with JWT
- Role-based access (future-ready)

### 📝 Recipe Management
- CRUD operations for recipes
- Each recipe includes:
  - Name
  - Description
  - Ingredients
  - Instructions
  - Cooking Time
  - Rating
  - CreatedBy (username)

### 🤖 AI Chatbot
- Ask the bot for ideas like:  
  _"Suggest a vegetarian Indian dinner with paneer"_
- If not found in user recipes, uses OpenAI to suggest ideas

---

## 📦 Project Structure

```
RecipeManagementSystem/
│
├── RecipeBackEnd/          # Spring Boot application
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   ├── service/
│   └── security/
│
├── RecipeFrontEnd/recipe-manager/  # React frontend
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   └── services/
│   └── public/
```

---

## 🛠️ How to Run

### ✅ Backend Setup

1. Navigate to the backend folder:
   ```bash
   cd RecipeBackEnd
   ```
2. Configure your database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/recipe_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### ✅ Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd RecipeFrontEnd/recipe-manager
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the React development server:
   ```bash
   npm start
   ```

---

## 🔐 Environment Variables

For the frontend, create a `.env` file in the `recipe-manager` directory:
```env
REACT_APP_API_URL=http://localhost:8080
REACT_APP_OPENAI_API_KEY=your_openai_api_key
```

---

## 📸 Screenshots

> _Add `screenshots/` images and embed them here for better presentation._

---

## 🧪 API Endpoints (Java Backend)

| Endpoint                 | Method | Description               |
|--------------------------|--------|---------------------------|
| `/api/auth/register`     | POST   | Register user             |
| `/api/auth/login`        | POST   | Login and return JWT      |
| `/api/recipes`           | GET/POST/PUT/DELETE | Recipe CRUD |
| `/api/recipes/search`    | GET    | Search by keyword         |
| `/api/recipes/user`      | GET    | Recipes by logged-in user |
| `/api/search-by-keywords`| GET    | Filter by ingredients     |
| `/process_input` (Flask) | POST   | AI Chatbot route          |

---

## 🙋‍♂️ Author

**Srinivasulu Duggampudi**  
[GitHub Profile](https://github.com/SrinivasuluDuggampudi)

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributions

Contributions are welcome! Feel free to fork the repository and submit a pull request for any enhancements or bug fixes.
