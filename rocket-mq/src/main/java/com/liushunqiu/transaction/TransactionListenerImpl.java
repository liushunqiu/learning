package com.liushunqiu.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

@RocketMQTransactionListener(txProducerGroup="transaction")
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
	private static final Logger logger = LoggerFactory.getLogger(TransactionListenerImpl.class);
	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
		logger.info("进来了executeLocalTransaction方法,msg={},arg={}",msg,arg);
		return RocketMQLocalTransactionState.UNKNOWN;
	}

	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
		logger.info("进来了checkLocalTransaction方法,msg={}",msg);
		return RocketMQLocalTransactionState.COMMIT;
	}
}
