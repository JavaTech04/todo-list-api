import { createContext, useState, useEffect } from 'react';
import { tokenService } from '../utils/tokenService';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = tokenService.getUser();
    if (savedUser) {
      setUser(savedUser);
    }
  }, []);

  const login = (userData) => {
    setUser(userData);
    tokenService.saveUser(userData);
  };

  const logout = () => {
    setUser(null);
    tokenService.clearUser();
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
