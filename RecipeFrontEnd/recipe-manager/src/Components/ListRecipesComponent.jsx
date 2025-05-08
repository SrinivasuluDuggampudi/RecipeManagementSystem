import React, { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import axios from "axios";
import * as jwtDecode from "jwt-decode";

const ListRecipesComponent = () => {
  const [recipes, setRecipes] = useState([]);
  const [showLoginMessage, setShowLoginMessage] = useState(false);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [username, setUsername] = useState("");

  useEffect(() => {
    fetchRecipes();
  }, []);

  const fetchRecipes = async () => {
    const token = localStorage.getItem("jwtToken");

    if (!token) {
      setShowLoginMessage(true);
      return;
    }

    try {
      const decoded = jwtDecode.jwtDecode(token);
      const currentTime = Math.floor(Date.now() / 1000);

      if (decoded.exp && decoded.exp < currentTime) {
        console.log("Token expired");
        localStorage.removeItem("jwtToken");
        setShowLoginMessage(true);
        return;
      }

      const loggedInUser = decoded.sub || decoded.username;
      setUsername(loggedInUser);

      const response = await axios.get(
        `http://localhost:8080/api/recipes/user?usernew=${loggedInUser}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      setRecipes(response.data);
    } catch (error) {
      console.error("Error fetching recipes:", error);
      localStorage.removeItem("jwtToken");
      setShowLoginMessage(true);
    }
  };

  const handleSearch = async () => {
    const token = localStorage.getItem("jwtToken");

    if (!token) {
      setShowLoginMessage(true);
      return;
    }

    if (searchKeyword.trim() === "") {
      fetchRecipes();
      return;
    }

    try {
      const decoded = jwtDecode.jwtDecode(token);
      const loggedInUser = decoded.sub || decoded.username;

      const response = await axios.get(
        `http://localhost:8080/api/recipes/search?key=${searchKeyword}&user=${loggedInUser}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setRecipes(response.data);
    } catch (error) {
      console.error("Search failed:", error);
      alert("Failed to search recipes. Try again.");
    }
  };

  const handleDeleteRecipe = async (id) => {
    const confirmDelete = window.confirm("Are you sure you want to delete this recipe?");
    if (!confirmDelete) return;

    const token = localStorage.getItem("jwtToken");

    if (!token) {
      alert("You are not logged in!");
      window.location.href = "/login";
      return;
    }

    try {
      await axios.delete(`http://localhost:8080/api/recipes/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert("Recipe deleted successfully!");
      fetchRecipes();
    } catch (error) {
      console.error("Error deleting recipe:", error);
      alert("Failed to delete the recipe. Please try again.");
    }
  };

  return (
    <div className="container mt-5">
      {showLoginMessage ? (
        <div className="d-flex flex-column justify-content-center align-items-center text-center p-5">
          <img
            src="https://cdn-icons-png.flaticon.com/512/2921/2921822.png"
            alt="Login Required"
            style={{ width: "100px", marginBottom: "20px" }}
          />
          <h2 className="text-danger mb-3">You're not logged in</h2>
          <p className="fs-5 text-secondary mb-4">
            Please log in to view and manage your favorite recipes. Whether you're a culinary
            explorer or a kitchen pro, your personal cookbook awaits!
          </p>
        </div>
      ) : (
        <>
          <div className="d-flex justify-content-center mb-2">
            <div className="d-flex align-items-center mb-4" style={{ maxWidth: "700px", width: "100%" }}>
              <input
                type="text"
                placeholder="Search recipes by name or description..."
                className="form-control me-2"
                value={searchKeyword}
                onChange={(e) => setSearchKeyword(e.target.value)}
              />
              <Button onClick={handleSearch} variant="primary">
                Search
              </Button>
              
            </div>
          </div>
          <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            {recipes.map((recipe) => (
              <div className="col" key={recipe.id}>
                <div className="card">
                  <div className="card-body">
                    <h5 className="card-title">{recipe.name}</h5>
                    <p className="card-text">{recipe.description}</p>
                    <a href={`/view/recipe/${recipe.id}`} className="btn btn-primary">
                      View Recipe
                    </a>
                    <span style={{ marginRight: "2px" }}></span>
                    <a href={`/edit/recipe/${recipe.id}`} className="btn btn-secondary p-2 fa fa-pencil"></a>
                    <span style={{ marginRight: "2px" }}></span>
                    <Button
                      className="btn-danger fa fa-trash-o p-2 mr-2"
                      onClick={() => handleDeleteRecipe(recipe.id)}
                    ></Button>
                  </div>
                </div>
              </div>
            ))}
            <Button
              href="/addRecipe"
              className="btn btn-secondary"
              style={{
                position: "fixed",
                bottom: "20px",
                right: "20px",
                width: "60px",
                height: "60px",
                borderRadius: "50%",
                fontSize: "30px",
                padding: "0",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              +
            </Button>
             <Button
              href="/aigen"
              variant="info"
              className="position-fixed"
              style={{
                bottom: "90px",
                right: "20px",
                width: "60px",
                height: "60px",
                borderRadius: "50%",
                fontSize: "16px",
                fontWeight: "bold",
                padding: "0",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              🤖
            </Button>
          </div>
        </>
      )}
    </div>
  );
};

export default ListRecipesComponent;
