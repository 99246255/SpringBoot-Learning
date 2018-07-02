package com.camunda.demo.springboot.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BSink {
	String SINK1 = "input2";

	@Input(SINK1)
	SubscribableChannel input();
}
