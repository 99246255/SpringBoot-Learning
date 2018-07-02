package com.camunda.demo.springboot.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

public interface BSource extends Source{
	//here is the very important defines, it must be same with the name in application.yml
	String SOURCE1 = "output2";

	@Output(SOURCE1)
	MessageChannel output();
}


