package com.camunda.demo.springboot.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestConfiguration {

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public RestTemplate createRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
    jsonMessageConverter.setObjectMapper(objectMapper);
    messageConverters.add(jsonMessageConverter);
    restTemplate.setMessageConverters(messageConverters);
    return restTemplate;
  }
}
