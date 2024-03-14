package com.example.jwtsecond.domain.todo.repository;

import com.example.jwtsecond.domain.todo.entity.Todo;
import com.example.jwtsecond.domain.todo.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {

}
