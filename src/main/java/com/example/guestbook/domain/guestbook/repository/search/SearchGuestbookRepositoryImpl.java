//package com.example.guestbook.domain.guestbook.repository.search;
//
//import com.example.guestbook.domain.guestbook.entity.Guestbook;
//import com.example.guestbook.domain.guestbook.entity.QGuestbook;
//import com.example.guestbook.global.page.PageRequestDTO;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//
//@Log4j2
//public class SearchGuestbookRepositoryImpl extends QuerydslRepositorySupport implements SearchGuestbookRepository {
//
//    public SearchGuestbookRepositoryImpl() {
//        super(Guestbook.class);
//    }
//
////    @Override
////    public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
////        QGuestbook qGuestbook = QGuestbook.guestbook;
////
////        String type = requestDTO.getType();
////        String keyword = requestDTO.getKeyword();
////
////        BooleanBuilder booleanBuilder = new BooleanBuilder();
////        BooleanExpression expression = qGuestbook.gno.gt(0);
////
////        booleanBuilder.and(expression);
////
////        if(type == null || type.trim().length()==0){
////            return booleanBuilder;
////        }
////
////
////        String[] typeArr = type.split("");
////
////        BooleanBuilder conditionalBuilder = new BooleanBuilder();
////
////        for(String t:typeArr){
////            switch (t){
////                case "t" -> conditionalBuilder.or(qGuestbook.title.contains(keyword));
////                case "c" -> conditionalBuilder.or(qGuestbook.content.contains(keyword));
////                case "w" -> conditionalBuilder.or(qGuestbook.writer.contains(keyword));
////            }
////            booleanBuilder.and(conditionalBuilder);
////        }
////        return booleanBuilder;
////    }
//
//}
