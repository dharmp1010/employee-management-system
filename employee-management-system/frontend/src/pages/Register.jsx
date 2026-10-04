import { useState } from "react";
import axiosInstance from "../api/axiosInstance";
import { useNavigate } from "react-router-dom";

function Register() {

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      const response = await axiosInstance.post("/api/auth/register", {
        name: name,
        email: email,
        password: password
      });

      console.log("Registration successful");
      console.log(response.data);
      navigate("/login");
    } catch (error) {
      console.log("Registration failed");
      console.log(error);
    }
  };

return (
  <div className="container">

    <div className="register-card">

      <h1>Employee Management System</h1>

      <h2>Register</h2>

      <form onSubmit={handleRegister}>

        <div className="form-group">
          <label>Name</label>

          <input
            type="text"
            placeholder="Enter your name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>

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
          Register
        </button>
        
        <button
          type="button"
          onClick={() => navigate("/login")}
          style={{ marginLeft: "10px" }}
        >
  Login
</button>
      </form>

    </div>

  </div>
);
}

export default Register;