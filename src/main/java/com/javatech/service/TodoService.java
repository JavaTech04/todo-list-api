package com.javatech.service;

import com.javatech.dto.requests.TodoRequest;
import com.javatech.dto.response.TodoListResponse;

public interface TodoService {
    long createTodo(TodoRequest request);

    void updateTodo(long id, TodoRequest request);

    void deleteTodo(long id);

    TodoListResponse getTodosByList(long listTodoId);
}
