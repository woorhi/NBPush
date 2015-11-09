package com.nbware.push.service;

import java.util.List;

import kafka.consumer.KafkaStream;

public interface SendResultSvc {
	public void streamList(List<KafkaStream<byte[], byte[]>> streamList);
}
