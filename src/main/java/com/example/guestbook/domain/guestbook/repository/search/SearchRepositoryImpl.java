package com.example.guestbook.domain.guestbook.repository.search;

import com.example.guestbook.domain.guestbook.dto.GuestbookListAllDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.entity.QGuestbook;
import com.example.guestbook.domain.image.dto.ImageResultDTO;
import com.example.guestbook.domain.member.entity.QMember;
import com.example.guestbook.domain.review.entity.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchRepositoryImpl extends QuerydslRepositorySupport implements SearchRepository{

    public SearchRepositoryImpl() {
        super(Guestbook.class);
    }

    @Override
    public Guestbook search1() {
        QGuestbook qGuestbook = QGuestbook.guestbook;
        QReview qReview = QReview.review;
        QMember qMember = QMember.member;

        JPQLQuery<Guestbook> jpqlQuery = from(qGuestbook);
        jpqlQuery.leftJoin(qMember).on(qGuestbook.writer.eq(qMember));
        jpqlQuery.leftJoin(qReview).on(qReview.guestbook.eq(qGuestbook));

//        jpqlQuery.select(qGuestbook).where(qGuestbook.gno.eq(11L));
//        jpqlQuery.select(qGuestbook, qMember.email, qReview.count()).groupBy(qGuestbook);

        JPQLQuery<Tuple> tuple = jpqlQuery.select(qGuestbook, qMember.email, qReview.count());
        tuple.groupBy(qGuestbook);

        log.info(jpqlQuery);
        log.info(tuple);

        List<Guestbook> result = jpqlQuery.fetch();
        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        // todo. join table
        // todo. 검색 조건 처리
        // todo.정렬 처리
        // todo. page 처리
        // todo. fetch count
        log.info(" ======== SearchPage ==========");

        QGuestbook guestbook = QGuestbook.guestbook;
        QMember member = QMember.member;
        QReview review = QReview.review;

        JPQLQuery<Guestbook> jpqlQuery = from(guestbook);
        jpqlQuery.leftJoin(member).on(guestbook.writer.eq(member));
        jpqlQuery.leftJoin(review).on(review.guestbook.eq(guestbook));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(guestbook, member, review.count());


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = guestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        // 검색 조건
        if(type != null){
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t:typeArr){
                switch (t){
                    case "t" -> conditionBuilder.or(guestbook.title.contains(keyword));
                    case "w" -> conditionBuilder.or(member.email.contains(keyword));
                    case "c" -> conditionBuilder.or(guestbook.content.contains(keyword));
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);


        // sort 처리
        Sort sort = pageable.getSort();

        sort.stream().forEach(order->{
            Order direction = order.isAscending() ? Order.ASC : Order.DESC; // 정렬
            String prop = order.getProperty(); // 객체 속성

            PathBuilder orderByExpression = new PathBuilder(Guestbook.class, "guestbook");

            tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(guestbook);

        // page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        log.info("Result : "+result);


        long count = tuple.fetchCount();
        log.info("Count : "+count);

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count
        );
    }

    @Override
    public Page<GuestbookListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {
        QGuestbook qGuestbook = QGuestbook.guestbook;
        QReview qReview = QReview.review;

        JPQLQuery<Guestbook> guestbookJPQLQuery = from(qGuestbook);
        guestbookJPQLQuery.leftJoin(qReview).on(qReview.guestbook.eq(qGuestbook)); // left join

        if((types != null && types.length > 0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            String[] typeArr = StringUtils.split(Arrays.toString(types),"");

            for(String type:typeArr){
                switch (type){
                    case "t" -> booleanBuilder.or(qGuestbook.title.contains(keyword));
                    case "w" -> booleanBuilder.or(qGuestbook.writer.email.contains(keyword));
                    case "c" -> booleanBuilder.or(qGuestbook.content.contains(keyword));
                }
            }// end for
            guestbookJPQLQuery.where(booleanBuilder);
        }
        guestbookJPQLQuery.groupBy(qGuestbook);

        getQuerydsl().applyPagination(pageable, guestbookJPQLQuery); // paging

        JPQLQuery<Tuple> tupleJPQLQuery = guestbookJPQLQuery.select(qGuestbook, qReview.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<GuestbookListAllDTO> dtoList = tupleList.stream().map(tuple -> {
            Guestbook guestbook = (Guestbook) tuple.get(qGuestbook);
            long reviewCnt = tuple.get(1, Long.class);

            GuestbookListAllDTO dto = GuestbookListAllDTO.builder()
                    .gno(guestbook.getGno())
                    .title(guestbook.getTitle())
                    .writer(String.valueOf(guestbook.getWriter()))
                    .regDate(guestbook.getRegDate())
                    .reviewCnt(reviewCnt)
                    .build();

            // imageDTO 처리할 부분
            List<ImageResultDTO> imageDTOS = guestbook.getImageSet().stream().sorted().map(image->
                ImageResultDTO.builder()
                        .uuid(image.getUuid())
                        .imgName(image.getImgName())
                        .ord(image.getOrd())
                        .build()
            ).collect(Collectors.toList());

            dto.setGuestbookImages(imageDTOS);
            return dto;

        }).collect(Collectors.toList());

        long totalCount = guestbookJPQLQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, totalCount);
    }

}
