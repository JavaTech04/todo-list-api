import { useState } from "react";
import { login as loginApi } from "../api/authApi";
import useAuth from "../hooks/useAuth";
import { useNavigate } from "react-router-dom";

export const SignIn = () => {
  const [username, setUsername] = useState("");
  const [password, setpassword] = useState("");
  const [incorrect, setIncorrect] = useState(false);
  const [message, setMessage] = useState('');

  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const userData = await loginApi(username, password);
      login(userData);
      navigate("/");
    } catch (error) {
      setIncorrect(true)
      setMessage(error.response.data.message)
      console.log(error);
      
    }
  };
  return (
    <div className="h-100 m-5 d-flex align-items-center justify-content-center">
      <div className="w-50">
        <h1 className="text-center mb-5">Login</h1>
        {incorrect && (
          <div className="alert alert-danger" role="alert">
            {message}
          </div>
        )}
        <form className="mt-3" onSubmit={handleSubmit}>
          <div className="mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <input
              type="password"
              className="form-control"
              placeholder="password"
              value={password}
              onChange={(e) => setpassword(e.target.value)}
            />
          </div>
          <div className="d-grid mb-3">
            <button type="submit" className="btn btn-success">
              Login
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
