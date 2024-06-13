package com.flow.homework.api.whitelist;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.flow.homework.api.whitelist.request.DeleteIpRequest;
import com.flow.homework.api.whitelist.request.GetIpRequest;
import com.flow.homework.api.whitelist.request.SaveIpRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WhiteListController {

	/**
	 * 현재 IP 불러오기 API
	 * [Get] /ip
	 * @return BaseResponse<>
	 */
	@GetMapping
	public String getIp(HttpServletRequest request) {
		String clientIp = (String)request.getAttribute("clientIp");
		log.info("clientIp : {}", clientIp);

		return clientIp;
	}

	/**
	 * IP 저장 API
	 * [POST] /save
	 * @return BaseResponse<>
	 */
	@PostMapping
	public void save(SaveIpRequest request) {
		log.info("request.getIp() = {}", request.getIp());
		log.info("request.getDescription() = {}", request.getDescription());
		log.info("request.getStartTime() = {}", request.getStartTime().toString());
		log.info("request.getEndTime() = {}", request.getEndTime().toString());

	}


	/**
	 * IPList 검색 API
	 * [GET] /search
	 * @return BaseResponse<>
	 */
	@GetMapping
	public void search(GetIpRequest request) {
		log.info("검색기능");
	}

	/**
	 * IPList 삭제 API
	 * [DELETE] /delete
	 * @return BaseResponse<>
	 */
	@DeleteMapping
	public void delete(DeleteIpRequest request) {
		Long id = request.getId();
		log.info("id : {}", id);
		// 해당 WhiteList의 status를 DELETE로 변경하는 usecase
	}

}
