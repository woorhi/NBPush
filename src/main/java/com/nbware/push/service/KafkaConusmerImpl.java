package com.nbware.push.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nbware.push.StaticFinalValue;
import com.nbware.push.model.KafkaProperties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

@Service
public class KafkaConusmerImpl implements KafkaConsumer {

	private static Logger logger = LoggerFactory.getLogger(KafkaConusmerImpl.class);

	@Autowired
	private KafkaProperties properties;
	
	@Autowired
	private MsgConsumerSvc sendConsumer ;
	
	@Autowired
	private SendResultSvc resultConsumer ;

	private ConsumerConfig consumerConfig;

	private ConsumerConnector consumer;

	private Map<String, List<KafkaStream<byte[], byte[]>>> map;

	@PostConstruct
	public void initProperties() {

		Properties props = new Properties();
		props.put(StaticFinalValue.VALUE_STRING_GROUP_ID, properties.getGroup_id());
		props.put(StaticFinalValue.VALUE_STRING_ZOOKEEPER_CONNECT, properties.getZookeeper_connect());
		props.put(StaticFinalValue.VALUE_STRING_AUTO_COMMIT_INTERVAL_MS, properties.getAuto_commit_interval_ms());

		consumerConfig = new ConsumerConfig(props);		
		
		consumer = Consumer.createJavaConsumerConnector(consumerConfig);
	}
	
    @Scheduled(cron="*/3 * * * * *") 
	public void cronJob(){
		HashMap<String, Integer>  topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(properties.getSend_msg_topic(), properties.getConsume_num_thread());
		topicCountMap.put(properties.getResult_msg_topic() , properties.getConsume_num_thread());
		try {
			getlist(topicCountMap);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	public  void getlist( HashMap<String, Integer> topicCountMap) {
	   if(map == null){
		      map = consumer.createMessageStreams(topicCountMap);
		}
	     
		List<KafkaStream<byte[], byte[]>> sendList = map.get(properties.getSend_msg_topic());
		//logger.info("sendList : "+sendList.size());
	     if(sendList.size() >0){
	    	 sendConsumer.streamList(sendList);
	     }
	     
	     List<KafkaStream<byte[], byte[]>> resultList = map.get(properties.getResult_msg_topic());
	     //logger.info("resultList : "+resultList.size());
	     if(resultList.size() > 0){
	    	 resultConsumer.streamList(resultList);
	     }
	}
}
