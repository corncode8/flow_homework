package com.flow.homework.domain.whitelist.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flow.homework.domain.whitelist.entity.WhiteList;

public interface WhiteListJpaRepository extends JpaRepository<WhiteList, Long> {
}
