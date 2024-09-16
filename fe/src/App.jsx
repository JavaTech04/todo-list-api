import { useEffect, useState } from "react";
import "./App.css";
import { AuthenticatedApp } from "./components/AuthenticatedApp";
import { LoginForm } from "./components/LoginForm";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  const [authentication, setAuthentiaction] = useState(false);
  useEffect(() => {
    if (localStorage.getItem("authentication")) {
      setAuthentiaction(true);
    } else {
      setAuthentiaction(false);
    }
  }, [authentication]);

  return <div>{authentication ? <AuthenticatedApp /> : <LoginForm />}</div>;
}

export default App;
