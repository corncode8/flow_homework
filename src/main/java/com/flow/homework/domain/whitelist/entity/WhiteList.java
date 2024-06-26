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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WhiteList extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "ip_address", nullable = false, length = 45)
	private String ipAddress;

	@Column(nullable = false, length = 20)
	private String description;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 10)
	private State status = State.ACTIVE;

	public enum State {
		ACTIVE, DELETE
	}

	public void delete() {
		this.status = State.DELETE;
	}

	public WhiteList(String ipAddress, String description, LocalDateTime startTime, LocalDateTime endTime) {
		this.ipAddress = ipAddress;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public static WhiteList create(String ip, String description, LocalDateTime startTime, LocalDateTime endTime) {
		return new WhiteList(ip, description, startTime, endTime);
	}


}
