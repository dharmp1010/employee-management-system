import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Home() {

  const { role, logout } = useAuth();

  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="container">

      <div className="dashboard-header">
        <div>
          <h1>Employee Management System</h1>
          <p>Welcome to your dashboard</p>
        </div>

        <button onClick={handleLogout}>
          Logout
        </button>
      </div>

      <div className="user-info">
        <h2>Dashboard</h2>
        <p>
          Your role: <strong>{role}</strong>
        </p>
      </div>

      <div className="dashboard-buttons">

        {role === "ADMIN" && (
          <button onClick={() => navigate("/employees")}>
            Manage Employees
          </button>
        )}

        {role === "ADMIN" && (
          <button onClick={() => navigate("/admin-leaves")}>
            Manage Leaves
          </button>
        )}

        {role === "EMPLOYEE" && (
          <button onClick={() => navigate("/my-leaves")}>
            Manage Leaves
          </button>
        )}

        <button onClick={() => navigate("/register")}>
          Register New User
        </button>

      </div>

    </div>
  );
}

export default Home;