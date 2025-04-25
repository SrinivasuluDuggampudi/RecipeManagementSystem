import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes, useNavigate } from "react-router-dom";
import ListRecipesComponent from "./Components/ListRecipesComponent";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { Navbar, Nav, Container, Button } from "react-bootstrap";
import RecipeList from "./Components/RecipeList.jsx";
import EditRecipe from "./Components/EditRecipe.jsx";
import AddRecipe from "./Components/AddRecipe.jsx";
import "font-awesome/css/font-awesome.min.css";
import Login from "./Components/Login.jsx";
import Register from "./Components/Register.jsx";
import * as jwtDecode from "jwt-decode";
import AiRecipeGen from "./Components/AiRecipeGen.jsx";


function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [username, setUsername] = useState("");
  //const navigate = useNavigate();

  useEffect(() => {
    const jwtToken = localStorage.getItem("jwtToken");

    if (!jwtToken) {
      setIsAuthenticated(false);
      //window.location.href = "/login"; 
      return;
    }

    try {
      const decoded = jwtDecode.jwtDecode(jwtToken);
      const currentTime = Math.floor(Date.now() / 1000);

      if (decoded.exp && decoded.exp < currentTime) {
        console.log("Token expired");
        localStorage.removeItem("jwtToken");
        setIsAuthenticated(false);
        window.location.href = "/login"; 
        return;
      }

      setIsAuthenticated(true);
      setUsername(decoded.sub || decoded.username);
    } catch (error) {
      console.error("Invalid token:", error);
      localStorage.removeItem("jwtToken");
      setIsAuthenticated(false);
      window.location.href = "/login"; 
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("jwtToken");
    setIsAuthenticated(false);
    window.location.href = "/login"; 
  };

  return (
    <Router>
      <div className="App">
        <header>
          <Navbar variant="dark" expand="lg" className="p-3 custom-bg">
            <Container>
              <Navbar.Brand href="/">
                <h2>Recipe Manager</h2>
              </Navbar.Brand>
              <Navbar.Toggle aria-controls="navbar-nav" />
              <Navbar.Collapse id="navbar-nav">
                <Nav className="ms-auto">
                  <Nav.Link href="/">Home</Nav.Link>
                  <Nav.Link href="/about">About</Nav.Link>
                  <Nav.Link href="/services">Services</Nav.Link>
                  <Nav.Link href="/contact">Contact</Nav.Link>
                </Nav>
                {isAuthenticated ? (
                  <>
                    <Button variant="danger" onClick={handleLogout} className="ms-3">
                      Logout
                    </Button>
                    <span>{username}</span>
                  </>
                ) : (
                  <Button href="/login" variant="dark" className="ms-3">
                    Login
                  </Button>
                )}
              </Navbar.Collapse>
            </Container>
          </Navbar>
        </header>
        <Routes>
          <Route path="/" element={<ListRecipesComponent />} />
          <Route path="/view/recipe/:id" element={<RecipeList />} />
          <Route path="/edit/recipe/:id" element={<EditRecipe />} />
          <Route path="/addRecipe" element={<AddRecipe />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/aigen" element={<AiRecipeGen />} />

        </Routes>
      </div>
    </Router>
  );
}

export default App;
