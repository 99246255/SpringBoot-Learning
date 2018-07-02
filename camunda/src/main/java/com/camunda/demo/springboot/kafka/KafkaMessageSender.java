package com.camunda.demo.springboot.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;


@Service
public class KafkaMessageSender{
    
    @Autowired
    private ThreadPoolTaskExecutor executor;

    public <T> boolean sendMessage(Source source, T data) {
        return source.output().send(MessageBuilder.withPayload(data).build());
    }
    
    public <T> void sendMessageAsync(KafkaSourceThread<T> thread, Source source, T data) throws Exception{
    	thread.init(source, data);
    	ListenableFuture<Object> future = executor.submitListenable(thread);
    	// register a callback with the listener to receive the result of the send asynchronously
    	future.addCallback(thread.successCallback(data), thread.failureCallback(data));
    }
    
    
}