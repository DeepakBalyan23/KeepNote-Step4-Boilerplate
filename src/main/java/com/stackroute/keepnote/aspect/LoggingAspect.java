package com.stackroute.keepnote.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggingAspect {
	private static Logger logger;
	 @Before("allControllers()")
	 public void beforeMethods() { 
		 logger.info("fffefe");
	 }
	 
	 @After("allControllers()")
	 public void afterMethods() { 
		 logger.info("fffefe");
	 }
	 
	 @AfterReturning(pointcut="allControllers()", returning="returnValue")
	 public void afterReturningMethods(String returnValue) { 
		 logger.info("fffefe");
		 //logger.debug(joinPoint.getSignature().getName());
		 logger.debug(returnValue);
	 }
	 
	@AfterThrowing(pointcut="allControllers()", throwing = "ex")
	 public void afterThrowingMethods(Exception ex) { 
		 logger.debug(ex.toString());
	 }
	
	@Pointcut("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void allControllers() {}
	/*
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
}
