package com.springbootclient.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class SpringCloudController {

    private static final Logger logger = LoggerFactory.getLogger(SpringCloudController .class);

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/springCloudTest",method = RequestMethod.GET)
    public String springCloudTest() {
        String providerMsg = restTemplate.getForEntity("http://KUNFIND-SERVICE/test/testSpringcloud",
                String.class).getBody();
        return "Hello,I am springcloudClient,<br>So success!!: <br/> " + providerMsg;
    }

}
