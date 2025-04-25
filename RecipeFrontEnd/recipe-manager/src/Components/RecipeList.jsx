import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Button } from "react-bootstrap";
import { viewRecipes } from "../Services/RecipeService";

const RecipeDetail = () => {
  const { id } = useParams();
  const [recipe, setRecipe] = useState(null);

  useEffect(() => {
    if (id) {
      fetchRecipeDetails(id);
    }
  }, [id]);

  const fetchRecipeDetails = async (recipeId) => {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
      window.location.href = "/login";
      return;
    }

    try {
      const response = await viewRecipes(recipeId, token);
      setRecipe(response.data);
    } catch (error) {
      console.error("Error fetching recipe:", error);
      if (error.response && error.response.status === 401) {
        alert("Session expired! Please log in again.");
        localStorage.removeItem("jwtToken");
        window.location.href = "/login";
      } else {
        alert("An error occurred while fetching the recipe. Please try again.");
      }
    }
  };

  if (!recipe) {
    return <div>Loading recipe...</div>;
  }

  return (
    <div>
      <div className="container mt-3">
        <h1 className="display-4 text-center">{recipe.name}</h1>
        <p className="lead text-muted text-center">{recipe.description}</p>
      </div>

      <div className="card w-20 mx-auto p-0 shadow-sm">
        <table className="table table-borderless">
          <tbody>
            <tr>
              <td>
                <strong>Created by:</strong> {recipe.createdBy}
              </td>
              <td>|</td>
              <td>
                <strong>Created on:</strong>{" "}
                {new Date(recipe.createdDate).toLocaleString()}
              </td>
              <td>|</td>
              <td>
                <strong>Updated on:</strong>{" "}
                {new Date(recipe.updatedDate).toLocaleString()}
              </td>
              <td>|</td>
              <td>
                <strong>Rating:</strong> {recipe.rating} / 5
              </td>
              <td>|</td>
              <td>
                <strong>Cooking Time:</strong> {recipe.cookingTime}
              </td>
              <td>|</td>
              <td>
                <strong>Servings:</strong> {recipe.servings}
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div className="container-fluid p-2 mt-3">
        <h1 className="fs-2">Ingredients:</h1>
        <ul>
          {recipe.ingredients.map((ingredient, i) => (
            <li key={i} className="mt-3">
              {ingredient.quantity} {ingredient.name}
            </li>
          ))}
        </ul>
      </div>

      <div className="container-fluid p-2 mt-2">
        <h1 className="fs-2">Instructions:</h1>
        <ol>
          {recipe.instructions.map((instruction, i) => (
            <li key={i} className="mt-3">
              {instruction.step}
            </li>
          ))}
        </ol>
      </div>

      <div className="container-fluid p-3 mt-4" style={{ textAlign: "right" }}>
        <Button href="/" variant="dark" className="ms-4 fs-5">
          Back
        </Button>
      </div>
    </div>
  );
};

export default RecipeDetail;
