package com.example.jwtsecond.domain.todo.service;

import com.example.jwtsecond.domain.todo.dto.TodoDTO;
import com.example.jwtsecond.domain.todo.entity.Todo;
import com.example.jwtsecond.domain.todo.repository.TodoRepository;
import com.example.jwtsecond.global.page.PageRequestDTO;
import com.example.jwtsecond.global.page.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Override
    public Long register(TodoDTO todoDTO) {

        Todo todo = toEntity(todoDTO);

        todoRepository.save(todo);

        return todo.getTno();
    }

    public TodoDTO read(Long tno){
        Todo result= todoRepository.findById(tno).orElseThrow();

        TodoDTO todoDTO = toDto(result);

        return todoDTO;
    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO requestDTO) {

        Page<TodoDTO> result = todoRepository.list(requestDTO);

        return PageResponseDTO.<TodoDTO>withAll()
                .requestDTO(requestDTO)
                .dtoList(result.toList())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }

    @Override
    public void modify(TodoDTO todoDTO) {
        Todo result = todoRepository.findById(todoDTO.getTno()).orElseThrow();

        result.changeTitle(todoDTO.getTitle());
        result.changeComplete(todoDTO.isComplete());
        result.changeDueDate(todoDTO.getDueDate());

        todoRepository.save(result);
    }
}
