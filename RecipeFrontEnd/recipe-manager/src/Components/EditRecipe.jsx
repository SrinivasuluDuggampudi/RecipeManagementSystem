import React, { useState, useEffect } from "react";
import { Button, Form, Container } from "react-bootstrap";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

const EditRecipe = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState("");

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
    const fetchRecipe = async () => {
      const token = localStorage.getItem("jwtToken");

      try {
        const response = await axios.get(`http://localhost:8080/api/recipes/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setRecipeData(response.data);
      } catch (error) {
        if (error.response && error.response.status === 401) {
          // Token expired or invalid, redirect to login
          setError("Session expired. Please log in again.");
          localStorage.removeItem("jwtToken");
          window.location.href = "/login"; 
        } else {
          console.error("Error fetching recipe:", error);
          setError("An error occurred while fetching the recipe.");
        }
      }
    };

    fetchRecipe();
  }, [id]);

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
      ingredients: [...recipeData.ingredients, { id: null, quantity: "", name: "" }],
    });
  };

  const handleDeleteIngredient = async (index, id) => {
    const updatedIngredients = recipeData.ingredients.filter((_, i) => i !== index);
    const updatedRecipeData = { ...recipeData, ingredients: updatedIngredients };
    const token = localStorage.getItem("jwtToken");

    if (id) {
      try {
        await axios.delete(`http://localhost:8080/api/ingredients/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
      } catch (error) {
        if (error.response && error.response.status === 401) {
          setError("Session expired. Please log in again.");
          localStorage.removeItem("jwtToken");
          window.location.href = "/login";  
        } else {
          console.error("Error deleting ingredient:", error);
          setError("An error occurred while deleting the ingredient.");
        }
      }
    }

    setRecipeData(updatedRecipeData);
  };

  const handleDeleteInstruction = async (index, id) => {
    const updatedInstructions = recipeData.instructions.filter((_, i) => i !== index);
    const updatedRecipeData = { ...recipeData, instructions: updatedInstructions };
    const token = localStorage.getItem("jwtToken");

    if (id) {
      try {
        await axios.delete(`http://localhost:8080/api/instructions/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
      } catch (error) {
        if (error.response && error.response.status === 401) {
          setError("Session expired. Please log in again.");
          localStorage.removeItem("jwtToken");
          window.location.href = "/login";  
        } else {
          console.error("Error deleting instruction:", error);
          setError("An error occurred while deleting the instruction.");
        }
      }
    }

    setRecipeData(updatedRecipeData);
  };

  const handleAddInstruction = () => {
    setRecipeData({
      ...recipeData,
      instructions: [...recipeData.instructions, { id: null, step: "" }],
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (recipeData.ingredients.length === 0) {
      setError("Please add Ingredients");
      return;
    }
    if (recipeData.instructions.length === 0) {
      setError("Please add Instructions");
      return;
    }
  
    const updatedRecipe = {
      ...recipeData,
      updatedDate: new Date().toISOString(),  
    };
  
    const token = localStorage.getItem("jwtToken");
  
    try {
      await axios.put(`http://localhost:8080/api/recipes/${id}`, updatedRecipe, {
        headers: { Authorization: `Bearer ${token}` },
      });
      window.location.href = "/";
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setError("Session expired. Please log in again.");
        localStorage.removeItem("jwtToken");
        window.location.href = "/login";
      } else {
        console.error("Error updating recipe:", error);
        setError("An error occurred while updating the recipe.");
      }
    }
  };
  

  return (
    <Container className="mt-5">
      <h1>Edit Recipe</h1>
      {error && <div className="alert alert-danger">{error}</div>}
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="name" className="mb-3">
          <Form.Label>Recipe Name</Form.Label>
          <Form.Control type="text" name="name" value={recipeData.name} onChange={handleChange} required />
        </Form.Group>

        <Form.Group controlId="description" className="mb-3">
          <Form.Label>Description</Form.Label>
          <Form.Control type="text" name="description" value={recipeData.description} onChange={handleChange} required />
        </Form.Group>

        <Form.Group controlId="createdBy" className="mb-3">
          <Form.Label>Created By</Form.Label>
          <Form.Control type="text" name="createdBy" value={recipeData.createdBy} onChange={handleChange} required />
        </Form.Group>

        <Form.Group controlId="createdDate" className="mb-3">
          <Form.Label>Created Date</Form.Label>
          <Form.Control
                type="text"
                value={new Date(recipeData.createdDate).toLocaleDateString()}
                readOnly
          />
        </Form.Group>

        <Form.Group controlId="rating" className="mb-3">
          <Form.Label>Rating</Form.Label>
          <Form.Control type="number" name="rating" value={recipeData.rating} onChange={handleChange} min="1" max="5" required />
        </Form.Group>

        <Form.Group controlId="cookingTime" className="mb-3">
          <Form.Label>Cooking Time</Form.Label>
          <Form.Control type="text" name="cookingTime" value={recipeData.cookingTime} onChange={handleChange} required />
        </Form.Group>

        <Form.Group controlId="servings" className="mb-3">
          <Form.Label>Servings</Form.Label>
          <Form.Control type="text" name="servings" value={recipeData.servings} onChange={handleChange} required />
        </Form.Group>

        <h3>Ingredients</h3>
        {recipeData.ingredients.map((ingredient, index) => (
          <div key={index} className="d-flex mb-2">
            <Form.Control type="text" name="quantity" placeholder="Quantity" value={ingredient.quantity} onChange={(e) => handleIngredientChange(index, e)} className="me-2" required />
            <Form.Control type="text" name="name" placeholder="Ingredient Name" value={ingredient.name} onChange={(e) => handleIngredientChange(index, e)} required />
            <Button variant="link" onClick={() => handleDeleteIngredient(index, ingredient.id)}>Delete</Button>
          </div>
        ))}
        <Button variant="link" onClick={handleAddIngredient}>Add Another Ingredient</Button>

        <h3 className="mt-3">Instructions</h3>
        {recipeData.instructions.map((instruction, index) => (
          <div key={index} className="d-flex mb-2">
            <Form.Control type="text" name="step" placeholder={`Step ${index + 1}`} value={instruction.step} onChange={(e) => handleInstructionChange(index, e)} className="me-2" required />
            <Button variant="link" onClick={() => handleDeleteInstruction(index, instruction.id)}>Delete</Button>
          </div>
        ))}
        <Button variant="link" onClick={handleAddInstruction}>Add Another Instruction</Button>
        <br />
        <Button type="submit" variant="primary" className="mt-3">Update Recipe</Button>
        <Button onClick={() => window.location.href = "/"} variant="dark" className="ms-2 mt-3">Back</Button>
      </Form>
    </Container>
  );
};

export default EditRecipe;
