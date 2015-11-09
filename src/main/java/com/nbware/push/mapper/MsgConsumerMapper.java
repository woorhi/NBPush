package com.nbware.push.mapper;

import org.apache.ibatis.annotations.Param;

public interface MsgConsumerMapper {
	void updateSendResult(@Param("msgMngID") int msgMngID, @Param("successCnt") int successCnt, @Param("failCnt") int failCnt);
	void insertSendFailLog(@Param("msgMngID") int msgMngID, @Param("regID") String regID, @Param("jsonMsg") String jsonMsg, @Param("errMsg") String errMsg);
}
