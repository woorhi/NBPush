package com.nbware.push.model;

import java.util.List;

import com.google.android.gcm.server.MulticastResult;
import com.google.gson.Gson;

public class SendResultMsg {
	private MulticastResult multiResult;
	private List<String> regIds;
	private int msgMngId;
	private SendRequestMsg sendReqMsg;
	
	public SendRequestMsg getSendReqMsg() {
		return sendReqMsg;
	}
	public void setSendReqMsg(SendRequestMsg sendReqMsg) {
		this.sendReqMsg = sendReqMsg;
	}
	public int getMsgMngId() {
		return msgMngId;
	}
	public void setMsgMngId(int msgMngId) {
		this.msgMngId = msgMngId;
	}
	public MulticastResult getMultiResult() {
		return multiResult;
	}
	public void setMultiResult(MulticastResult multiResult) {
		this.multiResult = multiResult;
	}
	public List<String> getRegIds() {
		return regIds;
	}
	public void setRegIds(List<String> regIds) {
		this.regIds = regIds;
	}
	
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SendResultMsg [multiResult=");
		builder.append(multiResult);
		builder.append(", regIds=");
		builder.append(regIds);
		builder.append(", msgMngId=");
		builder.append(msgMngId);
		builder.append(", sendReqMsg=");
		builder.append(sendReqMsg);
		builder.append("]");
		return builder.toString();
	}
	
	
}
