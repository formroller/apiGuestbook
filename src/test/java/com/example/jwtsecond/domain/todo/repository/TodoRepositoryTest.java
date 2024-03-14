package com.example.jwtsecond.domain.todo.repository;

import com.example.jwtsecond.domain.todo.dto.TodoDTO;
import com.example.jwtsecond.domain.todo.entity.Todo;
import com.example.jwtsecond.global.page.PageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Todo todo = Todo.builder()
                    .title("Todo --- "+i)
                    .dueDate(LocalDate.of(2024, (i%12)+1, (i%30)+1))
                    .writer("user"+(i%10))
                    .complete(false)
                    .build();

            todoRepository.save(todo);
        });
    }

    @DisplayName("검색 조건 테스트")
    @Test
    public void testSearch(){
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .from(LocalDate.of(2024, 12, 11))
                .to(LocalDate.of(2024, 12, 30))
                .build();

        Page<TodoDTO> result = todoRepository.list(requestDTO);

        result.forEach(dto -> log.info(dto));
    }

}