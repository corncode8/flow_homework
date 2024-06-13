package com.flow.homework.domain.whitelist.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.flow.homework.domain.common.BaseEntity;

import lombok.Getter;

@Entity
@Getter
public class WhiteList extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "ip_address", nullable = false, length = 45)
	private String ipAddress;

	@Column(nullable = false, length = 50)
	private String description;

	@Column(name = "start_time", nullable = false)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 10)
	private State status = State.ACTIVE;

	public enum State {
		ACTIVE, DELETE
	}

	public WhiteList(String ipAddress, String description, LocalDateTime startTime, LocalDateTime endTime,
		State status) {
		this.ipAddress = ipAddress;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}
}
