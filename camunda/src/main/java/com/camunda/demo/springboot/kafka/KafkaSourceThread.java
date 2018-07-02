package com.camunda.demo.springboot.kafka;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

public abstract class KafkaSourceThread<T> implements Callable<Object> {

	private static Logger logger = LoggerFactory
			.getLogger(KafkaSourceThread.class);

	private Source source;
	private T payload;
	
	public void init(Source source, T payload){
		this.source = source;
		this.payload = payload;
	}

	@Override
	public Boolean call() {
		return source.output()
				.send(MessageBuilder.withPayload(payload).build());
	}

	public SuccessCallback<? super Object> successCallback(T o) {
		return new SuccessCallback<Object>() {
			@Override
			public void onSuccess(Object result) {
				logger.debug("Success to produce message. Message is {}.",
						result);
			}
		};
	}

	public FailureCallback failureCallback(T o) {
		return new FailureCallback() {
			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error in producing Mseeage to Kafka!", ex);
			}

		};
	}

}
