package com.javatech.dto.response;

import com.javatech.utils.TodoStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
@Getter
@Builder
public class TodoResponse {
    private long id;
    private String title;
    private String description;
    private Date dueDate;
    private TodoStatus status;
    private Date createdAt;
    private Date updatedAt;
}
