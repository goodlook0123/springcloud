package com.springboot.caoqing;

import com.springboot.caoqing.MQ.Sender;
import com.springboot.caoqing.bean.Role;
import com.springboot.caoqing.service.SpringBootService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class Controller {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private SpringBootService springBootService;

    @Autowired
    private DiscoveryClient client;

    @ResponseBody
    @RequestMapping(value = "/findRoles", produces = {"application/json;charset=UTF-8"})
    public String findRoles(){
        return springBootService.findRoles().getRoleName();
    }

    @RequestMapping(value = "/testSpringcloud", method = RequestMethod.GET)
    public String indexs(){
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/testSpringcloud,host:"+instance.getHost()+",service_id:"+instance.getServiceId());
        return springBootService.findRoles().getRoleName();
    }

    @ResponseBody
    @RequestMapping(value = "/findRolesNameByModuleName", produces = {"application/json;charset=UTF-8"})
    public List<Role> findRolesNameByModuleName(HttpServletRequest request){

        String moduleName = request.getParameter("moduleName");
//"领料申请单"
        //测试rabbitmq
        springBootService.sendMqMessage(moduleName);

        return springBootService.findRoleByModele(moduleName);
    }
    @RequestMapping(value = "/testRabbidMqkunFind",method = RequestMethod.GET)
    public void testRabbidMqkunFind(HttpServletRequest request)
    {
        String strMessage = request.getParameter("strMessage");
        /*ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/testSpringcloud,host:"+instance.getHost()+",service_id:"+instance.getServiceId());*/
        //Sender sender = new Sender();
        springBootService.sendMqMessage(strMessage);
    }
    @RequestMapping(value = "/testSendmailMessage",method = RequestMethod.GET)
    public void testSendmailMessage(HttpServletRequest request){
        springBootService.sendMailMessage();
    }

}
