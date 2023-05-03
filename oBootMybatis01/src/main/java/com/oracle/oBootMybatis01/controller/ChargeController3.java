//package com.kakao.jPanda.kyg.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.kakao.jPanda.kyg.domain.ChargeDto;
//import com.kakao.jPanda.kyg.domain.CouponUseDto;
//import com.kakao.jPanda.kyg.domain.PaymentDto;
//import com.kakao.jPanda.kyg.service.ChargeService;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//@Slf4j
//
//@RequestMapping("/charge")		
//public class ChargeController {
//	
//	private final ChargeService chargeService;
//	
//	@Autowired
//	public ChargeController(ChargeService chargeService) {
//		this.chargeService = chargeService;
//	}
//	
//	
//	/*
//	 * 메인페이지
//	 * 결제수단, RATIO를 테이블에 List형식으로 나타냄
//	 * 충전내역, chargeHistory를 테이블에 List형식으로 나타냄
//	 * Model	TB payment -> method, bonusRatio 
//	 * @param	HttpSession, ChargeHistoryDto, Model
//	 * @return	kyg/chargePage
//	 */
////	@GetMapping(value = "")
////	public String chargePage(HttpSession session, ChargeHistoryDto chargeHistoryDto, Model model) {
////		String chargerId = (String) session.getAttribute("memberId");
////		PaymentDto paymentDto = new PaymentDto();
////		//ChargeHistoryDto selectChargeHistoryDto = new ChargeHistoryDto();
////		ChargeHistoryDto chargeHistoryListDto = new ChargeHistoryDto();
////		log.info("ChargeContoller chargePage() Start...");
////		
////		chargeHistoryListDto.setChargerId(chargerId);
////		log.info("ChargeContoller chargePage() chargerId -> {}", chargerId);
////		
////		List<PaymentDto> getPaymentList = chargeService.findPaymentList(paymentDto);
////		log.info("ChargeContoller chargePage() getPaymentList.size() -> {}", getPaymentList.size());
////		
////		List<ChargeHistoryDto> getChargeHistoryList = chargeService.findChargeHistoryList(chargeHistoryListDto);
////		log.info("ChargeContoller chargePage() getChargeHistoryList.size() -> {}", getChargeHistoryList.size());
////		
////		model.addAttribute("listPayment", getPaymentList);
////		model.addAttribute("listChargeHistory", getChargeHistoryList);
////		
////		return "kyg/chargePage";
////	}
//	
//	@GetMapping(value = "")
//	public String chargePage(HttpSession session, Model model) {
//		String chargerId = (String) session.getAttribute("memberId");
//		//PaymentDto paymentDto = new PaymentDto();
//		//ChargeHistoryDto selectChargeHistoryDto = new ChargeHistoryDto();
//		ChargeDto chargeDto = new ChargeDto();
//		log.info("ChargeContoller chargePage() Start...");
//		
//		chargeDto.setChargerId(chargerId);
//		log.info("ChargeContoller chargePage() chargerId -> {}", chargerId);
//		
//		List<PaymentDto> getPaymentList = chargeService.findPaymentList();
//		log.info("ChargeContoller chargePage() getPaymentList.size() -> {}", getPaymentList.size());
//		
//		//List<chargeDto> getChargeHistoryList = chargeService.findChargeHistoryList(chargerId);
//		List<ChargeDto> getBambooChargeList = chargeService.findBambooChargeListbyChargerId(chargerId);
//		log.info("ChargeContoller chargePage() getChargeHistoryList.size() -> {}", getBambooChargeList.size());
//		
//		model.addAttribute("listPayment", getPaymentList);
//		model.addAttribute("listChargeHistory", getBambooChargeList);
//		
//		return "kyg/chargePage";
//	}
//	
//	/*
//	 * 밤부 충전
//	 * chargePage에서 ajax 요청 처리
//	 * 전달된 data를 ChargeDto에 저장 후 DB에 삽입
//	 * @param	ChargeDto
//	 * @return	resultMap	/	resultMap을 return하여 callback시 success, fail에 따라 resultMap.put()을 console에 출력 
//	 */
//	@ResponseBody
//	@PostMapping("/charge") 
//	public Map<String, String> chargeAdd(@RequestBody ChargeDto chargeDto, HttpSession session) {
//		String chargerId = (String) session.getAttribute("memberId");
//		log.info("ChargeContoller charge() Start...");
//		log.info("ChargeContoller checkAvailableCoupon() chargerId -> {}", chargerId);
//		chargeDto.setChargerId(chargerId);
//		log.info("ChargeContoller checkAvailableCoupon() couponUseDto.toString() -> {}", chargeDto.toString());
//		
//		int resultCharge = chargeService.addCharge(chargeDto);
//		Map<String, String> resultMap = new HashMap<>();
//		
//		
//		if(resultCharge > 0) {
//			log.info("ChargeController charge() resultCharge 완료");
//			 resultMap.put("result", "success");
//			return resultMap;
//			
//		} else {
//			log.error("ChargeContoller charge() resultCharge 실패");
//			resultMap.put("result", "fail");
//			return resultMap;
//		}
//		
//	}
//	
//	/*
//	 *	이용 가능한 쿠폰 체크
//	 *	chargePage에서 ajax 요청 처리
//	 *	전달받은 memberId와 couponCode를 TB coupon, TB coupon_use와 비교하여 validation check
//	 *	사용 불가능한 쿠폰 : resultInt 0을 반환, 사용 가능한 쿠폰 : resultInt 1은 반환 ,couponValue를 반환
//	 *	@param	CouponUseDto
//	 *	@return	checkedcouponUseDto
//	 */
//	@GetMapping(value = "/check-available-coupon")
//	@ResponseBody
//	public CouponUseDto checkAvailableCoupon(HttpSession session, CouponUseDto couponUseDto) {
//		String memberId = (String) session.getAttribute("memberId");
//		CouponUseDto checkedcouponUseDto = new CouponUseDto();				
//		log.info("ChargeContoller checkAvailableCoupon() Start...");
//		log.info("ChargeContoller checkAvailableCoupon() memberId -> {}", memberId);
//		couponUseDto.setMemberId(memberId);
//		log.info("ChargeContoller checkAvailableCoupon() couponUseDto.toString() -> {}", couponUseDto.toString());
//		
//		// 사용 결과만 가져옴 -> 사용 가능한 쿠폰, 사용 했던 쿠폰을 비교하여, 회원이 사용했던 이력이 있는 쿠폰의 결과를 가져와 사용가능 여부를 따짐
//		int resultInt = chargeService.checkAvailableCoupon(couponUseDto);
//		
//		// 충전 금액과 쿠폰의 금액을 차감해 실제 충전에 사용되는 금액을 구하기 위해 쿠폰의 금액을 가져옴
//		Long couponValue = chargeService.getAvailAmountCoupon(couponUseDto);
//		
//		checkedcouponUseDto.setResult(resultInt);
//		checkedcouponUseDto.setCouponValue(couponValue);
//		
//		log.info("ChargeContoller checkAvailableCoupon resultInt-> {}", resultInt);
//		log.info("ChargeContoller checkAvailableCoupon couponValue-> {}", couponValue);
//		
//		return checkedcouponUseDto;
//	}
//	
//	/*
//	 * 총보유밤부
//	 * chargePage에서 ajax 요청 처리
//	 * memberId에 따른 총 보유 bamboo를 계산하여 반환
//	 * @param	memberId
//	 * @return	foundTotalBambooStr
//	 */
//	@GetMapping(path = "/members/total-bamboo")
//	@ResponseBody
//	public String  totalBambooByMemberId(HttpSession session) {
//		String memberId = (String) session.getAttribute("memberId");
//		log.info("ChargeContoller totalBambooByMemberId Start...");
//		log.info("ChargeContoller totalBambooByMemberId() memberId -> {}", memberId);
//		
//		Long foundTotalBambooByMemberId = chargeService.findTotalBambooByMemberId(memberId);
//		
//		String  foundTotalBambooByMemberIdStr =  Long.toString(foundTotalBambooByMemberId);
//		
//		log.info("ChargeContoller totalBambooByMemberId calculatedTotalBamboo -> {}", foundTotalBambooByMemberIdStr);
//		
//		return foundTotalBambooByMemberIdStr;
//	}
//	
//	
//	
//}
//	
//
//
