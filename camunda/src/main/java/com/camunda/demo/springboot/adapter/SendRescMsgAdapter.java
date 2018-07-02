package com.camunda.demo.springboot.adapter;

import com.camunda.demo.springboot.kafka.BSource;
import com.camunda.demo.springboot.kafka.Data;
import com.camunda.demo.springboot.kafka.KafkaMessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendRescMsgAdapter implements JavaDelegate {

    @Autowired
    private BSource source;

    @Autowired
    private KafkaMessageSender messageSender;

    public void execute(DelegateExecution ctx) throws Exception {
        Long barcode = (Long) ctx.getVariable("barcode");
        System.out.println("接收barcode : " + barcode);
        this.messageSender.sendMessage(source, new Data(barcode));
    }

}
