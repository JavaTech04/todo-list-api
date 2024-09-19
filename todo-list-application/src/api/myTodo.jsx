import axios from "axios";
import { tokenService } from "../utils/tokenService";
import axiosInstance from "../utils/axiosInstance";

export const getAllTodo = async () => {
  const access_token = tokenService.getAccessToken();
  const response = await axiosInstance
    .get("/todo-lists", {
      headers: {
        Authorization: `Bearer ${access_token}`,
      },
    })
    .then((res) => res.data);
  return response;
};

export const getTodoDetail = async (id) => {
  const access_token = tokenService.getAccessToken();
  const response = await axiosInstance.get(`/todo/list/${id}`, {
    headers: {
      Authorization: `Bearer ${access_token}`,
    },
  })
  .then(res => res.data);
  return response;
};
