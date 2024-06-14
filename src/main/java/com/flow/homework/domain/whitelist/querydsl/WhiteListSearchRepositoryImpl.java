package com.flow.homework.domain.whitelist.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.flow.homework.api.whitelist.response.WhiteListViewResponse;
import com.flow.homework.domain.whitelist.entity.QWhiteList;
import com.flow.homework.domain.whitelist.entity.WhiteList;
import com.flow.homework.domain.whitelist.querydsl.dto.SearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WhiteListSearchRepositoryImpl implements WhiteListSearchRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<WhiteListViewResponse> searchWhiteList(SearchDto searchDto, Pageable pageable) {
		QWhiteList whiteList = QWhiteList.whiteList;
		BooleanBuilder condition = searchCondition(searchDto);

		List<WhiteListViewResponse> resultList = queryFactory
			.select(Projections.constructor(
				WhiteListViewResponse.class,
				whiteList.id,
				whiteList.ipAddress,
				whiteList.description,
				whiteList.startTime,
				whiteList.endTime
			))
			.from(whiteList)
			.where(condition)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(whiteList.createdAt.desc())
			.fetch();

		long total = queryFactory.selectFrom(whiteList)
			.where(condition)
			.fetchCount();

		return new PageImpl<>(resultList,pageable, total);
	}

	private BooleanBuilder searchCondition(SearchDto searchDto) {
		QWhiteList whiteList = QWhiteList.whiteList;
		BooleanBuilder condition = new BooleanBuilder();

		condition.and(whiteList.status.eq(WhiteList.State.ACTIVE));
		if (!searchDto.getDescription().isEmpty()) {
			condition.and(whiteList.description.like("%" + searchDto.getDescription() + "%"));
		}
		if (searchDto.getStartTime() != null && searchDto.getEndTime() != null) {
			condition.and(
				whiteList.startTime.loe(searchDto.getEndTime())
					.and(whiteList.endTime.goe(searchDto.getStartTime()))
			);
		}
		return condition;
	}
}
