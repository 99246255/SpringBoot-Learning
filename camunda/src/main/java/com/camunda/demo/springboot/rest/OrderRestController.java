package com.camunda.demo.springboot.rest;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.demo.springboot.ProcessConstants;

@RestController
public class OrderRestController {
  
  @Autowired
  private ProcessEngine camunda;


  @RequestMapping(value = "/hello",method = RequestMethod.GET)
  public void placeOrderPOST(Long barcode) {
      ProcessInstance processInstance = camunda.getRuntimeService().startProcessInstanceByKey(//
              "Process_1", //
              Variables //
                      .putValue("barcode", barcode));
//    String processDefinitionId = processInstance.getProcessDefinitionId();
  }

}
