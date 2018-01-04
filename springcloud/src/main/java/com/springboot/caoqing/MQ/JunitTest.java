package com.springboot.caoqing.MQ;

import com.springboot.caoqing.CaoqingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CaoqingApplication.class)
public class JunitTest {

    @Autowired
    private Sender sender;

    @Test
    public void hello() {
        sender.send("hello 我测试下");
    }
}
