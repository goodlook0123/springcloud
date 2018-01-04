package com.springboot.caoqing.service.serviceImpl;

import com.springboot.caoqing.MQ.Sender;
import com.springboot.caoqing.bean.Role;
import com.springboot.caoqing.mapper.RoleMapper;
import com.springboot.caoqing.redis.RedisUtil;
import com.springboot.caoqing.service.SpringBootService;
import com.springboot.caoqing.util.SendMessageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service(value = "springBootService")
public class SpringBootServiceImpl implements SpringBootService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private Sender sender;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SendMessageUtil sendMessageUtil;

    @Override
    //@Transactional
    public Role findRoles() {
        return roleMapper.selectByPrimaryKey(1);
    }

    @Override
    public List<Role> findRoleByModele(String moduleName) {

        //RedisUtil redisUtil = new RedisUtil();
        redisUtil.set("name", "曹清");
        redisUtil.set("address", "内蒙古");
        logger.info(redisUtil.set("address", "大内蒙", 50));


        return roleMapper.findRoleByModele(moduleName);
    }

    @Override
    public void sendMqMessage(String strMessage) {
        logger.info(strMessage+"service打印的mq发送");
        sender.send(strMessage);
    }

    @Override
    public void sendMailMessage() {
        sendMessageUtil.sendMailMessage();
        logger.debug("邮件发送成功**********####################################");
    }
}
