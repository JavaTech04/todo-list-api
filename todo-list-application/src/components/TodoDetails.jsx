import { useParams } from "react-router-dom";
import { getTodoDetail as funcDetail } from "../api/myTodo";
import { useEffect, useState } from "react";
export const TodoDetails = () => {
  const [todos, setTodos] = useState(null);
  const [listName, setListName] = useState("");

  const { id } = useParams();

  const fechTodo = async (id) => {
    const response = await funcDetail(id).then((res) => res.data);
    setTodos(response.todo);
    setListName(response.listName);
  };

  useEffect(() => {
    fechTodo(id);
  }, []);

  return (
    <div className="container">
      <h1>{listName}</h1>
      <ul className="list-group" style={{cursor: "pointer"}}>
        {todos &&
          todos.map((value) => (
            <li className="list-group-item" key={value.id}>
              {value.title} &nbsp;
              {value.description} &nbsp;
              {value.dueDate} &nbsp;
              {value.dueDate} &nbsp;
              {value.status === "complete" ? (
                <b className="text-success">{value.status}</b>
              ) : (
                <b className="text-danger">{value.status}</b>
              )}
            </li>
          ))}
      </ul>
    </div>
  );
};
