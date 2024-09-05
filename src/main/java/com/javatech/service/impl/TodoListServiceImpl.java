package com.javatech.service.impl;

import com.javatech.dto.response.TodoListResponse;
import com.javatech.exceptions.ResourceNotFoundException;
import com.javatech.model.TodoList;
import com.javatech.repository.TodoListRepository;
import com.javatech.repository.UserRepository;
import com.javatech.service.TodoListService;
import com.javatech.utils.TokenHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoListServiceImpl implements TodoListService {
    private final TodoListRepository todoListRepository;

    private final UserRepository userRepository;

    private final TokenHeader tokenHeader;


    @Override
    public List<TodoListResponse> getAllTodoLists(int pageNumber, int pageSize) {
        var user = this.userRepository.findByUsername(tokenHeader.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return this.todoListRepository.findByUser_Id(PageRequest.of(pageNumber, pageSize), user.getId()).stream().map(this::convertToResponse).toList();
    }

    @Override
    public long createTodoList(String listName) {
        var user = this.userRepository.findByUsername(tokenHeader.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return this.todoListRepository.save(TodoList.builder()
                .listName(listName).user(user).build()).getId();
    }

    @Override
    public void updateTodoList(long id, String listName) {
        TodoList todoList = this.todoListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found!"));
        todoList.setListName(listName);
        this.todoListRepository.save(todoList);
    }

    @Override
    public void deleteTodoList(long id) {
        TodoList todoList = this.todoListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found!"));
        this.todoListRepository.delete(todoList);
    }

    private TodoListResponse convertToResponse(TodoList todoList) {
        return TodoListResponse.builder()
                .id(todoList.getId())
                .listName(todoList.getListName())
                .created_at(todoList.getCreatedAt())
                .updated_at(todoList.getUpdatedAt())
                .build();
    }
}
