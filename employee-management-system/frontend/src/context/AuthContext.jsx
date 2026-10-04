import { createContext, useContext, useState } from "react";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

function AuthProvider({ children }) {

  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem("token")
  );

  const [role, setRole] = useState(() => {

    const token = localStorage.getItem("token");

    if (!token) return null;

    const decoded = jwtDecode(token);

    return decoded.role;
  });

  const logout = () => {

    localStorage.removeItem("token");

    setIsAuthenticated(false);

    setRole(null);
  };

  return (

    <AuthContext.Provider
      value={{
        isAuthenticated,
        setIsAuthenticated,
        role,
        setRole,
        logout
      }}
    >

      {children}

    </AuthContext.Provider>

  );
}

export default AuthProvider;