package com.example.jwtsecond.domain.todo.controller;

import com.example.jwtsecond.domain.todo.dto.TodoDTO;
import com.example.jwtsecond.domain.todo.service.TodoService;
import com.example.jwtsecond.global.page.PageRequestDTO;
import com.example.jwtsecond.global.page.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
@Log4j2
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO){

        log.info(todoDTO);

        Long tno = todoService.register(todoDTO);

        return Map.of("tno", tno);

//        return todoService.register(todoDTO);
//        return new ResponseEntity<>(todoService.register(todoDTO), HttpStatus.OK);
    }

    @GetMapping("/{tno}")
    public TodoDTO read(@PathVariable("tno")Long tno){
        log.info("** read tno: "+tno);

        return todoService.read(tno);
    }

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO requestDTO){
        return todoService.list(requestDTO);
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> delete(@PathVariable("tno")Long tno){
        todoService.remove(tno);

        return Map.of("result", "deleted");
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable("tno")Long tno, @RequestBody TodoDTO todoDTO){

        todoDTO.setTno(tno);

        todoService.modify(todoDTO);

        return Map.of("result", "Modified");
    }
}

