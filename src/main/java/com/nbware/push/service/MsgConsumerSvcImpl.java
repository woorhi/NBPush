package com.nbware.push.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbware.push.mapper.MsgConsumerMapper;
import com.nbware.push.model.KafkaProperties;
import com.nbware.push.model.SendResultMsg;

import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

@Service
public class MsgConsumerSvcImpl implements MsgConsumerSvc {
	
    private static Logger logger = LoggerFactory.getLogger(MsgConsumerSvcImpl.class);
	@Autowired
	private MsgConsumerMapper consumeMapper;

	@Autowired
	private com.nbware.push.service.KafkaProducer producer;
	
	@Autowired
	private GCMSenderSvc gcmSender;
	
	@Autowired
	private KafkaProperties properties;

	private  ExecutorService executor;	
	
	@PostConstruct
	public void initSendMsgConsumer() {
		executor = Executors.newFixedThreadPool(properties.getConsume_num_thread());
	}	
    
	@Override
	public void streamList(List<KafkaStream<byte[], byte[]>> streamList) {
	      for (final KafkaStream<byte[], byte[]> stream : streamList) {
	            executor.execute(new Runnable() {
	                @Override
	                public void run() {
	                    for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : stream) {
	                        System.out.println(new String(messageAndMetadata.message()));
	                        String msg = new String(messageAndMetadata.message());            
	                        String appId = "AIzaSyAu7GTzwn6VI_SfrCFKwt_sBfKe0QR2QMc";

							try {
								SendResultMsg sendResultMsg = gcmSender.gcmSend(appId, msg);
								
	                        	if(sendResultMsg != null)
	                        	{
	                				producer.resultMsgProduce(sendResultMsg.toJson()); 
		    	                    	
                					logger.info("전송결과 DB Update 시작");
	                        		consumeMapper.updateSendResult(Integer.valueOf(sendResultMsg.getMsgMngId()), sendResultMsg.getMultiResult().getSuccess(), sendResultMsg.getMultiResult().getFailure());      
                					logger.info("전송결과 DB Update 완료");          				
	                        	}
	                		} catch (Exception e) {
								logger.info("==== GCM 전송 실패 ====");
								logger.info(msg);
								continue;
							}
	                    }
	                }
	            });
	        }
	}	
}
