package com.javatech.service.impl;

import com.javatech.repository.TodoListRepository;
import com.javatech.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoListServiceImpl implements TodoListService {
    private final TodoListRepository todoListRepository;

    @Override
    public List<?> getAllTodoList() {
        return this.todoListRepository.findAll();
    }
}
