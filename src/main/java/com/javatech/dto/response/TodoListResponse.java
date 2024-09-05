package com.javatech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class TodoListResponse {
    private Long id;
    private String listName;
    private Date created_at;
    private Date updated_at;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object todo;
}
