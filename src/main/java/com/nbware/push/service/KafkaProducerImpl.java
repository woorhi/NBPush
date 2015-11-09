package com.nbware.push.service;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbware.push.model.KafkaProperties;

@Service
public class KafkaProducerImpl implements com.nbware.push.service.KafkaProducer {
	private static Logger logger = LoggerFactory.getLogger(KafkaProducerImpl.class);

	private Properties props = new Properties();
	private KafkaProducer<String,String> producer;
	private String sendMsgTopic;
	private String resultMsgTopic;

	@Autowired
	private KafkaProperties properties;
	
	@PostConstruct
	public void initKafkaProducer() {
		sendMsgTopic = properties.getSend_msg_topic();
		resultMsgTopic = properties.getResult_msg_topic();
		
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,properties.getProduce_connect());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());	

		producer = new KafkaProducer<String, String>(props);
	}

	@Override
	public void sendMsgProduce(String msg){
		logger.info("Kafka Broker에 전송요청 시작");
		producer.send(new ProducerRecord<String, String>(sendMsgTopic, "myKey", msg));
		logger.info("Kafka Broker에 전송요청 종료");
		//producer.close();
	}

	@Override
	public void resultMsgProduce(String msg){
		logger.info("Kafka Broker에 전송결과 시작");
		producer.send(new ProducerRecord<String, String>(resultMsgTopic, "myKey", msg));	
		logger.info("Kafka Broker에 전송결과 종료");
		//producer.close();
	}
}
