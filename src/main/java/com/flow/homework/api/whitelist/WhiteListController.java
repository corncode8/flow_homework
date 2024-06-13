package com.flow.homework.api.whitelist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.flow.homework.api.whitelist.request.DeleteIpRequest;
import com.flow.homework.api.whitelist.request.GetIpRequest;
import com.flow.homework.api.whitelist.request.SaveIpRequest;
import com.flow.homework.domain.whitelist.entity.WhiteList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WhiteListController {

	/**
	 * 규칙 View API
	 * [Get] /
	 * @return BaseResponse<>
	 */
	@GetMapping("/")
	public List<WhiteList> whiteListView() {
		List<WhiteList> whiteLists = new ArrayList<>();
		WhiteList whiteList = new WhiteList(
			"127.0.0.1", "test_description", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
		);

		whiteLists.add(whiteList);
		return whiteLists;
	}

	/**
	 * 현재 IP 불러오기 API
	 * [Get] /ip
	 * @return BaseResponse<>
	 */
	@GetMapping("/ip")
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
	@PostMapping("/save")
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
	@GetMapping("/search")
	public void search(GetIpRequest request) {
		log.info("검색기능");
	}

	/**
	 * IPList 삭제 API
	 * [DELETE] /delete
	 * @return BaseResponse<>
	 */
	@DeleteMapping("/delete")
	public void delete(DeleteIpRequest request) {
		Long id = request.getId();
		log.info("id : {}", id);
		// 해당 WhiteList의 status를 DELETE로 변경하는 usecase
	}

}
