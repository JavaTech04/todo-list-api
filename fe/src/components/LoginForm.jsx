import { useState } from "react";
import { login } from "../service/authService";
export const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const onSubmit = async () => {
    try {
      login(username, password);
    } catch (error) {
      console.log(error);
    }
  };
  return (
    <div className="container">
      <div className="m-5">
        <h1 className="text-center">Login</h1>
        <input
          className="form-control mb-3"
          type="text"
          placeholder="username"
          value={username}
          onChange={(e) => {
            setUsername(e.target.value);
          }}
        />
        <input
          className="form-control mb-3"
          type="text"
          placeholder="password"
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        />
        <button className="btn btn-primary" onClick={onSubmit}>
          Login
        </button>
      </div>
    </div>
  );
};
