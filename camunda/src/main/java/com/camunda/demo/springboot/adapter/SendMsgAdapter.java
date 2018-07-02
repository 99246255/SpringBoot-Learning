package com.camunda.demo.springboot.adapter;

import com.camunda.demo.springboot.kafka.ASource;
import com.camunda.demo.springboot.kafka.Data;
import com.camunda.demo.springboot.kafka.KafkaMessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMsgAdapter implements JavaDelegate {


  @Autowired
  private ASource source;

  @Autowired
  private KafkaMessageSender messageSender;

  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    Long barcode = (Long) ctx.getVariable("barcode");
    System.out.println("扫码barcode : " + barcode);
    this.messageSender.sendMessage(source, new Data(barcode));
  }

}
