package com.javatech.model;

import com.javatech.utils.TodoStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "todos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo extends AbstractEntity<Long> implements Serializable {

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @ColumnDefault("'INCOMPLETE'")
    @Column(name = "status", columnDefinition = "todo_status not null")
    private TodoStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "list_id")
    private TodoList todoList;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;
}