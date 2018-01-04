package com.springboot.caoqing.service;

import com.springboot.caoqing.bean.Role;

import java.util.List;

public interface SpringBootService {

    Role findRoles();

    List<Role> findRoleByModele(String moduleName);

    void sendMqMessage(String strMessage);

    void sendMailMessage();
}
