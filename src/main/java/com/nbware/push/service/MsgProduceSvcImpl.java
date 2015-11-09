package com.nbware.push.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nbware.push.mapper.SendRequestMsgMapper;
import com.nbware.push.model.SendRequestMsg;

@Service
public class MsgProduceSvcImpl implements MsgProduceSvc {
    private static Logger logger = LoggerFactory.getLogger(MsgConsumerSvcImpl.class);
    
    @Autowired
    private com.nbware.push.service.KafkaProducer producer;
    
	@Autowired
	private SendRequestMsgMapper reqMapper;
	
	@Override
	public List<SendRequestMsg> getReqMsgList(){
		return reqMapper.selectSendRequestMsgList();
	}	
	
	@PostConstruct
	public void init(){
		System.out.println("Start Producer Service");		
	}	
	
   @Scheduled(cron="*/3 * * * * *") 
	public void cronJob() throws UnsupportedEncodingException{
		List<SendRequestMsg> listSendReqMsg = reqMapper.selectSendRequestMsgList();
		
		for (SendRequestMsg msg : listSendReqMsg) {		
			msg.setMsgType("2");
			msg.setRetryCnt(0);
			msg.setListRegIDs(reqMapper.selectRegIds(msg.getReceiverID()));			
			msg.addRegID("TESTREGID1234");
			
			Gson gson = new Gson();		
			String jsonMsg = gson.toJson(msg);
			
			System.out.println(jsonMsg);				
			
			try {
				producer.sendMsgProduce(jsonMsg);

				logger.info("전송요청 DB Update 시작");
				reqMapper.updateSendRequestMsgStatus(Integer.valueOf(msg.getMsgMngID()), 1);
				logger.info("전송요청 DB Update 종료");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

	
}
