package com.example.jwtsecond.domain.todo.service;

import com.example.jwtsecond.domain.todo.dto.TodoDTO;
import com.example.jwtsecond.domain.todo.entity.Todo;
import com.example.jwtsecond.global.page.PageRequestDTO;
import com.example.jwtsecond.global.page.PageResponseDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoService {
    Long register(TodoDTO todoDTO);

    TodoDTO read(Long tno);

    PageResponseDTO<TodoDTO> list(PageRequestDTO requestDTO);

    void remove(Long tno);

    void modify(TodoDTO todoDTO);


    /*직렬화, 역직렬화*/
    default Todo toEntity(TodoDTO dto){
        Todo todo = Todo.builder()
                .tno(dto.getTno())
                .writer(dto.getWriter())
                .title(dto.getTitle())
                .dueDate(dto.getDueDate())
                .complete(dto.isComplete())
                .build();

        return todo;
    }

    default TodoDTO toDto(Todo todo){
        TodoDTO dto = TodoDTO.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .dueDate(todo.getDueDate())
                .complete(todo.isComplete())
                .writer(todo.getWriter())
                .build();

        return dto;
    }
}
