package com.oracle.oBootMybatis01.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// och06_AOP3
@Aspect	// 핵심관심사와 종단관심사를 이어주는애 
@Component
public class LogAop {
	
	// aop3.buz 패키지 안에 있는 모든 메소드
		@Pointcut("within(com.oracle.oBootMybatis01.dao.EmpDao*)")
		private void pointcutMethod() {
		}

//		loggerAop를 pointcutMethod와 Around방식으로연결시킬거야
		@Around("pointcutMethod()")
		public Object loggerAop(ProceedingJoinPoint joinpoint) throws Throwable {
			//	핵심업무에 사용 Method
				String signatureStr = joinpoint.getSignature().toShortString();
				System.out.println(signatureStr + " is start...");
			//	Return the current time in milliseconds
				long startTime = System.currentTimeMillis();
				
				
				try {
//					핵심관심사 Method 수행 --> aop2.buz.Student.getStudentInfo()
					Object obj = joinpoint.proceed(); 
					return obj;
				} finally {
					long endTime = System.currentTimeMillis(); /* endTime */
					System.out.println(signatureStr + " is finished");
					System.out.println(signatureStr + " 경과시간 : " + (endTime - startTime));
				}
				
			}
		
		@Before("within(aop3.buz.*)")
		public void beforAdvice() {
			System.out.println("beforAdvice()");
		}
		
		@After("within(aop3.buz.*)")
		public void afterAdvice() {
			System.out.println("afterAdvice())");
		}

}
