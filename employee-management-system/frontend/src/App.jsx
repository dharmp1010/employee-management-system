import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import EmployeeList from "./pages/EmployeeList";
import AuthProvider from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";
import AdminRoute from "./components/AdminRoute";
import MyLeaves from "./pages/MyLeaves";
import AdminLeaves from "./pages/AdminLeaves";
import { useState } from "react";
import "./App.css";

function App() {
  const [darkMode, setDarkMode] = useState(false);
  return (
    <AuthProvider>
      <div className={darkMode ? "app dark" : "app"}>
      <BrowserRouter>
          <button onClick={() => setDarkMode(!darkMode)}>
            {darkMode ? "Light Mode" : "Dark Mode"}
          </button>
        <Routes>
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Home />
              </ProtectedRoute>
            }
          />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route
            path="/employees"
            element={
              <AdminRoute>
                <EmployeeList />
              </AdminRoute>
            }
          />
          <Route
            path="/my-leaves"
            element={
          <ProtectedRoute>
            <MyLeaves />
          </ProtectedRoute>
          }
          />
          <Route
          path="/admin-leaves"
          element={
        <AdminRoute>
          <AdminLeaves />
        </AdminRoute>
  }
/>

        </Routes>
      </BrowserRouter>
      </div>
    </AuthProvider>
  );
}

export default App;