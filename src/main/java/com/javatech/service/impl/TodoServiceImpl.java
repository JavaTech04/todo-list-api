package com.javatech.service.impl;

import com.javatech.dto.requests.TodoRequest;
import com.javatech.dto.response.TodoListResponse;
import com.javatech.dto.response.TodoResponse;
import com.javatech.exceptions.EntityNotFoundException;
import com.javatech.exceptions.ResourceNotFoundException;
import com.javatech.model.Todo;
import com.javatech.model.TodoList;
import com.javatech.repository.TodoListRepository;
import com.javatech.repository.TodoRepository;
import com.javatech.repository.UserRepository;
import com.javatech.service.TodoService;
import com.javatech.utils.TokenHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    private final TodoListRepository todoListRepository;

    private final UserRepository userRepository;

    private final TokenHeader tokenHeader;

    @Override
    public long createTodo(TodoRequest request) {
        var user = this.userRepository.findByUsername(tokenHeader.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with username %s not found", tokenHeader.getUsername()))
        );
        TodoList todoList = this.todoListRepository.findByUser_IdAndId(user.getId(), request.getTodoListId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("TodoList with id %d not found", request.getTodoListId()))
        );
        return this.todoRepository.save(Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(request.getStatus())
                .todoList(todoList)
                .user(user)
                .build()).getId();
    }

    @Override
    public void updateTodo(long id, TodoRequest request) {
        var user = this.userRepository.findByUsername(tokenHeader.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with username %s not found", tokenHeader.getUsername()))
        );
        TodoList todoList = this.todoListRepository.findByUser_IdAndId(user.getId(), request.getTodoListId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("TodoList with id %d not found", request.getTodoListId()))
        );
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(request.getStatus())
                .todoList(todoList)
                .user(user)
                .build();
        todo.setId(id);
        this.todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(long id) {
        var user = this.userRepository.findByUsername(tokenHeader.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with username %s not found", tokenHeader.getUsername()))
        );
        Todo todo = this.todoRepository.findByIdAndUser_Id(id, user.getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Todo with id %d not found", id))
        );
        this.todoRepository.delete(todo);
    }

    @Override
    public TodoListResponse getTodosByList(long listTodoId) {
        var user = this.userRepository.findByUsername(tokenHeader.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with username %s not found", tokenHeader.getUsername()))
        );
        TodoList todoList = this.todoListRepository.findByUser_IdAndId(user.getId(), listTodoId).orElseThrow(
                () -> new EntityNotFoundException(String.format("TodoList with id %d not found", listTodoId))
        );
        List<TodoResponse> todoResponseList = todoList.getTodos().stream().map(this::convertToResponse).toList();
        return TodoListResponse.builder()
                .id(todoList.getId())
                .listName(todoList.getListName())
                .created_at(todoList.getCreatedAt())
                .updated_at(todoList.getUpdatedAt())
                .todo(todoResponseList)
                .build();
    }

    private TodoResponse convertToResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .dueDate(todo.getDueDate())
                .status(todo.getStatus())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
