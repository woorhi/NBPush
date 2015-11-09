package com.nbware.push.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import com.nbware.push.model.SendRequestMsg;
import com.nbware.push.model.SendResultMsg;

@Service
public class GCMSenderSvcImpl implements GCMSenderSvc {
	private Sender sender;
	private SendRequestMsg gcmMsg; 
	private Gson gson = new Gson();

	@PostConstruct
	public void initGCMSender() {
	}	
	
	@Override
	public SendResultMsg gcmSend(String appId, String msg) throws Exception{        
        gcmMsg = gson.fromJson(msg, SendRequestMsg.class);        
        sender = new Sender(appId);

    	Message message = new Message.Builder()
    			.addData("content", gcmMsg.getContent())
    			.addData("imgUrl", gcmMsg.getImgUrl())
    			.addData("linkUrl", gcmMsg.getLinkUrl())
    			.addData("msgMngID", String.valueOf(gcmMsg.getMsgMngID()))
    			.addData("msgType", gcmMsg.getMsgType())
    			.addData("showDate", gcmMsg.getShowDate())
    			.addData("title", gcmMsg.getTitle())
    			.build();
    	
    	List<String> list = new ArrayList<String>();
    	list = gcmMsg.getListRegIDs();

    	MulticastResult multiResult;    	
    	multiResult = sender.send (message,list,5);

		SendResultMsg resultMsg = new SendResultMsg();
		
		resultMsg.setMultiResult(multiResult);
		resultMsg.setRegIds(list);
		resultMsg.setMsgMngId(Integer.valueOf(gcmMsg.getMsgMngID()));
		resultMsg.setSendReqMsg(gcmMsg);
		
    	return resultMsg;
	}
}
