package com.nbware.push.service;

import com.nbware.push.model.SendResultMsg;

public interface GCMSenderSvc {
	SendResultMsg gcmSend(String appId, String msg) throws Exception;
}
