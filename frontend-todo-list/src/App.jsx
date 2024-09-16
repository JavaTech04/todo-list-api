import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import { AuthProvider } from "./context/AuthContext";
import LoginForm from "./components/LoginForm";
import PrivateRoute from "./components/PrivateRoute";
import AuthenticatedApp from './components/AuthenticatedApp'
function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginForm />} />
          <Route path="/" element={
            <PrivateRoute>
              <AuthenticatedApp />
            </PrivateRoute>
          } />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
