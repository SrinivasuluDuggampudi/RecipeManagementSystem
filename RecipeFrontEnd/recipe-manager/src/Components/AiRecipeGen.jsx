import React, { useState } from "react";
import axios from "axios";
import { Button, Container, Form } from "react-bootstrap";
import * as jwt_decode from "jwt-decode";
import { useNavigate } from "react-router-dom";

const AiRecipeGen = () => {
  const [prompt, setPrompt] = useState("");
  const [response, setResponse] = useState("");
  const navigate = useNavigate();

  const getUserFromJWT = () => {
    const token = localStorage.getItem("jwtToken");
    if (!token) return "";
    const decoded = jwt_decode.jwtDecode(token);
    return decoded.sub || decoded.username || "";
  };

  const handleGenerate = async () => {
    const user = getUserFromJWT();
  
    try {
      const res = await axios.post("http://localhost:5000/process_input", {
        input: prompt,
        user: user,
      });
  
      if (res.data.message && res.data.recipes) {
        const recipeList = res.data.recipes.map((r, idx) => (
          <div key={r.id} className="mb-2">
            {idx + 1}. {r.name}{" "}
            <Button variant="link" onClick={() => navigate(`/view/recipe/${r.id}`)}>
              View Recipe
            </Button>
          </div>
        ));
        setResponse(
          <>
            <p>{res.data.message}</p>
            {recipeList}
          </>
        );
      } else if (res.data.ideas) {
        const ideaList = res.data.ideas.map((idea, idx) => (
          <div key={idx} className="mb-2">
            {idx + 1}.{" "}
            <a href={idea.link} target="_blank" rel="noopener noreferrer">
              {idea.name}
            </a>
          </div>
        ));
        setResponse(
          <>
            <p>{res.data.message}</p>
            {ideaList}
          </>
        );
      } else if (res.data.message) {
        setResponse(res.data.message);
      } else {
        setResponse("No relevant recipes or suggestions found.");
      }
    } catch (error) {
      console.error("Error:", error);
      setResponse("Failed to process your input. Please try again.");
    }
  };
  

  return (
    <Container className="mt-5">
      <h2>AI Recipe Chatbot</h2>
      <Form.Control
        as="textarea"
        rows={3}
        placeholder="e.g., Suggest recipes using chicken"
        value={prompt}
        onChange={(e) => setPrompt(e.target.value)}
      />
      <Button className="mt-2" onClick={handleGenerate}>
        Ask the Bot
      </Button>

      <div className="mt-4">
        <h4>Response:</h4>
        <div style={{ whiteSpace: "pre-wrap" }}>{response}</div>
      </div>
    </Container>
  );
};

export default AiRecipeGen;
