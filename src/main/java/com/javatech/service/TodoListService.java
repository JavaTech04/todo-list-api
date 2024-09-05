package com.javatech.service;

import com.javatech.dto.response.TodoListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoListService {

    List<TodoListResponse> getAllTodoLists(int pageNumber, int pageSize);

    long createTodoList(String listName);

    void updateTodoList(long id, String listName);

    void deleteTodoList(long id);
}
