import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import axiosInstance from "../api/axiosInstance";
import { useAuth } from "../context/AuthContext";

function Login() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const { setIsAuthenticated, setRole } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await axiosInstance.post("/api/auth/login", {
        email: email,
        password: password
      });

      const token = response.data.token;

      localStorage.setItem("token", token);

      const decoded = jwtDecode(token);

      setIsAuthenticated(true);
      setRole(decoded.role);

      console.log("Login successful");
      console.log("Role:", decoded.role);

      navigate("/");

    } catch (error) {
      console.log("Login failed");
      console.log(error);
    }
  };

  return (
  <div className="container">

    <div className="login-card">

      <h1>Employee Management System</h1>

      <h2>Login</h2>

      <form onSubmit={handleLogin}>

        <div className="form-group">
          <label>Email</label>

          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Password</label>

          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button type="submit">
          Login
        </button>
        <button type="button" onClick={() => navigate("/register")}>
          Register
        </button>
      </form>

    </div>

  </div>
);
}

export default Login;