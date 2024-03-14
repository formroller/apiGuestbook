package com.example.jwtsecond.domain.todo.repository.search;

import com.example.jwtsecond.domain.todo.dto.TodoDTO;
import com.example.jwtsecond.global.page.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {
    Page<TodoDTO> list(PageRequestDTO requestDTO);
}
