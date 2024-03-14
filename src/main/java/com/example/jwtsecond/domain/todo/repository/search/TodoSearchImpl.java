package com.example.jwtsecond.domain.todo.repository.search;

import com.example.jwtsecond.domain.todo.dto.TodoDTO;
import com.example.jwtsecond.domain.todo.entity.QTodo;
import com.example.jwtsecond.domain.todo.entity.Todo;
import com.example.jwtsecond.global.page.PageRequestDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<TodoDTO> list(PageRequestDTO requestDTO) {
        // todo 1. from-to 이용한 검색 조건 구현
        QTodo todo = QTodo.todo;
        JPQLQuery<Todo> query = from(todo);

        if(requestDTO.getFrom() != null && requestDTO.getTo()!= null){
            BooleanBuilder fromToBuilder = new BooleanBuilder();
            fromToBuilder.and(todo.dueDate.goe(requestDTO.getFrom())); // goe = Grate or Equal
            fromToBuilder.and(todo.dueDate.loe(requestDTO.getTo())); // loe = Less or Equal
            query.where(fromToBuilder);
        }
        // todo 2. complete 해당하는 검색 조건 구현
        if(requestDTO.getCompleted() != null){
            query.where(todo.complete.eq(requestDTO.getCompleted()));
        }

        if(requestDTO.getKeyword() != null){
            query.where(todo.title.contains(requestDTO.getKeyword()));
        }

        this.getQuerydsl().applyPagination(requestDTO.getPageable("tno"),query);

        JPQLQuery<TodoDTO> dtoQuery = query.select(Projections.bean(TodoDTO.class,
                todo.tno,
                todo.title,
                todo.dueDate,
                todo.complete,
                todo.writer
                ));

        List<TodoDTO> list = dtoQuery.fetch();

        long count = dtoQuery.fetchCount();

        return new PageImpl<>(list, requestDTO.getPageable("tno"), count);
    }
}
