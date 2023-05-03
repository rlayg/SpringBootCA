//package com.oracle.oBootMybatis01.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.kakao.jPanda.kyg.domain.ChargeDto;
//import com.kakao.jPanda.kyg.domain.CouponUseDto;
//import com.kakao.jPanda.kyg.service.ChargeService;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//@Slf4j
//@Controller
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
////	충전 메인페이지
//	@GetMapping("/")
//	public String chargePage() {
//		return "kyg/chargePage";
//	}
//	
//	
//	
//	
////	밤부 충전
//	@ResponseBody
//	@PostMapping("/charge") 
//	public Map<String, String> chargeAdd(@RequestBody ChargeDto chargeDto) {
//		
//		log.info("ChargeContoller charge() Start...");
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
//	
////	이용 가능한 쿠폰 체크
//	@GetMapping(value = "/check-available-coupon")
//	@ResponseBody
//	public CouponUseDto checkAvailableCoupon(CouponUseDto couponUseDto) {
//
//		CouponUseDto checkedcouponUseDto = new CouponUseDto();				
//		log.info("ChargeContoller couponDetails() Start...");
//		log.info("ChargeContoller checkAvailableCoupon couponUseDto.toString() -> {}", couponUseDto.toString());
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
//		log.info("Controller checkAvailableCoupon resultInt-> {}", resultInt);
//		log.info("Controller checkAvailableCoupon couponValue-> {}", couponValue);
//		
//		return checkedcouponUseDto;
//	}
//	
//	// 총보유밤부
//	@GetMapping(path = "/members/{memberId}/total-bamboo")
//	@ResponseBody
//	public String  totalBamboo(@PathVariable String memberId) {
//		log.info("Controller totalBamboo Start...");
//		log.info("Controller totalBamboo memberId -> {}", memberId);
//		
//		Long foundTotalBamboo = chargeService.findTotalBamboo(memberId);
//		
//		String  foundTotalBambooStr =  Long.toString(foundTotalBamboo);
//		
//		log.info("Controller totalBamboo calculatedTotalBamboo -> {}", foundTotalBamboo);
//		
//		return foundTotalBambooStr;
//	}
//	
//	
//}
//	
//
//
