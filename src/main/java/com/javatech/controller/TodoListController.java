package com.javatech.controller;

import com.javatech.dto.response.ResponseData;
import com.javatech.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${api.version}/todo-lists")
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping("/lists")
    ResponseData<?> getAllLists() {
        return new ResponseData<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), this.todoListService.getAllTodoList());
    }
}
