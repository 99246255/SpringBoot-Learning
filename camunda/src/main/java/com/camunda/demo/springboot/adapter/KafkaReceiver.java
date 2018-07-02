package com.camunda.demo.springboot.adapter;

import com.camunda.demo.springboot.ProcessConstants;
import com.camunda.demo.springboot.kafka.ASink;
import com.camunda.demo.springboot.kafka.Data;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("!test")
@EnableBinding(ASink.class)
public class KafkaReceiver {

  @Autowired
  private ProcessEngine camunda;

  public KafkaReceiver() {
  }

  private static int count = 0;
  public KafkaReceiver(ProcessEngine camunda) {
    this.camunda = camunda;
  }
  
  /**
   * Dummy method to handle the shipGoods command message - as we do not have a 
   * shipping system available in this small example
   */

  // 消息监听，实现了消息类型转换自动转化
  @StreamListener(ASink.SINK1)
  public void loggerSink(Data data) {
      Long barcode = data.getBarcode();
    count++;
    System.out.println("分样barcode: " + barcode);
    camunda.getRuntimeService().createMessageCorrelation("Message_Recv")
            .processInstanceVariableEquals("barcode", barcode)
            .setVariable("type", count%2==0?1:0)
            .correlateWithResult();
  }


  
}
