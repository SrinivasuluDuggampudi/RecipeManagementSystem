import React, { useState } from "react";
import axios from "axios";
import { Button, Container, Form } from "react-bootstrap";

const AiRecipeGen = () => {
  const [prompt, setPrompt] = useState("");
  const [response, setResponse] = useState("");

  const handleGenerate = async () => {
    try {
      const res = await axios.post("http://localhost:8080/api/ai/generate", { prompt });
      console.log(res.data);
      const data = (res.data);
      setResponse(data.choices[0].message.content);
    } catch (error) {
      setResponse("Error generating recipe.");
      console.error(error);
    }
  };

  return (
    <Container className="mt-5">
      <h2>AI Recipe Generator</h2>
      <Form.Control
        as="textarea"
        rows={3}
        placeholder="e.g., Suggest a vegetarian Indian dinner with paneer"
        value={prompt}
        onChange={(e) => setPrompt(e.target.value)}
      />
      <Button className="mt-2" onClick={handleGenerate}>Generate Recipe</Button>

      <div className="mt-4">
        <h4>Generated Recipe:</h4>
        <pre style={{ whiteSpace: 'pre-wrap' }}>{response}</pre>
      </div>
    </Container>
  );
};

export default AiRecipeGen;
