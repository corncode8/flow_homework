package com.flow.homework.domain.whitelist.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flow.homework.domain.whitelist.entity.WhiteList;

public interface WhiteListJpaRepository extends JpaRepository<WhiteList, Long> {

	@Query("select count(w) from WhiteList w where w.status = :status")
	int activeWhiteListNum(@Param("status") WhiteList.State status);

}
