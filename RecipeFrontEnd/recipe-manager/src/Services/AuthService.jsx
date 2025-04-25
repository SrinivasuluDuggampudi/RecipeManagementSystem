import axios from "axios";

const API_URL = "http://localhost:8082/auth";

export const register = async (user) => {
  const response = await axios.post(`${API_URL}/register`, user);
  return response.data;
};

export const login = async (user) => {
  const response = await axios.post(`${API_URL}/login`, user);
  if (response.data === "Login successful.") {
    localStorage.setItem("username", user.username);
  }
  return response.data;
};

export const logout = async () => {
  const username = localStorage.getItem("username");
  await axios.post(`${API_URL}/logout`, { username });
  localStorage.removeItem("username");
};
