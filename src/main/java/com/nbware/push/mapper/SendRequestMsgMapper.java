package com.nbware.push.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.nbware.push.model.SendRequestMsg;

public interface SendRequestMsgMapper {
	List<SendRequestMsg> selectSendRequestMsgList();
	void updateSendRequestMsgStatus(@Param("msgId") int msgId, @Param("status") int status);
	List<String> selectRegIds(@Param("receiverId") String receiverId);
}
