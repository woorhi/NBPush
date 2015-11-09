package com.nbware.push.service;

public interface KafkaProducer {
	void sendMsgProduce(String msg);
	void resultMsgProduce(String msg);
}
