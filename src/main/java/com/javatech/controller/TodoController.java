package com.javatech.controller;

import com.javatech.config.Translator;
import com.javatech.dto.requests.TodoRequest;
import com.javatech.dto.response.ResponseData;
import com.javatech.dto.response.ResponseError;
import com.javatech.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/${api.version}/todo")
@Tag(name = "Todo Controller", description = "Handles operations related to todo items, including creating, updating, retrieving, and deleting todo items.")
public class TodoController {
    private final TodoService todoService;

    /**
     * title: title-dummy
     * description: description-dummy
     * dueDate: dd/MM/yyyy
     * status: INCOMPLETE(incomplete)|IN_PROGRESS(in_progress)|COMPLETE(complete)
     * todoListId: number
     */
    @Operation(
            summary = "Create Todo Item",
            description = "Create a new todo item for the authenticated user. The request body should include all necessary details for the todo item."
    )
    @PostMapping
    ResponseData<?> createTodo(@Valid @RequestBody TodoRequest request) {
        try {
            log.info("Creating todo item: {}", request);
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("todo.success.createTodo"), this.todoService.createTodo(request));
        } catch (Exception e) {
            log.error("Error creating todo item: request={}, errorMessage={}", request, e.getMessage(), e);
            return new ResponseError<>(HttpStatus.OK.value(), Translator.toLocale("todo.error.createTodo"));
        }
    }

    /**
     * PathVariable: /id number min 1
     * title: title-dummy
     * description: description-dummy
     * dueDate: dd/MM/yyyy
     * status: INCOMPLETE(incomplete)|IN_PROGRESS(in_progress)|COMPLETE(complete)
     * todoListId: number
     */
    @Operation(
            summary = "Update Todo Item",
            description = "Update an existing todo item for the authenticated user. Provide the todo item's ID in the URL path and the new details in the request body."
    )
    @PutMapping("/{id}")
    ResponseData<?> updateTodo(@PathVariable @Min(1) long id, @Valid @RequestBody TodoRequest request) {
        try {
            log.info("Updating todo item with id={} and request={}", id, request);
            this.todoService.updateTodo(id, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("todo.success.updateTodo"));
        } catch (Exception e) {
            log.error("Error updating todo item: id={}, request={}, errorMessage={}", id, request, e.getMessage(), e);
            return new ResponseError<>(HttpStatus.OK.value(), Translator.toLocale("todo.error.updateTodo"));
        }
    }

    /**
     * PathVariable: /id number min 1
     */
    @Operation(
            summary = "Delete Todo Item",
            description = "Delete an existing todo item for the authenticated user. Provide the todo item's ID in the URL path."
    )
    @DeleteMapping("/{id}")
    ResponseData<?> deleteTodo(@PathVariable @Min(1) long id) {
        try {
            log.info("Deleting todo item with id={}", id);
            this.todoService.deleteTodo(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("todo.success.deleteTodo"));
        } catch (Exception e) {
            log.error("Error deleting todo item: id={}, errorMessage={}", id, e.getMessage(), e);
            return new ResponseError<>(HttpStatus.OK.value(), Translator.toLocale("todo.error.deleteTodo"));
        }
    }

    /**
     * PathVariable: /id number min 1
     */
    @Operation(
            summary = "Retrieve Todos by List",
            description = "Retrieve all todo items associated with a specific todo list. Provide the list ID in the URL path."
    )
    @GetMapping("/list/{listId}")
    public ResponseData<?> getTodosByList(@PathVariable int listId) {
        try {
            log.info("Retrieving todos for list id={}", listId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("todo.success.getTodosByList"), this.todoService.getTodosByList(listId));
        } catch (Exception e) {
            log.error("Error retrieving todos for list id={}, errorMessage={}", listId, e.getMessage(), e);
            return new ResponseError<>(HttpStatus.OK.value(), Translator.toLocale("todo.error.getTodosByList"));
        }
    }
}
