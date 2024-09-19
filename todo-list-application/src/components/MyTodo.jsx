import { useState } from "react";
import { getAllTodo } from "../api/myTodo";
import { useEffect } from "react";

import { useNavigate } from "react-router-dom";

export const MyTodo = () => {
  const [todos, setTodos] = useState(null);

  const navigate = useNavigate();

  const listTodo = async () => {
    const res = await getAllTodo();
    setTodos(res);
  };

  const openDetail = (id) => {
    navigate(`/my-todo/${id}`)
  }

  useEffect(() => {
    listTodo();
  }, []);

  return (
    <div className="container">
      <h1>MyTodo</h1>
      {todos &&
        todos.data.map((td) => 
        <ul key={td.id}>
          <li className="list-name" onClick={() => openDetail(td.id)}>
            {td.listName}
          </li>
        </ul>)}
    </div>
  );
};
