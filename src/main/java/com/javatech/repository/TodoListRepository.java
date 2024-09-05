package com.javatech.repository;

import com.javatech.model.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    Page<TodoList> findByUser_Id(Pageable pageable, Long userId);

    Optional<TodoList> findByUser_IdAndId(long userId, long id);
}
