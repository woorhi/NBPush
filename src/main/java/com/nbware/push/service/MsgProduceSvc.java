package com.nbware.push.service;

import java.util.List;

import com.nbware.push.model.SendRequestMsg;

public interface MsgProduceSvc {
	List<SendRequestMsg> getReqMsgList();

}
