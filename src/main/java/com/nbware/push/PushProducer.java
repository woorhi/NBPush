package com.nbware.push;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class PushProducer {
	
	 private static Logger logger = LoggerFactory.getLogger(PushProducer.class);

	public static void main(String[] args) {
		try {
			Log4jConfigurer.initLogging("classpath:config/log/log4j.xml",60000L);
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		logger.info("===========================START====================");
    	new ClassPathXmlApplicationContext("config/spring/applicationContext.xml");
	}
}