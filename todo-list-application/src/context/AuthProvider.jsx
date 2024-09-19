import { AuthContext } from "./AuthContext";
import { useState, useEffect } from "react";
import { tokenService } from "../utils/tokenService";
import { jwtDecode } from "jwt-decode";
const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const savedToken = tokenService.getAccessToken();
    if (savedToken) {
      try {
        const decodedUser = jwtDecode(savedToken);
        setUser(decodedUser);
      } catch (error) {
        console.error("Invalid token", error);
        tokenService.clearUser();
      }
    }
    setIsLoading(false);
  }, []);

  const login = (userData) => {
    setUser(jwtDecode(userData.accessToken));
    tokenService.saveUser(userData);
  };

  const logout = () => {
    setUser(null);
    tokenService.clearUser();
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export { AuthProvider };
