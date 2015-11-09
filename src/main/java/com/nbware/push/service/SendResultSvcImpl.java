package com.nbware.push.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.gson.Gson;
import com.nbware.push.mapper.MsgConsumerMapper;
import com.nbware.push.model.KafkaProperties;
import com.nbware.push.model.SendRequestMsg;
import com.nbware.push.model.SendResultMsg;

import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

@Service
public class SendResultSvcImpl implements SendResultSvc {

    private static Logger logger = LoggerFactory.getLogger(MsgConsumerSvcImpl.class);
        
    @Autowired
	private MsgConsumerMapper consumeMapper;

	@Autowired
	private KafkaProperties properties;

	private  ExecutorService executor;
	
	@PostConstruct
	public void initSendResultConsumer() {
		executor = Executors.newFixedThreadPool(properties.getConsume_num_thread());
	}	
    
	@Override
	public void streamList(List<KafkaStream<byte[], byte[]>> streamList) {
     	
        for (final KafkaStream<byte[], byte[]> stream : streamList) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : stream) {
                        String msg = new String(messageAndMetadata.message());
                        
                        try{
                        	Gson gson = new Gson();
                        	
                        	logger.info(msg);
                        	SendResultMsg sendResultMsg = gson.fromJson(msg, SendResultMsg.class);
                        	logger.info(sendResultMsg.toString());
                            MulticastResult multiResult = sendResultMsg.getMultiResult();
                            List<String> listRegIds = sendResultMsg.getRegIds();
                                                    	
                        	if(multiResult != null)
                        	{
                        		List<Result> resultList = multiResult.getResults();
                        		int idxId = 0;
                        		
            			    	SendRequestMsg failMsg = sendResultMsg.getSendReqMsg();
            			    	failMsg.increaseRetryCnt();	                 
            			    	
                        		for(Result result : resultList){
                       				System.out.println(result.getMessageId());
                       				if (result.getMessageId() != null) {
	                    			    String canonicalRegId = result.getCanonicalRegistrationId();
	                    			    System.out.println("canonicalRegId : " + canonicalRegId);
	                    			    if (canonicalRegId != null) {
		                    			     // same device has more than on registration ID: update database
		                    			     System.out.println("same device has more than on registration ID: update database");
	                    			    }
                       				} else {
	                    			    String error = result.getErrorCodeName();
	                    			    logger.info("[ERROR]"+error + ", [RegID]"+listRegIds.get(idxId));
	                    			    
	                    			    if (failMsg.getRetryCnt() < 3){
	                    			    	failMsg.setListRegID(listRegIds.get(idxId));
	                    			    	
	                    			    	consumeMapper.insertSendFailLog(Integer.valueOf(failMsg.getMsgMngID()), listRegIds.get(idxId), gson.toJson(failMsg), error);                    			    	
	                    			    }	                    			    	                    			    	
	                    			    
	                    			    if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
	                    			    	System.out.println("application has been removed from device - unregister");
		                    			     // database
	                    			    }
                    			   }
           							idxId++;
                        		}       		
                        	}
                		} catch(Exception e) {
                			e.printStackTrace();
                		}
                    }
                }
            });
        }
		
	}
}
