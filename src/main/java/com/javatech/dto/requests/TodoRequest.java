package com.javatech.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javatech.dto.validator.EnumPattern;
import com.javatech.utils.TodoStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Builder
public class TodoRequest {
    @NotBlank(message = "title must be not blank!")
    private String title;

    @NotBlank(message = "description must be not blank!")
    private String description;

    @NotNull(message = "dueDate must be not null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dueDate;

    @NotNull(message = "status must be not null!")
    @EnumPattern(name = "status", regexp = "INCOMPLETE|IN_PROGRESS|COMPLETE", message = "status invalid format!")
    private TodoStatus status;

    @Min(1)
    private long todoListId;
}
