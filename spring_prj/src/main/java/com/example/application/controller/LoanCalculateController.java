package com.example.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import com.example.domain.dto.request.LoanRequestDto;
import com.example.domain.dto.response.LoanResponseDto;
import com.example.domain.service.PrincepalAndInterestLoanService;

/**
 * アカウント情報に対してのリクエストを処理します
 */
@Controller
public class LoanCalculateController {
    // Beenからserviceクラスを呼び出し
    @Autowired
    PrincepalAndInterestLoanService princepalAndInterestLoanService;

    @RequestMapping("/")
	public ModelAndView index(ModelAndView mav) {

        LoanRequestDto loanResponseDto = new LoanRequestDto();
		// 変数式
		// ・コントローラーからテンプレートに値を渡す
		// ・変数「msg」に値を設定
		mav.addObject("currentUri", "/"); // (2)

		// 使用するビューを設定
		mav.setViewName("index"); // (3)

		return mav;
	}

    /**
     * アカウント情報を全件取得します
     * 
     * @return List<AccountEntity> アカウント情報リスト
     */
    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public ModelAndView calculatePrincepalAndInterestLoan(@RequestBody MultiValueMap loanRequest, ModelAndView mav) {
        LoanRequestDto loanRequestDto = new LoanRequestDto(
            Optional.of(loanRequest.toSingleValueMap().get("userName").toString()),
            Optional.of(loanRequest.toSingleValueMap().get("totalBorrowingAmount").toString()),
            Optional.of(loanRequest.toSingleValueMap().get("annualInterest").toString()),
            Optional.of(loanRequest.toSingleValueMap().get("commission").toString()),
            Optional.of(loanRequest.toSingleValueMap().get("repaymentStartDate").toString()),
            Optional.of(loanRequest.toSingleValueMap().get("repaymentEndDate").toString())
        );

        LoanResponseDto loanResponseDto = princepalAndInterestLoanService.calculatePrincepalAndInterestLoan(loanRequestDto);
        
        // 変数式
		// ・コントローラーからテンプレートに値を渡す
		// ・変数「msg」に値を設定
		mav.addObject("loanResponseDto", loanResponseDto); // (2)
		mav.addObject("currentUri", "/calculate"); // (2)

		// 使用するビューを設定
		mav.setViewName("index"); // (3)
        
        return mav;
    }
}
