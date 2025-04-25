import React, { useState } from "react";
import { register } from "../Services/AuthService";
import { useNavigate } from "react-router-dom";
import { Container, Form, Button, Alert, Card } from "react-bootstrap";

const Register = () => {
  const [user, setUser] = useState({ username: "", password: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await register(user); 
      setMessage(response.msg);
  
      if (response.msg === "User Created Successfully.") {
        navigate("/login");
      }
    } catch (error) {
      if (error.response && error.response.data && error.response.data.msg) {
        setMessage(error.response.data.msg); 
      } else {
        setMessage("An unexpected error occurred.");
      }
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Card style={{ width: "400px" }} className="p-4 shadow">
        <Card.Title className="text-center">Register</Card.Title>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="username">
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="text"
              name="username"
              placeholder="Enter username"
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="password" className="mt-3">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              name="password"
              placeholder="Enter password"
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Button variant="success" type="submit" className="w-100 mt-3">
            Register
          </Button>
        </Form>

        {message && (
          <Alert
            variant={message.includes("Success") ? "success" : "danger"}
            className="mt-3"
          >
            {message}
          </Alert>
        )}

        <div className="text-center mt-3">
          <p>
            Already have an account? <a href="/login">Login here</a>
          </p>
        </div>
      </Card>
    </Container>
  );
};

export default Register;
