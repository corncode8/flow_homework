package com.flow.homework.domain.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {

	@CreationTimestamp
	@Column(name = "createdAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updatedAt", nullable = false)
	private LocalDateTime updatedAt;

}