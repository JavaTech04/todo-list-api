package com.javatech.controller;

import com.javatech.config.Translator;
import com.javatech.dto.response.ResponseData;
import com.javatech.dto.response.ResponseError;
import com.javatech.service.TodoListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${api.version}/todo-lists")
@Tag(name = "Todo List Controller", description = "Handles operations related to managing todo lists, including creating, updating, retrieving, and deleting todo items.")
@Validated
public class TodoListController {
    private final TodoListService todoListService;

    /**
     * {'pageNumber': 'pageNumber', 'pageSize': 'pageSize'}
     */
    @Operation(
            summary = "Get All Todo Lists",
            description = "Retrieve a paginated list of all todo lists for the authenticated user."
    )
    @GetMapping
    ResponseData<?> getAllTodoLists(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) @Min(5) int pageSize
    ) {
        try {
            log.info("Retrieving todo lists: pageNumber={}, pageSize={}", pageNumber, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("todo-lists.success.getAllTodoLists"), this.todoListService.getAllTodoLists(pageNumber, pageSize));
        } catch (Exception e) {
            log.error("Error retrieving todo lists: pageNumber={}, pageSize={}, errorMessage={}", pageNumber, pageSize, e.getMessage(), e);
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("todo-lists.error.getAllTodoLists"));
        }
    }

    /**
     * listName: length between 3 and 100
     */
    @Operation(
            summary = "Create Todo List",
            description = "Create a new todo list for the authenticated user by providing a name for the list."
    )
    @PostMapping
    ResponseData<?> createTodoList(@RequestBody @Length(min = 3, max = 100) String listName) {
        try {
            log.info("Creating a new todo list with name={}", listName);
            return new ResponseData<>(HttpStatus.OK.value(), Translator.toLocale("todo-lists.success.createTodoList"), this.todoListService.createTodoList(listName));
        } catch (Exception e) {
            log.error("Error creating todo list: listName={}, errorMessage={}", listName, e.getMessage(), e);
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("todo-lists.error.createTodoList"));
        }
    }

    /**
     * id: min 1
     * listName: length between 3 and 100
     */
    @Operation(
            summary = "Update Todo List",
            description = "Update the name of an existing todo list for the authenticated user by providing the list ID and new name."
    )
    @PatchMapping("/{id}")
    ResponseData<?> updateTodoList(
            @PathVariable @Min(1) long id,
            @RequestBody @Length(min = 3, max = 100) String listName
    ) {
        try {
            log.info("Updating todo list with id={} and new name={}", id, listName);
            this.todoListService.updateTodoList(id, listName);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("todo-lists.success.updateTodoList"));
        } catch (Exception e) {
            log.error("Error updating todo list: id={}, listName={}, errorMessage={}", id, listName, e.getMessage(), e);
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("todo-lists.error.updateTodoList"));
        }
    }

    /**
     * id: min 1s
     */
    @Operation(
            summary = "Delete Todo List",
            description = "Delete an existing todo list for the authenticated user by providing the list ID. All related todos will also be deleted."
    )
    @DeleteMapping("/{id}")
    ResponseData<?> deleteTodoList(@PathVariable @Min(1) long id) {
        try {
            log.info("Deleting todo list with id={}", id);
            this.todoListService.deleteTodoList(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("todo-lists.success.deleteTodoList"));
        } catch (Exception e) {
            log.error("Error deleting todo list: id={}, errorMessage={}", id, e.getMessage(), e);
            return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("todo-lists.error.deleteTodoList"));
        }
    }
}
