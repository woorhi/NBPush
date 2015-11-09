package com.nbware.push.model;

import org.springframework.stereotype.Component;

@Component
public class KafkaProperties {	
	private String group_id = "test-group";	
	private String produce_connect = "192.168.1.21:9092";
	private String zookeeper_connect = "192.168.1.21:2181";	
	private String auto_commit_interval_ms = "1000";
	private String send_msg_topic = "xerobyte";
	private String result_msg_topic = "sendResult";
	private int consume_num_thread = 20;
	
	public int getConsume_num_thread() {
		return consume_num_thread;
	}

	public String getSend_msg_topic() {
		return send_msg_topic;
	}

	public String getGroup_id() {
		return group_id;
	}

	public String getZookeeper_connect() {
		return zookeeper_connect;
	}

	public String getAuto_commit_interval_ms() {
		return auto_commit_interval_ms;
	}

	public String getProduce_connect() {
		return produce_connect;
	}

	public String getResult_msg_topic() {
		return result_msg_topic;
	}

}
