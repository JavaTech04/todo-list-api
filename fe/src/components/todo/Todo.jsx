import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";

export const Todo = () => {
  const [todos, setTodos] = useState(null);
  const API = "http://localhost:2004/api/v1/";
  const token = localStorage.getItem("authentication");

  const callApi = async () => {
    await axios
      .get(`${API}todo-lists`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => res.data)
      .then((res) => {
        console.log(res);
        setTodos(res.data);
      });
  };
  useEffect(() => {
    callApi();
  }, []);
  return (
    <>
      <h1>List Todo</h1>
      {todos && todos.map((value, index) => (
        <ul key={value.id}>
          <li>{value.listName}</li>
        </ul>
      ))}
    </>
  );
};
