package com.camunda.demo.springboot.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ASink {
	String SINK1 = "input1";

	@Input(SINK1)
	SubscribableChannel input();
}
