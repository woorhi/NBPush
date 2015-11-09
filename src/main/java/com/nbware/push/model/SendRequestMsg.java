package com.nbware.push.model;

import java.util.ArrayList;
import java.util.List;

public class SendRequestMsg {
	private String title;
	private String content;
	private String imgUrl;
	private String linkUrl;
	private String msgMngID;
	private String msgType;
	private String receiverID;
	private String receiverType;
	private String showDate;
	private int retryCnt;
	public int getRetryCnt() {
		return retryCnt;
	}

	public void setRetryCnt(int retryCnt) {
		this.retryCnt = retryCnt;
	}
	
	public void increaseRetryCnt(){
		this.retryCnt++;
	}
	private List<String> listRegIDs;
		
	public SendRequestMsg() {
		super();
	}
	
	public SendRequestMsg(String title) {
		super();
		setTitle(title);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getMsgMngID() {
		return msgMngID;
	}
	public void setMsgMngID(String msgMngID) {
		this.msgMngID = msgMngID;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getShowDate() {
		return showDate;
	}
	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}
	public List<String> getListRegIDs() {
		return listRegIDs;
	}
	public void setListRegIDs(List<String> listRegIDs) {
		this.listRegIDs = listRegIDs;
	}
	public void setListRegID(String RegID){
		List<String> list = new ArrayList<String>();
		list.add(RegID);
		this.listRegIDs = list;
	}
public void addRegID(String regID){
	this.listRegIDs.add(regID);
}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SendRequestMsg [title=");
		builder.append(title);
		builder.append(", content=");
		builder.append(content);
		builder.append(", imgUrl=");
		builder.append(imgUrl);
		builder.append(", linkUrl=");
		builder.append(linkUrl);
		builder.append(", msgMngID=");
		builder.append(msgMngID);
		builder.append(", msgType=");
		builder.append(msgType);
		builder.append(", showDate=");
		builder.append(showDate);
		builder.append(", listRegIDs=");
		builder.append(listRegIDs);
		builder.append("]");
		return builder.toString();
	}
}
