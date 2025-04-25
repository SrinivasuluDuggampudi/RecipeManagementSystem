import React, { useState } from "react";
import { login } from "../Services/AuthService";
import { useNavigate } from "react-router-dom";
import { Container, Form, Button, Alert, Card } from "react-bootstrap";

const Login = () => {
  const [user, setUser] = useState({ username: "", password: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await login(user);
      if (response && response.token) {
        localStorage.setItem("jwtToken", response.token); // Save token
        setMessage("Login successful.");
       // navigate("/"); // Redirect to homepage
       window.location.href = "/"; //to refersh page
      } else {
        setMessage("Invalid credentials. Please try again.");
      }
    } catch (error) {
      console.error("Login error:", error);
      setMessage("Login failed. Please check your credentials.");
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Card style={{ width: "400px" }} className="p-4 shadow">
        <Card.Title className="text-center">Login</Card.Title>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="username">
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="text"
              name="username"
              placeholder="Enter username"
              value={user.username}
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
              value={user.password}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Button variant="primary" type="submit" className="w-100 mt-3">
            Login
          </Button>
        </Form>

        {message && (
          <Alert
            variant={message.includes("successful") ? "success" : "danger"}
            className="mt-3"
          >
            {message}
          </Alert>
        )}

        <div className="text-center mt-3">
          <p>
            Don't have an account? <a href="/register">Register here</a>
          </p>
        </div>
      </Card>
    </Container>
  );
};

export default Login;
