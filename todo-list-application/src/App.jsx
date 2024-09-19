import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import { SignIn } from "./components/SignIn";
import { AuthProvider } from "./context/AuthProvider";
import ProtectedLayout from "./components/ProtectedLayout";
import { Home } from "./components/Home";
import { MyTodo } from "./components/MyTodo";
import { TodoDetails } from "./components/TodoDetails";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<SignIn />} />
          
          <Route element={<ProtectedLayout />}>
            <Route path="/" element={<Home />} />
            <Route path="/my-todo" element={<MyTodo />} />
            <Route path="/my-todo/:id" element={<TodoDetails />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
