import React, { useState, useEffect } from "react";
import { Button, Form, Container } from "react-bootstrap";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import * as jwt_decode from "jwt-decode";

const AddRecipe = () => {
  const navigate = useNavigate();

  const [recipeData, setRecipeData] = useState({
    name: "",
    description: "",
    createdBy: "",
    rating: 1,
    cookingTime: "",
    servings: "",
    createdDate: "",
    updatedDate: "",
    ingredients: [{ quantity: "", name: "" }],
    instructions: [{ step: "" }],
  });

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");

    if (token) {
      try {
        const decodedToken = jwt_decode.jwtDecode(token);
        const currentTime = Date.now() / 1000;

        if (decodedToken.exp < currentTime) {
          alert("Session expired. Please login again.");
          localStorage.removeItem("jwtToken");
          window.location.href = "/login";
        } else {
          const now = new Date().toISOString();
          setRecipeData((prevData) => ({
            ...prevData,
            createdBy: decodedToken.sub,
            createdDate: now,
            updatedDate: now,
          }));
        }
      } catch (err) {
        console.error("Invalid token");
        localStorage.removeItem("jwtToken");
        window.location.href = "/login";
      }
    } else {
      alert("You are not logged in!");
      window.location.href = "/login";
    }
  }, [navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRecipeData({
      ...recipeData,
      [name]: value,
    });
  };

  const handleIngredientChange = (index, e) => {
    const { name, value } = e.target;
    const newIngredients = [...recipeData.ingredients];
    newIngredients[index][name] = value;
    setRecipeData({
      ...recipeData,
      ingredients: newIngredients,
    });
  };

  const handleInstructionChange = (index, e) => {
    const { name, value } = e.target;
    const newInstructions = [...recipeData.instructions];
    newInstructions[index][name] = value;
    setRecipeData({
      ...recipeData,
      instructions: newInstructions,
    });
  };

  const handleAddIngredient = () => {
    setRecipeData({
      ...recipeData,
      ingredients: [...recipeData.ingredients, { quantity: "", name: "" }],
    });
  };

  const handleAddInstruction = () => {
    setRecipeData({
      ...recipeData,
      instructions: [...recipeData.instructions, { step: "" }],
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem("jwtToken");
    if (!token) {
      alert("You are not logged in!");
      navigate("/login");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/api/recipes",
        recipeData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log("Recipe added:", response.data);
      navigate("/");
    } catch (error) {
      console.error("Error adding recipe:", error);
    }
  };

  return (
    <Container className="mt-5">
      <h1>Add Recipe</h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="name" className="mb-3">
          <Form.Label>Recipe Name</Form.Label>
          <Form.Control
            type="text"
            name="name"
            value={recipeData.name}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="description" className="mb-3">
          <Form.Label>Description</Form.Label>
          <Form.Control
            type="text"
            name="description"
            value={recipeData.description}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="createdBy" className="mb-3">
          <Form.Label>Created By</Form.Label>
          <Form.Control
            type="text"
            name="createdBy"
            value={recipeData.createdBy}
            readOnly
          />
        </Form.Group>

        <Form.Group controlId="createdDate" className="mb-3">
          <Form.Label>Created Date</Form.Label>
          <Form.Control
            type="text"
            name="createdDate"
            value={new Date(recipeData.createdDate).toLocaleString()}
            readOnly
          />
        </Form.Group>

        <Form.Group controlId="rating" className="mb-3">
          <Form.Label>Rating</Form.Label>
          <Form.Control
            type="number"
            name="rating"
            value={recipeData.rating}
            onChange={handleChange}
            min="1"
            max="5"
            required
          />
        </Form.Group>

        <Form.Group controlId="cookingTime" className="mb-3">
          <Form.Label>Cooking Time</Form.Label>
          <Form.Control
            type="text"
            name="cookingTime"
            value={recipeData.cookingTime}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="servings" className="mb-3">
          <Form.Label>Servings</Form.Label>
          <Form.Control
            type="text"
            name="servings"
            value={recipeData.servings}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <h3>Ingredients</h3>
        {recipeData.ingredients.map((ingredient, index) => (
          <div key={index} className="d-flex mb-2">
            <Form.Control
              type="text"
              name="quantity"
              placeholder="Quantity"
              value={ingredient.quantity}
              onChange={(e) => handleIngredientChange(index, e)}
              className="me-2"
              required
            />
            <Form.Control
              type="text"
              name="name"
              placeholder="Ingredient Name"
              value={ingredient.name}
              onChange={(e) => handleIngredientChange(index, e)}
              required
            />
          </div>
        ))}
        <Button variant="link" onClick={handleAddIngredient}>
          Add Another Ingredient
        </Button>

        <h3 className="mt-3">Instructions</h3>
        {recipeData.instructions.map((instruction, index) => (
          <div key={index} className="d-flex mb-2">
            <Form.Control
              type="text"
              name="step"
              placeholder={`Step ${index + 1}`}
              value={instruction.step}
              onChange={(e) => handleInstructionChange(index, e)}
              className="me-2"
              required
            />
          </div>
        ))}
        <Button variant="link" onClick={handleAddInstruction}>
          Add Another Instruction
        </Button>

        <br />
        <Button type="submit" variant="primary" className="mt-3">
          Add Recipe
        </Button>
        <Button href="/" variant="dark" className="ms-2 mt-3">
          Back
        </Button>
      </Form>
    </Container>
  );
};

export default AddRecipe;
