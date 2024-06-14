package com.flow.homework.api.whitelist;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flow.homework.api.support.response.BaseResponse;
import com.flow.homework.api.whitelist.request.DeleteIpRequest;
import com.flow.homework.api.whitelist.request.SaveIpRequest;
import com.flow.homework.api.whitelist.request.SearchRequest;
import com.flow.homework.api.whitelist.response.WhiteListIpResponse;
import com.flow.homework.api.whitelist.response.WhiteListSaveResponse;
import com.flow.homework.api.whitelist.response.WhiteListViewResponse;
import com.flow.homework.api.whitelist.usecase.WhiteListDeleteUseCase;
import com.flow.homework.api.whitelist.usecase.WhiteListSaveUseCase;
import com.flow.homework.api.whitelist.usecase.WhiteListSearchUseCase;
import com.flow.homework.api.whitelist.usecase.WhiteListViewUseCase;
import com.flow.homework.domain.whitelist.entity.WhiteList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WhiteListController {

	private final WhiteListViewUseCase whiteListViewUseCase;
	private final WhiteListSaveUseCase whiteListSaveUseCase;
	private final WhiteListSearchUseCase whiteListSearchUseCase;
	private final WhiteListDeleteUseCase whiteListDeleteUseCase;

	/**
	 * 규칙 View API
	 * [Get] /
	 * @return BaseResponse<>
	 */
	@GetMapping("/")
	public String whiteListView(@RequestParam(defaultValue = "0") int page,
										 @RequestParam(defaultValue = "10") int size,
										 Model model) {

		Page<WhiteListViewResponse> view = whiteListViewUseCase.view(page, size);

		model.addAttribute("whiteList", view);
		return "main";
	}

	/**
	 * 현재 IP 불러오기 API
	 * [Get] /ip
	 * @return BaseResponse<String>
	 */
	@GetMapping("/ip")
	@ResponseBody
	public BaseResponse<WhiteListIpResponse> getIp(HttpServletRequest request) {
		String clientIp = (String)request.getAttribute("clientIp");

		return new BaseResponse<>(new WhiteListIpResponse(clientIp));
	}

	/**
	 * IP 저장 API
	 * [POST] /save
	 * @return BaseResponse<WhiteListSaveResponse>
	 */
	@PostMapping("/save")
	@ResponseBody
	public BaseResponse<WhiteListSaveResponse> save(@RequestBody SaveIpRequest request) {
		WhiteList save = whiteListSaveUseCase.save(request);

		return new BaseResponse<>(new WhiteListSaveResponse(save));
	}


	/**
	 * IPList 검색 API
	 * [GET] /search
	 * @return String
	 */
	@GetMapping("/search")
	public String search(Model model, @Valid SearchRequest request,@PageableDefault Pageable pageable) {
		log.info("SearchRequest: description={}, startTime={}, endTime={}", request.getDescription(), request.getStartTime(), request.getEndTime());
		Page<WhiteListViewResponse> search = whiteListSearchUseCase.search(request, pageable);

		model.addAttribute("whiteList", search);

		int currentPage = search.getNumber();
		int totalPage = search.getTotalPages();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPage);

		return "main";
	}

	/**
	 * IPList 삭제 API
	 * [DELETE] /delete/{id}
	 * @return BaseResponse<Boolean>
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public BaseResponse<Boolean> delete(@PathVariable("id")Long id) {

		Boolean result = whiteListDeleteUseCase.delete(id);
		return new BaseResponse<>(result);
	}

}
